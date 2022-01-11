let express = require('express');
let router = express.Router();
let constants = require("../constant");
let db = require('../db');

router.get('/list', async (req, res) => {
    // Ödemeler
	let payments = await db.db(`SELECT payment.transaction_sequence_number AS seq_num, bill.amount AS bill_amount, bill.start_date, bill.due_date, payment.created_at AS payment_date, provider.corporate_name,
									CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
									CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
								FROM payment
								INNER JOIN bill ON bill.id = payment.bill_id
								INNER JOIN subscription AS sub ON sub.id = bill.subscription_id
								INNER JOIN provider ON sub.provider_id = provider.id`);

	res.render('payment/list', { title: 'Ödemeler', payments: payments });
});

router.get('/my-list', async (req, res) => {

    // Ödemeler
    let payments = null;

    if (req.auth_type == 'SUB') {
        // Abonenin ödemeleri
        payments = await db.db(`SELECT payment.transaction_sequence_number AS seq_num, bill.amount AS bill_amount, bill.start_date, bill.due_date, payment.created_at AS payment_date, provider.corporate_name,
									CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
									CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
								FROM payment
								INNER JOIN bill ON bill.id = payment.bill_id
								INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.user_id = ?
								INNER JOIN provider ON sub.provider_id = provider.id`, [req.auth_id]);
    } else if (req.auth_type == 'PRO') {
        // Sağlayıcının gelen ödemeler
        payments = await db.db(`SELECT payment.transaction_sequence_number AS seq_num, bill.amount AS bill_amount, bill.start_date, bill.due_date, payment.created_at AS payment_date,
							 	CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
								 CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
							 FROM payment
							 INNER JOIN bill ON bill.id = payment.bill_id
							 INNER JOIN subscription AS sub ON sub.id = bill.subscription_id
							 INNER JOIN provider ON sub.provider_id = provider.id AND provider.user_id = ?`, [req.auth_id]);
    } else {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

	res.render('payment/list', { title: 'Ödemelerim', payments: payments });
});

router.get(['/create', '/create/:bill_id'], async (req, res) => {

	if (req.auth_type != 'SUB') {
		res.resultError("Ödeme yapmak için abone olmanız gerekmektedir!");
		return;
	}

    // Cüzdan
    let wallet = await db.dbGetFirst(`SELECT id, is_valid, user_id, balance, LPAD(user_id, 11, '10000000000') AS wallet_name
                                      FROM wallet
                                      WHERE user_id = ?`, [req.auth_id]);

    if (wallet == null) {
        res.resultError("Geçerli bir cüzdan bulunamadı!");
        return;
    }

	// Abonenin ödenmemiş faturaları
	let bills = await db.db(`SELECT *
							 FROM v_unpaids
							 WHERE user_id = ?`, [req.auth_id]);

	if (req.params.bill_id != null) {
		bills = bills.filter(b => b.bill_id == req.params.bill_id);
	}

	res.render('payment/create', { title: 'Fatura Öde', bills: bills, wallet: wallet });
});

router.post('/create', async (req, res) => {

	// Fatura
	let bill = await db.dbGetFirst(`SELECT id, amount
									FROM bill
									WHERE id = ? AND is_valid = 1`, [req.body.bill_id]);

	if (bill == null) {
		res.resultError("Geçerli bir fatura bulunamadı!");
		return;
	}

    // Abonenin Cüzdanı
    let sourceWallet = await db.dbGetFirst(`SELECT id, is_valid, user_id, balance, LPAD(user_id, 11, '10000000000') AS wallet_name
											FROM wallet
											WHERE user_id = ?`, [req.auth_id]);

    if (sourceWallet == null) {
        res.resultError("Geçerli bir cüzdan bulunamadı!");
        return;
    }

    // Sağlayıcı
    let provider = await db.dbGetFirst(`SELECT provider.id, wallet.id AS wallet_id
                                        FROM provider
										INNER JOIN wallet ON wallet.user_id = provider.user_id AND wallet.is_valid = 1
                                        WHERE provider.id = ?`, [req.body.provider_id]);

    if (provider == null) {
        res.resultError("Geçerli bir hizmet sağlayıcı bulunamadı bulunamadı!");
        return;
    }

	if (sourceWallet.balance < bill.amount) {
        res.resultError("Yeterli bakiye yok!");
        return;
	}

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
                internal_id,
                other_channel_id,
                sourceWallet.id,
                provider.wallet_id,
                bill.amount,
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
                sourceWallet.id,
                provider.wallet_id,
                bill.amount,
				req.auth_id,
				seq_number], function (err, results) {
					if (err) throw err;

					console.log("Second Transaction: ", results);
				});

		let payment_id = await db.dbInsert("INSERT INTO payment (bill_id, transaction_sequence_number, created_by) VALUES (?, ?, ?)",
			[req.body.bill_id, seq_number, req.auth_id]);
		
		console.log("Payment Id: ", payment_id);

		if (payment_id < 1) {
			throw new Error("Bir hata oluştu!");
		}
	} catch (e) {
		// Rollback
		await db.rollbackTransaction();
		console.log("Hata: ", String(e));
	}
	// Commit
	await db.commitTransaction();

	res.resultOkRedirect("Transfer başarılıyla tamamlandı.", "/payment/my-list");
});

module.exports = router;