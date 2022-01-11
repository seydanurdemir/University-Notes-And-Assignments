let express = require('express');
let router = express.Router();
let constants = require("../constant");
let db = require('../db');

router.get('/list', async (req, res) => {
    // Cüzdanlar
    let wallets = await db.db("SELECT id, is_valid, user_id, balance, LPAD(user_id, 11, '10000000000') AS wallet_name FROM wallet");

    res.render('wallet/list', { title: 'Cüzdanlar', wallets: wallets });
});

router.get('/my', async (req, res) => {

    if (!['SUB', 'PRO'].includes(req.auth_type)) {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT wallet.id, wallet.is_valid, user.username, wallet.balance, LPAD(wallet.user_id, 11, '10000000000') AS wallet_name
                                      FROM wallet
                                      INNER JOIN user ON user.id = wallet.user_id
                                      WHERE wallet.user_id = ?`, [req.auth_id]);

    if (wallet == null) {
        res.resultError("Geçerli bir cüzdan bulunamadı!");
        return;
    }

    // Transferler
    let transactions = await db.db(`SELECT t.* FROM
                                    (SELECT transaction_sequence_number AS seq_num, transaction_type_id, transaction_amount, channel_type_id, created_at
                                                                        FROM transaction
                                                                        WHERE transaction_source_id = 24 AND transaction_type_id = 22 AND is_valid = 1 AND source_id = ?
                                    UNION
                                    SELECT transaction_sequence_number AS seq_num, transaction_type_id, transaction_amount, channel_type_id, created_at
                                                                        FROM transaction
                                                                        WHERE transaction_source_id = 24 AND transaction_type_id = 23 AND is_valid = 1 AND destination_id = ?
                                    ) AS t
                                    ORDER BY t.created_at`, [wallet.id, wallet.id]);

    res.render('wallet/edit', { title: 'Cüzdanım', wallet: wallet, transactions: transactions });
});

router.get('/edit/:id', async (req, res) => {

    if (!['SUB', 'PRO'].includes(req.auth_type)) {
        res.resultError("Cüzdan güncellemek için abone veya hizmet sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT id, is_valid, user_id, balance
                                      FROM wallet
                                      WHERE id = ?`, [req.params.id]);

    // Kullanıcılar
    let users = await db.db(`SELECT id, is_valid, username
                             FROM user
                             WHERE user_type_id = 4 AND is_valid = 1`);

    res.render('wallet/edit', { title: 'Cüzdan Güncelle', wallet: wallet, users: users });
});

router.get('/upload', async (req, res) => {

    if (!['SUB', 'PRO'].includes(req.auth_type)) {
        res.resultError("Transfer yapmak için abone veya hizmet sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Kartlar
    let cards = await db.db(`SELECT id, is_valid, user_id, CONCAT(LEFT(card_number, 4), '-', SUBSTR(card_number, 6, 2),'**-****-**', RIGHT(card_number, 4)) AS card_number, card_name 
							 FROM card
							 WHERE user_id = ? AND is_valid = 1`, [req.auth_id]);

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT id, is_valid, LPAD(user_id, 11, '10000000000') AS wallet_name
                                      FROM wallet
                                      WHERE user_id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('wallet/upload', { title: 'Cüzdana Para Yükle', cards: cards, wallet: wallet });
});

router.post('/upload', async (req, res) => {

    if (!['SUB', 'PRO'].includes(req.auth_type)) {
        res.resultError("Transfer yapmak için abone veya hizmet sağlayıcı olmanız gerekmektedir!");
        return;
    }

    console.log("Request: ", req.body);

    // Begin transaction
    let transaction = await db.startTransaction();
    if (!transaction) {
        res.resultError("Bir hata oluştu!");
        return;
    }

    try {
        let credit_id = constants.transactionTypes[0].id;
        let debit_id = constants.transactionTypes[1].id;

        let internal_id = constants.transactionSources[0].id;
        let external_id = constants.transactionSources[1].id;

        let other_channel_id = constants.channelTypes[0].id;

        // Transaction Sequence Number
        let seq_num_results = await db.db("SELECT next_value('trn_seq') AS nextval;");
        let seq_number = seq_num_results[0].nextval;

        // First Transaction
        await db.query(`CALL create_transaction (?, ?, ?, ?, ?, ?, ?, ?, @result_val);`,
            [credit_id,
                external_id,
                other_channel_id,
                req.body.source_id,
                req.body.destination_id,
                req.body.transaction_amount,
                req.auth_id,
                seq_number], function (err, results) {
                    if (err) throw err;

                    db.query("SELECT @result_val AS result;", function (errCrd, resultsCrd) {
                        if (errCrd) throw errCrd;
                        if (resultsCrd[0].result < 1) {
                            if (err) throw err;
                            res.resultError("Limit yetersiz!");
                            return;
                        }
                    });

					console.log("First Transaction: ", results);
                });

        // Second Transaction
        await db.query("CALL create_transaction (?, ?, ?, ?, ?, ?, ?, ?, @result_val);",
            [debit_id,
                internal_id,
                other_channel_id,
                req.body.source_id,
                req.body.destination_id,
                req.body.transaction_amount,
                req.auth_id,
                seq_number], function (err, results) {
                    if (err) throw err;

					console.log("Second Transaction: ", results);
                });
    } catch (e) {
        // Rollback
        await db.rollbackTransaction();
        console.log("Hata: ", String(e));
    }
    // Commit
    await db.commitTransaction();

    res.resultOk("Transfer başarılıyla tamamlandı.");
});

router.post('/edit', async (req, res) => {

    if (!['SUB', 'PRO'].includes(req.auth_type)) {
        res.resultError("Cüzdan güncellemek için abone veya hizmet sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT id, is_valid, user_id, balance
                                      FROM wallet
                                      WHERE id = ?`, [req.params.id]);

    if (wallet == null) {
        res.resultError("Geçerli bir cüzdan bulunamadı!");
        return;
    }

    await db.dbUpdate(`UPDATE wallet SET user_id = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.user_id, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;