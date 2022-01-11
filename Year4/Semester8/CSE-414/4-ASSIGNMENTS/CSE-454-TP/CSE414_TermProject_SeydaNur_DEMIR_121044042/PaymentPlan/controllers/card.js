let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Kartlar
    let cards = await db.db("SELECT id, is_valid, user_id, CONCAT(LEFT(card_number, 4), '-', SUBSTR(card_number, 6, 2),'**-****-**', RIGHT(card_number, 4)) AS card_number, card_name FROM card");

    res.render('card/list', { title: 'Kartlar', cards: cards });
});

router.get('/my-list', async (req, res) => {
    // Kartlar
    let cards = await db.db(`SELECT id, is_valid, user_id, CONCAT(LEFT(card_number, 4), '-', SUBSTR(card_number, 6, 2),'**-****-**', RIGHT(card_number, 4)) AS card_number, card_name 
                             FROM card
                             WHERE user_id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('card/list', { title: 'Kartlarım', cards: cards });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Kart oluşturmak için abone olmanız gerekmektedir!");
        return;
    }

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT id, is_valid, LPAD(user_id, 11, '10000000000') AS wallet_name
                                      FROM wallet
                                      WHERE user_id = ? AND is_valid = 1`, [req.auth_id]);

    // Kullanıcı
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('card/add', { title: 'Kart Ekle', wallet: wallet, users: user });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Kart güncellemek için abone olmanız gerekmektedir!");
        return;
    }

    // Kart
    let card = await db.dbGetFirst(`SELECT id, is_valid, user_id, card_number, card_name
                                    FROM card
                                    WHERE id = ?`, [req.params.id]);

    // Kullanıcı
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [req.auth_id]);

    // Transferler
    let transactions = await db.db(`SELECT transaction_sequence_number AS seq_num, transaction_type_id, transaction_amount, channel_type_id, created_at, row_number() over (partition by transaction_sequence_number) 
                                    FROM transaction 
                                    WHERE transaction_source_id = 24 AND is_valid = 1 AND (source_id = ? OR destination_id = ?) 
                                    GROUP BY transaction_sequence_number`, [card.id, card.id]);

    res.render('card/edit', { title: 'Kart Güncelle', card: card, users: user, transactions: transactions });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Kart oluşturmak için abone olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO card (user_id, card_number, card_name, created_by) VALUES (?, ?, ?, ?)",
        [req.body.user_id, req.body.card_number.replace(/[-]/g,''), req.body.card_name, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/card/my-list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'PRO') {
        res.resultError("Kart güncellemek için sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Kart
    let card = await db.dbGetFirst(`SELECT id, is_valid, user_id, card_number, card_name
                                      FROM card
                                      WHERE id = ?`, [req.params.id]);

    if (card == null) {
        res.resultError("Geçerli bir kart bulunamadı!");
        return;
    }

    await db.dbUpdate(`UPDATE card SET user_id = ?, card_number = ?, card_name = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.user_id, req.body.card_number, req.body.card_name, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;