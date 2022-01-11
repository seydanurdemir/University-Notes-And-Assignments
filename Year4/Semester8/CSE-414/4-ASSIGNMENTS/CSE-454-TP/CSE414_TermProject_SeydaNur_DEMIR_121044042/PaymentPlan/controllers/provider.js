let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Sağlayıcılar
    let providers = await db.db(`SELECT id, is_valid, user_id, service_type_id, corporate_name 
                                 FROM provider
                                 ORDER BY service_type_id, corporate_name`);

    res.render('provider/list', { title: 'Hizmet Sağlayıcılar', providers: providers });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'EMP') {
        res.resultError("Hizmet Sağlayıcı oluşturmak için çalışan olmanız gerekmektedir!");
        return;
    }

    // Servis tipleri
    let serviceTypes = await db.db(`SELECT id, is_valid, code, value, description
                                    FROM parameter
                                    WHERE code = 'service_type' AND is_valid = 1`);

    // Herhangi bir sağlayıcının çalışanı olmayan kullanıcılar
    let users = await db.db(`SELECT u.id, u.is_valid, u.user_type_id, u.username
                             FROM user AS u
                             LEFT JOIN provider AS p ON p.user_id = u.id  AND p.is_valid = 1
                             WHERE p.id IS NULL AND user_type_id = 3 AND u.is_valid = 1`);

    res.render('provider/add', { title: 'Hizmet Sağlayıcı Ekle', serviceTypes: serviceTypes, users: users });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'EMP') {
        res.resultError("Hizmet Sağlayıcı güncellemek için çalışan olmanız gerekmektedir!");
        return;
    }

    // Sağlayıcı
    let provider = await db.dbGetFirst(`SELECT id, is_valid, service_type_id, corporate_name, user_id
                                        FROM provider
                                        WHERE id = ?`, [req.params.id]);

    if (provider == null) {
        res.resultError("Geçerli bir hizmet sağlayıcı bulunamadı bulunamadı!");
        return;
    }
                                        
    // Servis tipleri
    let serviceTypes = await db.db(`SELECT id, is_valid, code, value, description
                                    FROM parameter
                                    WHERE code = 'service_type' AND is_valid = 1`);

    // Kullanıcılar
    let users = await db.db(`SELECT id, is_valid, username
                             FROM user
                             WHERE user_type_id = 3 AND is_valid = 1`);

    res.render('provider/edit', { title: 'Hizmet Sağlayıcı Güncelle', provider: provider, serviceTypes: serviceTypes, users: users });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'EMP') {
        res.resultError("Hizmet Sağlayıcı oluşturmak için çalışan olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO provider (user_id, service_type_id, corporate_name, created_by) VALUES (?, ?, ?, ?)", [req.body.user_id, req.body.service_type_id, req.body.corporate_name, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/provider/list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'EMP') {
        res.resultError("Hizmet sağlayıcı güncellemek için çalışan olmanız gerekmektedir!");
        return;
    }

    await db.dbUpdate(`UPDATE provider SET user_id = ?, service_type_id = ?, corporate_name = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.user_id, req.body.service_type_id, req.body.corporate_name, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;