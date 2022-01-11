let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Abonelikler
    let subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, u.username, provider.corporate_name 
                                    FROM subscription AS sub
                                    INNER JOIN user AS u ON u.id = sub.user_id AND u.is_valid = 1
                                    INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                    WHERE sub.is_valid = 1
                                    ORDER BY sub.user_id, sub.id ASC`);

    res.render('subscription/list', { title: 'Abonelikler', subscriptions: subscriptions });
});

router.get('/my-list', async (req, res) => {
    // Abonelikler
    let subscriptions = null;

    if (req.auth_type == 'SUB') {
        // Abonenin abonelikleri
        subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, u.username, provider.corporate_name 
                                         FROM subscription AS sub
                                         INNER JOIN user AS u ON u.id = sub.user_id AND u.is_valid = 1
                                         INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                         WHERE sub.user_id = ? AND sub.is_valid = 1
                                         ORDER BY sub.user_id, sub.id ASC`, [req.auth_id]);
    } else if (req.auth_type == 'PRO') {
        // Hizmet Sağlayıcı
        let provider = await db.dbGetFirst(`SELECT id, is_valid, service_type_id, corporate_name, user_id
                                            FROM provider
                                            WHERE user_id = ?`, [req.auth_id]);

        if (provider == null) {
            res.resultError("Geçerli bir hizmet sağlayıcı bulunamadı bulunamadı!");
            return;
        }

        // Sağlayıcının aboneleri
        subscriptions = await db.db(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, u.username, provider.corporate_name 
                                     FROM subscription AS sub
                                     INNER JOIN user AS u ON u.id = sub.user_id AND u.is_valid = 1
                                     INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                     WHERE sub.provider_id = ? AND sub.is_valid = 1
                                     ORDER BY sub.user_id, sub.id ASC`, [provider.id]);
    } else {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    res.render('subscription/list', { title: 'Aboneliklerim', subscriptions: subscriptions });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Abonelik oluşturmak için abone kullanıcısı olmanız gerekmektedir!");
        return;
    }

    // Abonenin aboneliği olmayan sağlayıcılar
    let providers = await db.db(`SELECT DISTINCT provider.id, provider.is_valid, corporate_name
                                 FROM provider
                                 LEFT JOIN subscription AS sub ON provider.id = sub.provider_id AND sub.user_id = ? AND sub.is_valid = 1
                                 LEFT JOIN user ON user.id = sub.user_id AND user.is_valid = 1
                                 WHERE provider.is_valid = 1 AND user.id IS NULL
                                 ORDER BY corporate_name ASC`, [req.auth_id]);

    // Kullanıcı
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [req.auth_id]);

    res.render('subscription/add', { title: 'Abonelik Ekle', providers: providers, users: user });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type == 'EMP') {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    // Abonelik
    let subscription = await db.dbGetFirst(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, sub.provider_id
                                            FROM subscription AS sub
                                            INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                            WHERE sub.id = ?`, [req.params.id]);

    // Sağlayıcılar
    let provider = await db.db(`SELECT id, is_valid, corporate_name
                                FROM provider
                                WHERE id = ? AND is_valid = 1`, [subscription.provider_id]);

    // Kullanıcılar
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [subscription.user_id]);

    res.render('subscription/edit', { title: 'Abonelik Güncelle', subscription: subscription, providers: provider, users: user });
});

router.get('/view/:id', async (req, res) => {

    if (req.auth_type == 'EMP') {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    // Abonelik
    let subscription = await db.dbGetFirst(`SELECT sub.id, sub.is_valid, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, sub.provider_id, sub.created_at
                                            FROM subscription AS sub
                                            INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                            WHERE sub.id = ?`, [req.params.id]);

    // Sağlayıcılar
    let provider = await db.db(`SELECT id, is_valid, corporate_name
                                FROM provider
                                WHERE id = ? AND is_valid = 1`, [subscription.provider_id]);

    // Kullanıcılar
    let user = await db.db(`SELECT id, is_valid, username
                            FROM user
                            WHERE id = ? AND is_valid = 1`, [subscription.user_id]);

    res.render('subscription/view', { title: 'Abonelik Görüntüle', subscription: subscription, providers: provider, users: user });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'SUB') {
        res.resultError("Abonelik oluşturmak için abone kullanıcısı olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO subscription (user_id, provider_id, created_by) VALUES (?, ?, ?)",
        [req.body.user_id, req.body.provider_id, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/subscription/my-list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type == 'EMP') {
        res.resultError("Bu sayfayı görüntüleme yetkiniz yok!");
        return;
    }

    // Abonelik
    let subscription = await db.dbGetFirst(`SELECT sub.id, sub.is_valid, sub.user_id, CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) AS subscription_number, sub.user_id, sub.provider_id
                                            FROM subscription AS sub
                                            INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1
                                            WHERE sub.id = ?`, [req.params.id]);

    if (subscription == null) {
        res.resultError("Geçerli bir Abonelik bulunamadı!");
        return;
    }

    await db.dbUpdate(`UPDATE subscription SET user_id = ?, provider_id = ?, end_date = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.user_id, req.body.provider_id, req.body.end_date, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;