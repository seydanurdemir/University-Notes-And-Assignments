const userTypes = [
    { id: 1, value: "ADM", description: "Yönetici" },
    { id: 2, value: "PER", description: "Personel" },
    { id: 3, value: "POR", description: "Hizmet Sağlayıcı" },
    { id: 4, value: "SUB", description: "Abone" },
];

const subscriberTypes = [
    { id: 5, value: "IND", description: "Bireysel" },
    { id: 6, value: "BUS", description: "İş" },
];

const serviceTypes = [
    { id: 7, value: "ELC", description: "Elektrik" },
    { id: 8, value: "WAT", description: "Su" },
    { id: 9, value: "GAS", description: "Doğal Gaz" },
    { id: 10, value: "TEL", description: "Telekominasyon" },
    { id: 11, value: "GSM", description: "Telefon" },
    { id: 12, value: "TAX", description: "Kamu" },
];

const promiseStatus = [
    { id: 13, value: "WAI", description: "Bekleniyor" },
    { id: 14, value: "SUC", description: "Ödeme Yapıldı" },
    { id: 15, value: "REP", description: "Tekrar Planlanmış" },
    { id: 16, value: "CAN", description: "İptal Edildi" },
    { id: 17, value: "UNS", description: "Başarısız" },
];

const channelTypes = [
    { id: 18, value: "BRA", description: "Şube" },
    { id: 19, value: "OTH", description: "Diğer" },
];

const branchTypes = [
    { id: 20, value: "ONL", description: "Online" },
    { id: 21, value: "INS", description: "Mağaza" },
];

const transactionTypes = [
    { id: 22, value: "CRE", description: "Giden" },
    { id: 23, value: "DEB", description: "Gelen" },
];

const transactionSources = [
    { id: 24, value: "INT", description: "İç" },
    { id: 25, value: "EXT", description: "Dış" },
];

const logTypes = [
    { id: 26, value: "INS", description: "Insert" },
    { id: 27, value: "UPD", description: "Update" },
];

module.exports = {
    userTypes: userTypes,
    subscriberTypes: subscriberTypes,
    serviceTypes: serviceTypes,
    promiseStatus: promiseStatus,
    channelTypes: channelTypes,
    branchTypes: branchTypes,
    transactionSources: transactionSources,
    transactionTypes: transactionTypes,
    logTypes: logTypes
};