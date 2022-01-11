let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Faturalar
    let bills = await db.db(`SELECT bill.id, bill.is_valid, bill.subscription_id, bill.amount, bill.first_read_date, bill.last_read_date, bill.start_date, bill.due_date, 
                                CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
                                CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                             FROM bill
                             INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.is_valid = 1`);

    res.render('bill/list', { title: 'Faturalar', bills: bills });
});

router.get('/my-list', async (req, res) => {

    // Faturalar
    let bills = null;

    if (req.auth_type == 'SUB') {
        // Abonenin faturaları
        bills = await db.db(`SELECT bill.id, bill.is_valid, bill.subscription_id, bill.amount, bill.first_read_date, bill.last_read_date, bill.start_date, bill.due_date,
                                CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
                                CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                             FROM bill
                             INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.user_id = ? AND sub.is_valid = 1
                             WHERE bill.is_valid = 1`, [req.auth_id]);
    } else if (req.auth_type == 'PRO') {
        // Hizmet sağlayıcı
        let provider = await db.dbGetFirst(`SELECT id, is_valid, service_type_id, corporate_name, user_id
                                            FROM provider
                                            WHERE user_id = ?`, [req.auth_id]);

        if (provider == null) {
            res.resultError("Geçerli bir hizmet sağlayıcı bulunamadı bulunamadı!");
            return;
        }

        // Sağlayıcının faturaları
        bills = await db.db(`SELECT bill.id, bill.is_valid, bill.subscription_id, bill.amount, bill.first_read_date, bill.last_read_date, bill.start_date, bill.due_date, 
                                CONCAT('BILL', LPAD(bill.subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
                                CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                             FROM bill
                             INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.provider_id = ? AND sub.is_valid = 1
                             WHERE bill.is_valid = 1`, [provider.id]);
    } else {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    res.render('bill/list', { title: 'Faturalarım', bills: bills });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'PRO') {
        res.resultError("Fatura oluşturmak için sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Aboneler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                                     FROM subscription AS sub
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.user_id = ? AND provider.is_valid = 1
                                     WHERE sub.is_valid = 1`, [req.auth_id]);

    res.render('bill/add', { title: 'Fatura Ekle', subscriptions: subscriptions });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'PRO') {
        res.resultError("Fatura güncellemek için sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Fatura
    let bill = await db.dbGetFirst(`SELECT id, is_valid, subscription_id, amount, first_read_date, last_read_date, start_date, due_date,
                                        CONCAT('BILL', LPAD(subscription_id, 4, '0000'), LPAD(id, 4, '0000')) AS bill_number
                                    FROM bill
                                    WHERE id = ?`, [req.params.id]);

    // Aboneler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                                     FROM subscription AS sub
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.user_id = ? AND provider.is_valid = 1
                                     WHERE sub.is_valid = 1`, [req.auth_id]);

    res.render('bill/edit', { title: 'Fatura Güncelle', bill: bill, subscriptions: subscriptions });
});

router.get('/view/:id', async (req, res) => {

    // Fatura
    let bill = await db.dbGetFirst(`SELECT bill.id, bill.is_valid, bill.subscription_id, bill.amount, bill.first_read_date, bill.last_read_date, bill.start_date, bill.due_date,
                                        CONCAT('BILL', LPAD(subscription_id, 4, '0000'), LPAD(bill.id, 4, '0000')) AS bill_number,
                                        IFNULL(promise.id, 0) AS promise_id, IFNULL(payment.id, 0) AS payment_id
                                    FROM bill
                                    LEFT JOIN promise ON promise.bill_id = bill.id AND promise.is_valid = 1
                                    LEFT JOIN payment ON payment.bill_id = bill.id AND payment.is_valid = 1
                                    WHERE bill.id = ?`, [req.params.id]);

    // Aboneler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                                     FROM subscription AS sub
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                     WHERE sub.is_valid = 1`);

    res.render('bill/view', { title: 'Fatura Görüntüle', bill: bill, subscriptions: subscriptions });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'PRO') {
        res.resultError("Fatura oluşturmak için sağlayıcı olmanız gerekmektedir!");
        return;
    }
    
    // Abone
    let subscription = await db.dbGetFirst(`SELECT id, is_valid
                                            FROM subscription
                                            WHERE id = ? AND is_valid = 1`, [req.body.subscription_id]);

    if (subscription == null) {
        res.resultError("Geçerli bir abonelik bulunamadı!");
        return;
    }

    await db.dbInsert("INSERT INTO bill (subscription_id, amount, first_read_date, last_read_date, start_date, due_date, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)",
        [req.body.subscription_id, req.body.amount, req.body.first_read_date, req.body.last_read_date, req.body.last_read_date, req.body.due_date, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/bill/my-list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'PRO') {
        res.resultError("Fatura güncellemek için sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Abone
    let subscription = await db.dbGetFirst(`SELECT 1
                                            FROM subscription
                                            WHERE id = ? AND is_valid = 1`, [req.body.subscription_id]);

    if (subscription == null) {
        res.resultError("Geçerli bir abonelik bulunamadı!");
        return;
    }

    // Fatura
    let bill = await db.dbGetFirst(`SELECT 1
                                    FROM bill
                                    WHERE id = ? AND is_valid = 1`, [req.body.id]);

    if (bill == null) {
        res.resultError("Geçerli bir fatura bulunamadı!");
        return;
    }

    await db.dbUpdate(`UPDATE bill SET subscription_id = ?, amount = ?, first_read_date = ?, last_read_date = ?, start_date = ?, due_date = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.subscription_id, req.body.amount, req.body.first_read_date, req.body.last_read_date, req.body.last_read_date, req.body.due_date, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;