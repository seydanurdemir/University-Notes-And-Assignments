let constants = require("./constant");

module.exports = {
    ifeq: function (a, b, options) {
        if (a === b) {
            return options.fn(this);
        }
        return options.inverse(this);
    },
    ifCond: function (v1, operator, v2, options) {
        switch (operator) {
            case '==':
                return (v1 == v2) ? options.fn(this) : options.inverse(this);
            case '===':
                return (v1 === v2) ? options.fn(this) : options.inverse(this);
            case '!=':
                return (v1 != v2) ? options.fn(this) : options.inverse(this);
            case '!==':
                return (v1 !== v2) ? options.fn(this) : options.inverse(this);
            case '<':
                return (v1 < v2) ? options.fn(this) : options.inverse(this);
            case '<=':
                return (v1 <= v2) ? options.fn(this) : options.inverse(this);
            case '>':
                return (v1 > v2) ? options.fn(this) : options.inverse(this);
            case '>=':
                return (v1 >= v2) ? options.fn(this) : options.inverse(this);
            case '&&':
                return (v1 && v2) ? options.fn(this) : options.inverse(this);
            case '||':
                return (v1 || v2) ? options.fn(this) : options.inverse(this);
            default:
                return options.inverse(this);
        }
    },
    each_when: function (list, k, v, opts) {
        console.log(arguments);
        var i, result = '';
        for (i = 0; i < list.length; ++i)
            if (list[i][k] == v)
                result = result + opts.fn(list[i]);
        return result;
    },
    userType: function (a) {
        var type = constants.userTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    subsciberType: function (a) {
        var type = constants.subsciberTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    serviceType: function (a) {
        var type = constants.serviceTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    promiseStatus: function (a) {
        var status = constants.promiseStatus.filter(x => x.id == a);
        return status === undefined || status[0] === undefined ? "" : status[0].description;
    },
    channelType: function (a) {
        var type = constants.channelTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    branchType: function (a) {
        var type = constants.branchTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    transactionType: function (a) {
        var type = constants.transactionTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    transactionSource: function (a) {
        var source = constants.transactionSources.filter(x => x.id == a);
        return source === undefined || source[0] === undefined ? "" : source[0].description;
    },
    logType: function (a) {
        var type = constants.logTypes.filter(x => x.id == a);
        return type === undefined || type[0] === undefined ? "" : type[0].description;
    },
    isEmpty: function (a, options) {
        if (a.length === 0) {
            return options.fn(this);
        }
        return options.inverse(this);
    },
    isNotEmpty: function (a, options) {
        console.log(a);
        if (a.length > 0) {
            return options.fn(this);
        }
        return options.inverse(this);
    },
    formatCardNumber: function (value) {
        if (value === undefined)
            return value;

        const regex = /^(\d{0,4})(\d{0,4})(\d{0,4})(\d{0,4})$/g
        const onlyNumbers = String(value).replace(/[^\d]/g, '')

        return onlyNumbers.replace(regex, (regex, $1, $2, $3, $4) =>
            [$1, $2, $3, $4].filter(group => !!group).join('-')
        );
    },
    formatDate: function (value) {
        if (value === undefined)
            return value;

        var date = new Date(value);
        var day = ("0" + date.getDate()).slice(-2);
        var month = ("0" + (date.getMonth() + 1)).slice(-2);
        return date.getFullYear() + "-" + (month) + "-" + (day);
    }
};