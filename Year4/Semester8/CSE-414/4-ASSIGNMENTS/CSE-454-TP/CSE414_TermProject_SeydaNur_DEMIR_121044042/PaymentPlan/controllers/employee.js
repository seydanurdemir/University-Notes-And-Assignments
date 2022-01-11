let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/', async (req, res) => {
    res.send('respond with a resource');
});

router.get('/list', async (req, res) => {
    // Çalışanlar
    let employees = await db.db(`SELECT e.user_id, u.username, b.branch_name, e.created_at, e.updated_at
                                 FROM employee AS e
                                 INNER JOIN user AS u ON u.id = e.user_id AND u.is_valid = 1
                                 LEFT JOIN branch AS b ON b.id = e.branch_id AND b.is_valid = 1`);

    res.render('employee/list', { title: 'Çalışanlar', employees: employees });
});

router.get('/add/', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Çalışan oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    // Şubeler
    let branchs = await db.db(`SELECT id, is_valid, branch_name
                               FROM branch
                               WHERE branch_type_id = 20 AND is_valid = 1`);

    // Herhangi bir şubeyenin çalışanı olmayan kullanıcılar
    let users = await db.db(`SELECT u.id, u.is_valid, u.user_type_id, u.username
                             FROM user AS u
                             LEFT JOIN employee AS e ON e.user_id = u.id
                             WHERE e.user_id IS NULL AND u.user_type_id = 2`);

    res.render('employee/add', { title: 'Çalışan Ekle', branchs: branchs, users: users });
});

router.get('/add/:branch_id', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Çalışan oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    // Atanacak olan şube id
    let branch_id = parseInt(req.params.branch_id);
    
    // Şubeler
    let branchs = await db.db(`SELECT id, is_valid, branch_name
                               FROM branch
                               WHERE branch_type_id = 20 AND is_valid = 1`);

    // Herhangi bir şubeyenin çalışanı olmayan kullanıcılar
    let users = await db.db(`SELECT u.id, u.is_valid, u.user_type_id, u.username
                             FROM user AS u
                             LEFT JOIN employee AS e ON e.user_id = u.id
                             WHERE e.user_id IS NULL AND user_type_id = 2 AND e.is_valid = 1`);

    res.render('employee/add', { title: 'Çalışan Ekle', branchs: branchs, users: users, branch_id: branch_id });
});

router.get('/edit/:user_id', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Çalışan güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    // Çalışan
    let employee = await db.dbGetFirst(`SELECT user_id, is_valid, branch_id
                                FROM employee
                                WHERE user_id = ? AND is_valid = 1`, [req.params.user_id]);

    // Şubeler
    let branchs = await db.db(`SELECT id, is_valid, branch_name
                               FROM branch
                               WHERE branch_type_id = 20 AND is_valid = 1`);

    // Kullanıcılar
    let users = await db.db(`SELECT id, is_valid, username
                             FROM user
                             WHERE user_type_id = 2 AND is_valid = 1`);

    res.render('employee/edit', { title: 'Çalışan Güncelle', employee: employee, branchs: branchs, users: users });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Çalışan oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO employee (user_id, branch_id, created_by) VALUES (?, ?, ?)", [req.body.user_id, req.body.branch_id, req.auth_id]);
    
    res.resultOkRedirect("Başarıyla eklendi", "/employee/list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Çalışan güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbUpdate(`UPDATE employee SET branch_id = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE user_id = ?`, [req.body.branch_id, req.auth_id, req.body.user_id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;