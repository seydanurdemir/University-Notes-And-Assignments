let createError = require('http-errors');
let express = require('express');
let path = require('path');
let cookieParser = require('cookie-parser');
const exphbs = require('express-handlebars');
let app = express();

app.use(function (req, res, next) {
    res.resultOk = function (message = '', data = []) {
        res.send({ status: 'ok', message: message, data: data });
    };
    res.resultError = function (message = '') {
        res.send({ status: 'error', message: message });
    };
    res.resultReload = function (message = '') {
        res.send({ status: 'reload', message: message });
    };
    res.resultRedirect = function (to = '') {
        res.send({ status: 'redirect', message: to });
    };
    res.resultOkRedirect = function (message = '', url = []) {
        res.send({ status: 'okRedirect', message: message, url: url });
    };
    res.resultOkReload = function (message = '') {
        res.send({ status: 'okReload', message: message });
    };
    res.resultErrorRedirect = function (message = '', url = []) {
        res.send({ status: 'errorRedirect', message: message, url: url, });
    };
    next()
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));

app.engine('hbs', exphbs({
    defaultLayout: "./views/layout",
    extname: "hbs",
    partialsDir: __dirname + '/views/templates',
    layoutsDir: ".",
    helpers: require('./handlebars-helpers')
}));
app.set('view engine', 'hbs');

//app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(async (req, res, next) => {
    req.auth_user = false;
    app.locals.is_logged = false;
    let auth = req.cookies['auth_id'];
    // console.log('Auth -> ', auth);
    res.locals.auth_type = 'NONE';
    if (auth && auth !== '') {
        res.locals.auth_id = auth;
        res.locals.is_logged = true;
        res.locals.auth_type = req.cookies['auth_type'];
        req.auth_id = auth;
        req.auth_type = res.locals.auth_type;
    }
    next()
});

let indexController = require('./controllers/index');
app.use('/', indexController);

let billController = require('./controllers/bill');
app.use('/bill', billController);

let branchController = require('./controllers/branch');
app.use('/branch', branchController);

let cardController = require('./controllers/card');
app.use('/card', cardController);

let employeeController = require('./controllers/employee');
app.use('/employee', employeeController);

let notificationController = require('./controllers/notification');
app.use('/notification', notificationController);

let parameterController = require('./controllers/parameter');
app.use('/parameter', parameterController);

let paymentController = require('./controllers/payment');
app.use('/payment', paymentController);

let promiseController = require('./controllers/promise');
app.use('/promise', promiseController);

let providerController = require('./controllers/provider');
app.use('/provider', providerController);

let subscriptionController = require('./controllers/subscription');
app.use('/subscription', subscriptionController);

let transactionController = require('./controllers/transaction');
app.use('/transaction', transactionController);

let userController = require('./controllers/user');
app.use('/user', userController);

let walletController = require('./controllers/wallet');
app.use('/wallet', walletController);

app.render = (function (render) {
    return function (view, options, callback) {
        options['basename'] = view;
        return render.call(this, view, options, callback);
    };
})(app.render);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);

    res.render('error', { title: err });
});

module.exports = app;