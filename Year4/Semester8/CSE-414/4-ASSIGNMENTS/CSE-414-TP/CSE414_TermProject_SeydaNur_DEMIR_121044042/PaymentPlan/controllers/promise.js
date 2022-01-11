let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Ödeme Sözleri
    let promises = await db.db(`SELECT promise.id, promise.is_valid, promise.bill_id, promise.promise_date, promise.status_id, promise.due_date, 
                                    CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number 
                                FROM promise
                                INNER JOIN bill AS bill ON bill.id = promise.bill_id AND bill.is_valid = 1
                                INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.is_valid = 1
                                WHERE bill.is_valid = 1`);

    res.render('promise/list', { title: 'Ödeme Sözleri', promises: promises });
});

router.get('/my-list', async (req, res) => {
    // Ödeme Sözleri
    let promises = null;

    if (req.auth_type == 'SUB') {
        // Abonenin verdiği ödeme sözleri
        promises = await db.db(`SELECT promise.id, promise.is_valid, promise.bill_id, promise.promise_date, promise.status_id, promise.due_date, 
                                    CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number 
                                FROM promise
                                INNER JOIN bill AS bill ON bill.id = promise.bill_id AND bill.is_valid = 1
                                INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.user_id = ? AND sub.is_valid = 1
                                WHERE promise.is_valid = 1`, [req.auth_id]);
    } else if (req.auth_type == 'PRO') {
        // Sağlayıcının aldığı ödeme sözleri
        let provider = await db.dbGetFirst(`SELECT id, is_valid, service_type_id, corporate_name, user_id
                                            FROM provider
                                            WHERE user_id = ?`, [req.auth_id]);

        if (provider == null) {
            res.resultError("Geçerli bir hizmet sağlayıcı bulunamadı bulunamadı!");
            return;
        }

        promises = await db.db(`SELECT promise.id, promise.is_valid, promise.bill_id, promise.promise_date, promise.status_id, promise.due_date, 
                                    CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number 
                                FROM promise
                                INNER JOIN bill AS bill ON bill.id = promise.bill_id AND bill.is_valid = 1
                                INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.provider_id = ? AND sub.is_valid = 1
                                WHERE promise.is_valid = 1`, [provider.id]);
    } else {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    res.render('promise/list', { title: 'Ödeme Sözlerim', promises: promises });
});

router.get(['/add', '/add/:bill_id'], async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Ödeme sözü oluşturmak için abone olmanız gerekmektedir!");
        return;
    }

    // Abonelikler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                                     FROM subscription AS sub
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                     WHERE sub.user_id = ? AND sub.is_valid = 1`, [req.auth_id]);

    // Daha önce söz vermediği faturalar
    let bills = await db.db(`SELECT bill.id, bill.is_valid, bill.amount, CAST(bill.due_date AS DATE) as due_date, 
                                 CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
                                 sub.id AS subscription_id
                             FROM bill
                             INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.user_id = ? AND sub.is_valid = 1
                             LEFT JOIN promise ON promise.bill_id = bill.id AND bill.is_valid = 1
                             WHERE bill.is_valid = 1 AND promise.id IS NULL`, [req.auth_id]);

    if (req.params.bill_id != null) {
        bills = bills.filter(b => b.id == req.params.bill_id);
    }

    // Kullanıcı
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('promise/add', { title: 'Ödeme Sözü Ekle', subscriptions: subscriptions, bills: bills, users: user });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Ödeme sözü oluşturmak için abone olmanız gerekmektedir!");
        return;
    }
    
    // Ödeme Sözü
    let promise = await db.dbGetFirst(`SELECT promise.id, promise.is_valid, promise.bill_id, promise.promise_date, promise.status_id, promise.due_date, promise.bill_id, promise.status_id, bill.subscription_id, 
                                           CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number 
                                       FROM promise
                                       INNER JOIN bill AS bill ON bill.id = promise.bill_id AND bill.is_valid = 1
                                       INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.is_valid = 1
                                       WHERE promise.id = ? AND promise.is_valid = 1`, [req.params.id]);

    // Abonelikler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number
                                     FROM subscription AS sub
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                     WHERE sub.id = ? AND sub.is_valid = 1`, [promise.subscription_id]);

    // Faturalar
    let bills = await db.db(`SELECT bill.id, bill.is_valid, bill.amount, CAST(bill.due_date AS DATE) as due_date, 
                                CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number, 
                                sub.id AS subscription_id
                             FROM bill
                             INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.is_valid = 1
                             WHERE bill.id = ? AND bill.is_valid = 1`, [promise.bill_id]);

    let bill = bills && bills[0];

    // Kullanıcı
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('promise/edit', { title: 'Ödeme Sözü Güncelle', promise: promise, subscriptions: subscriptions, bill: bill, bills: bills, users: user });
});

router.get('/view/:id', async (req, res) => {
    
    // Ödeme Sözü
    let promise = await db.dbGetFirst(`SELECT promise.id, promise.is_valid, promise.bill_id, promise.status_id, promise.promise_date, bill.due_date, promise.due_date AS promise_due_date, promise.promise_date, bill.amount, 
                                           user.username,
                                           CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number,
                                           CONCAT('BILL', LPAD(sub.id, 7, '0000000'), LPAD(bill.id, 4, '0000')) AS bill_number 
                                       FROM promise
                                       INNER JOIN bill AS bill ON bill.id = promise.bill_id AND bill.is_valid = 1
                                       INNER JOIN subscription AS sub ON sub.id = bill.subscription_id AND sub.is_valid = 1
                                       INNER JOIN user ON user.id = sub.user_id AND user.is_valid = 1
                                       WHERE promise.id = ? AND promise.is_valid = 1`, [req.params.id]);

    res.render('promise/view', { title: 'Ödeme Sözü Görüntüle', promise: promise });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Ödeme sözü oluşturmak için abone olmanız gerekmektedir!");
        return;
    }

    if (req.body.bill_id < 1) {
        res.resultError("Geçerli bir fatura bulunamadı!");
        return;
    }

    await db.dbInsert("INSERT INTO promise (bill_id, due_date, created_by) VALUES (?, ?, ?)",
        [req.body.bill_id, req.body.due_date, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/promise/my-list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Ödeme Sözü güncellemek için sağlayıcı olmanız gerekmektedir!");
        return;
    }

    // Ödeme Sözü
    let promise = await db.dbGetFirst(`SELECT id, is_valid, bill_id, promise_date, status_id, due_date
                                       FROM promise
                                       WHERE id = ?`, [req.body.id]);

    if (promise == null) {
        res.resultError("Geçerli bir ödeme sözü bulunamadı!");
        return;
    }

    await db.dbUpdate(`UPDATE promise SET bill_id = ?, due_date = ?, status_id = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.bill_id, req.body.due_date, 15, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;