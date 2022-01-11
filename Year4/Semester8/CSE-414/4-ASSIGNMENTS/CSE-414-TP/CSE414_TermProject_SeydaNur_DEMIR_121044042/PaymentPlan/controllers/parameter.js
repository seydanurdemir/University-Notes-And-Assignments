let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {
    // Parametreler
    let parameters = await db.db("SELECT id, is_valid, code, value, description FROM parameter");

    res.render('parameter/list', { title: 'Parametreler', parameters: parameters });
});

router.get('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Parametre oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    res.render('parameter/add', { title: 'Parametre Ekle' });
});

router.get('/edit/:id', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Parametre güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    // Parametre
    let parameter = await db.dbGetFirst(`SELECT id, is_valid, code, value, description
                                      FROM parameter
                                      WHERE id = ?`, [req.params.id]);
                              
    res.render('parameter/edit', { title: 'Parametre Güncelle', parameter: parameter });
});

router.post('/add', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Parametre oluşturmak için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbInsert("INSERT INTO parameter (code, value, description, created_by) VALUES (?, ?, ?, ?, ?, ?)", [req.body.code, req.body.value, req.body.description, req.auth_id]);

    res.resultOkRedirect("Başarıyla eklendi", "/parameter/list");
});

router.post('/edit', async (req, res) => {

    if (req.auth_type != 'ADM') {
        res.resultError("Parametre güncellemek için yönetici olmanız gerekmektedir!");
        return;
    }

    await db.dbUpdate(`UPDATE parameter SET code = ?, value = ?, description = ?, updated_by = ?, updated_at = current_timestamp()
                       WHERE id = ?`, [req.body.code, req.body.value, req.body.description, req.auth_id, req.body.id]);

    res.resultOkReload('Başarıyla güncellendi');
});

module.exports = router;