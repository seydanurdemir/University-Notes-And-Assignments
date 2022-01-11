let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/list', async (req, res) => {

    // Bildirimler
    let notifications = await db.db(`SELECT *
                                    FROM notification
                                    WHERE is_read = 0 AND user_id = ?`, [req.auth_id]);

    res.render('notification/list', { title: 'Bildirimler', notifications: notifications });
});

router.post('/mark_as_read/', async (req, res) => {

    let result = await db.dbUpdate(`UPDATE notification SET is_read = 1, updated_by = ?, updated_at = current_timestamp()
                                    WHERE id = ?`, [req.auth_id, req.body.id]);

    res.send({ status: 'ok', data: result });
});

module.exports = router;