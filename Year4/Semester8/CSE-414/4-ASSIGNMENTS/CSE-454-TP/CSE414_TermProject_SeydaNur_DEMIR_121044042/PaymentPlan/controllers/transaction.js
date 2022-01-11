let express = require('express');
let router = express.Router();
let constants = require("../constant");
let db = require('../db');

router.get('/list', async (req, res) => {
	// Transferler
	let transactions = await db.db(`SELECT transaction_sequence_number AS seq_num, source, destination, transaction_amount, channel_type_id, created_at
									FROM v_transactions`);

	res.render('transaction/list', { title: 'Transferler', transactions: transactions });
});

router.post('/create', async (req, res) => {
	// Begin transaction
	let transaction = await db.startTransaction();
	if (!transaction) {
		res.resultError("Bir hata oluştu!");
		return;
	}

	try {
		let credit_id = constants.transactionTypes[0].id;
		let debit_id = constants.transactionTypes[1].id;

		if ([credit_id, debit_id].find(x => x == req.body.transaction_type_id) == null) {
			res.resultError("Transfer tipi belirlenemedi!");
			return;
		}

		// Transaction Sequence Number
		let seq_num_results = await db.db("SELECT next_value('trn_seq') AS nextval;");
		let seq_number = seq_num_results[0].nextval;

		// First Transaction
		await db.query(`CALL create_transaction (?, ?, ?, ?, ?, ?, ?, ?, @result_val);`,
			[req.body.transaction_type_id == credit_id ? credit_id : debit_id,
			req.body.transaction_source_id,
			req.body.channel_type_id,
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
			[req.body.transaction_type_id == credit_id ? debit_id : credit_id,
			req.body.transaction_source_id,
			req.body.channel_type_id,
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

module.exports = router;