let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Şubeler
    let branchs = await db.db("SELECT id, is_valid, branch_type_id, branch_code, branch_name FROM branch");

    res.render('branch/list', { title: 'Şubeler', branchs: branchs });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Şube oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    // Şube tipleri
    let branchTypes = await db.db(`SELECT id, is_valid, code, value, description
                                   FROM parameter
                                   WHERE code = 'branch_type' AND is_valid = 1`);
                                
    res.render('branch/add', { title: 'Şube Ekle', branchTypes: branchTypes });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Şube güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    // Şube
    let branch = await db.dbGetFirst(`SELECT id, is_valid, branch_type_id, branch_code, branch_name, phone, address
                                      FROM branch
                                      WHERE id = ?`, [req.params.id]);
    // Şube tipleri
    let branchTypes = await db.db(`SELECT id, is_valid, code, value, description
                                   FROM parameter
                                   WHERE code = 'branch_type' AND is_valid = 1`);
                                   
    // Şubenin çalışanları
    let employees = await db.db(`SELECT e.user_id, e.is_valid, u.username, e.created_at
                              FROM employee AS e
                              INNER JOIN user AS u ON e.user_id = u.id AND u.is_valid = 1
                              WHERE e.branch_id = ? AND e.is_valid = 1`, [branch.id]);
                              
    res.render('branch/edit', { title: 'Şube Güncelle', branch: branch, branchTypes: branchTypes, employees: employees });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Şube oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO branch (branch_type_id, branch_code, branch_name, phone, address, created_by) VALUES (?, ?, ?, ?, ?, ?)", [req.body.branch_type_id, req.body.branch_code, req.body.branch_name, req.body.phone, req.body.address, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/branch/list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Şube güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbUpdate(`UPDATE branch SET branch_code = ?, branch_name = ?, branch_type_id = ?, phone = ?, address = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.branch_code, req.body.branch_name, req.body.branch_type_id, req.body.phone, req.body.address, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;