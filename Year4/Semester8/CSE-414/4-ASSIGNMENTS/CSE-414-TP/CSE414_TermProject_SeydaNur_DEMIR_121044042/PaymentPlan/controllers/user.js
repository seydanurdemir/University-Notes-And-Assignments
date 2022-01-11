let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Kullanıcılar
    let users = await db.db(`SELECT id, is_valid, user_type_id, username
                             FROM user
                             ORDER BY user_type_id, username`);

    res.render('user/list', { title: 'Kullanıcılar', users: users });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Kullanıcı oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    // Kullanıcı tipleri
    let userTypes = await db.db(`SELECT id, is_valid, code, value, description
                                 FROM parameter
                                 WHERE code = 'user_type' AND is_valid = 1`);

    res.render('user/add', { title: 'Kullanıcı Kaydet', userTypes: userTypes });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Kullanıcı güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    // Kullanıcı
    let user = await db.dbGetFirst(`SELECT id, is_valid, user_type_id, username, password, email, phone, address
                                     FROM user
                                     WHERE id = ?`, [req.params.id]);

    // Kullanıcı tipleri
    let userTypes = await db.db(`SELECT id, is_valid, code, value, description
                                FROM parameter
                                WHERE code = 'user_type' AND is_valid = 1`);

    res.render('user/edit', { title: 'Kullanıcı Güncelle', user: user, userTypes: userTypes });
});

router.get('/login', async (req, res) => {
    res.render('user/login', { title: 'Giriş Yap' });
});

router.get('/logout', async (req, res) => {
    res.cookie('auth_id', '', { maxAge: 0, httpOnly: true });
    res.cookie('auth_type', '', { maxAge: 0, httpOnly: true });
    res.redirect('/')
});

router.post('/login', async (req, res) => {
    // Kullanıcı
    let user = await db.dbGetFirst(`SELECT u.id, p.value AS user_type 
                                    FROM user AS u
                                    LEFT JOIN parameter AS p ON p.id = u.user_type_id AND p.is_valid = 1
                                    WHERE u.username = ? AND u.password = ?`, [req.body.username, req.body.password]);
    if (user) {
        res.cookie('auth_id', user.id, { maxAge: 900000000, httpOnly: true });
        res.cookie('auth_type', user.user_type, { maxAge: 900000000, httpOnly: true });
        res.resultRedirect('/');
    } else {
        res.resultError("Bilgiler yanlış!");
        return;
    }
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Kullanıcı oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert(`INSERT INTO user (username, password, email, user_type_id, phone, address, created_by)
                       VALUES (?, ?, ?, ?, ?, ?, ?)`, [req.body.username, req.body.password, req.body.email, req.body.user_type_id, req.body.phone, req.body.address, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi.", "/user/list")
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Kullanıcı güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbUpdate(`UPDATE user SET password = ?, email = ?, user_type_id = ?, phone = ?, address = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.password, req.body.email, req.body.user_type_id, req.body.phone, req.body.address, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;