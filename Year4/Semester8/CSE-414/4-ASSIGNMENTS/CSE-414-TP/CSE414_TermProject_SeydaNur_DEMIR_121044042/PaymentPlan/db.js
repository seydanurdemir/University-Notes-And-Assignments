'use strict';
const mysql = require('mysql');

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    dateStrings: true,
    database: 'paymentplandb',
    timezone: 'UTC+3'
});

connection.connect(function (err) {
    if (err) {
        console.log("Mysql cannot connect !");
        throw err;
    } else {
        console.log("Mysql connected !");
    }

});
connection.on('error', function () {
    console.log("MYSQL error...");
});


module.exports = connection;

module.exports.db = function (query, data = []) {
    return new Promise(function (resolve, reject) {
        connection.query(query, data, (err, res) => {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(res);
            }
        })
    });
};

module.exports.dbGetFirst = function (query, data = []) {
    return new Promise(function (resolve, reject) {
        connection.query(query, data, (err, res) => {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(res[0]);
            }
        })
    });
};

module.exports.dbUpdate = function (query, data = []) {
    return new Promise(function (resolve, reject) {
        connection.query(query, data, (err, res) => {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(res.affectedRows);
            }
        })
    });
};

module.exports.dbInsert = function (query, data = []) {
    return new Promise(function (resolve, reject) {
        connection.query(query, data, (err, res) => {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(res.insertId);
            }
        })
    });
};


module.exports.startTransaction = function () {
    return new Promise(function (resolve, reject) {
        connection.beginTransaction(function (err) {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(true);
            }
        });
    });
};

module.exports.rollbackTransaction = function () {
    return new Promise(function (resolve, reject) {
        connection.rollback(function (err) {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(true);
            }
        });
    });
};

module.exports.commitTransaction = function () {
    return new Promise(function (resolve, reject) {
        connection.commit(function (err) {
            if (err) {
                console.log(err);
                resolve(null);
            } else {
                resolve(true);
            }
        });
    });
};