let express = require('express');
let router = express.Router();
let db = require('../db');

router.get('/', async (req, res) => {
    let bills;

    if (['ADM', 'EMP'].includes(req.auth_type)) {
        // Tüm ödenmemiş faturalar
        bills = await db.db("SELECT * FROM v_unpaids");
    } else if (req.auth_type == 'SUB') {
        // Abonenin ödenmemiş faturaları
        bills = await db.db(`SELECT *
                             FROM v_unpaids
                             WHERE user_id = ?`, [req.auth_id]);        
    } else {
        // Sağlayıcının ödenmemiş faturaları
        bills = await db.db(`SELECT *
                             FROM v_unpaids AS u
                             INNER JOIN subscription AS sub ON sub.id = u.subscription_id AND sub.is_valid = 1
                             INNER JOIN provider ON provider.id = sub.provider_id AND provider.is_valid = 1 AND provider.user_id = ?`, [req.auth_id]);
    }

    res.render('index', { title: 'Ödenmemiş Faturalar', bills: bills });
});

module.exports = router;