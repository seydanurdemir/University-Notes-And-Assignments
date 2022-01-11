-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: paymentplandb
-- Source Schemata: paymentplandb
-- Created: Sun May 30 03:56:12 2021
-- Workbench Version: 8.0.25
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- ----------------------------------------------------------------------------
-- Schema paymentplandb
-- ----------------------------------------------------------------------------
DROP DATABASE IF EXISTS `paymentplandb`;
CREATE DATABASE IF NOT EXISTS `paymentplandb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `paymentplandb`;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `bill`
--

CREATE TABLE `bill` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `subscription_id` int(11) NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `document` longblob NOT NULL,
  `first_read_date` datetime DEFAULT NULL,
  `last_read_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `bill`
--
DELIMITER $$
CREATE TRIGGER `send_bill_creating_notification` AFTER INSERT ON `bill` FOR EACH ROW BEGIN
    SET @subscription_number := (SELECT CONCAT(LPAD(sub.provider_id, 4, '0000'), LPAD(sub.user_id, 4, '0000')) FROM subscription AS sub WHERE sub.id = NEW.subscription_id);
    SET @bill_number := (SELECT CONCAT('BILL', LPAD(NEW.subscription_id, 4, '0000'), LPAD(NEW.id, 4, '0000')));
    SET @user_id := (SELECT user_id FROM subscription WHERE id = NEW.subscription_id);
    SET @username := (SELECT username FROM user WHERE id = @user_id);

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@subscription_number, ' numaralı aboneliğiniz için yeni faturanız oluşturuldu. Fatura No: ', @bill_number, ', Tutar: ', NEW.amount), @user_id, NEW.created_by);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `branch`
--

CREATE TABLE `branch` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `branch_type_id` int(11) NOT NULL,
  `branch_code` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `branch_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tablo döküm verisi `branch`
--

INSERT INTO `branch` (`id`, `is_valid`, `branch_type_id`, `branch_code`, `branch_name`, `phone`, `address`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(1, 1, 19, 'PAYONL', 'Online', '', '', 1, NULL, '2021-05-13 20:06:28', NULL),
(2, 1, 20, 'PAYIST', 'İstanbul', '', '', 1, NULL, '2021-05-13 20:06:28', NULL),
(3, 1, 20, 'PAYANK', 'Ankara', '', '', 1, NULL, '2021-05-13 20:06:28', NULL),
(4, 1, 20, 'PAYKOC', 'Kocaeli', '', '', 1, NULL, '2021-05-13 20:06:28', NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `card`
--

CREATE TABLE `card` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `user_id` int(11) NOT NULL,
  `card_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `card_number` int(16) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `card`
--
DELIMITER $$
CREATE TRIGGER `create_card` AFTER INSERT ON `card` FOR EACH ROW BEGIN
   INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(CONCAT(LEFT(card_number, 4), '-', SUBSTR(card_number, 6, 2),'**-****-**', RIGHT(card_number, 4)), ' numaralı kartınız eklenmiştir.'), NEW.user_id, NEW.created_by);
END
$$
DELIMITER ;

--
-- Tablo için tablo yapısı `employee`
--

CREATE TABLE `employee` (
  `user_id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `branch_id` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `text` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT 0,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `parameter`
--

CREATE TABLE `parameter` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `code` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tablo döküm verisi `parameter`
--

INSERT INTO `parameter` (`id`, `is_valid`, `code`, `value`, `description`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(1, 1, 'user_type', 'ADM', 'Administrator', 1, NULL, '2021-05-12 23:16:57', NULL),
(2, 1, 'user_type', 'EMP', 'Employee', 1, NULL, '2021-05-12 23:16:57', NULL),
(3, 1, 'user_type', 'PRO', 'Provider', 1, NULL, '2021-05-12 23:16:57', NULL),
(4, 1, 'user_type', 'SUB', 'Subscriber', 1, NULL, '2021-05-12 23:16:57', NULL),
(5, 1, 'subscriber_type', 'IND', 'Individual', 1, NULL, '2021-05-12 23:16:57', NULL),
(6, 1, 'subscriber_type', 'BUS', 'Business', 1, NULL, '2021-05-12 23:16:57', NULL),
(7, 1, 'service_type', 'ELC', 'Electric', 1, NULL, '2021-05-12 23:16:57', NULL),
(8, 1, 'service_type', 'WAT', 'Water', 1, NULL, '2021-05-12 23:16:57', NULL),
(9, 1, 'service_type', 'GAS', 'Gas', 1, NULL, '2021-05-12 23:16:57', NULL),
(10, 1, 'service_type', 'TEL', 'Telecommunication', 1, NULL, '2021-05-12 23:16:57', NULL),
(11, 1, 'service_type', 'GSM', 'Cell', 1, NULL, '2021-05-12 23:16:57', NULL),
(12, 1, 'service_type', 'TAX', 'Government', 1, NULL, '2021-05-12 23:16:57', NULL),
(13, 1, 'promise_status', 'WAI', 'Waiting', 1, NULL, '2021-05-24 11:14:33', NULL),
(14, 1, 'promise_status', 'SUC', 'Succeeded', 1, NULL, '2021-05-12 23:16:57', NULL),
(15, 1, 'promise_status', 'REP', 'Repeated', 1, NULL, '2021-05-12 23:16:57', NULL),
(16, 1, 'promise_status', 'CAN', 'Canceled', 1, NULL, '2021-05-12 23:16:57', NULL),
(17, 1, 'promise_status', 'UNS', 'Unsucceeded', 1, NULL, '2021-05-12 23:16:57', NULL),
(18, 1, 'channel_type', 'BRA', 'Branch', 1, NULL, '2021-05-12 23:16:57', NULL),
(19, 1, 'channel_type', 'OTH', 'Other', 1, NULL, '2021-05-12 23:16:57', NULL),
(20, 1, 'branch_type', 'ONL', 'Online', 1, NULL, '2021-05-12 23:16:57', NULL),
(21, 1, 'branch_type', 'INS', 'In store', 1, NULL, '2021-05-12 23:16:57', NULL),
(22, 1, 'transaction_type', 'CRE', 'Credit', 1, NULL, '2021-05-12 23:16:57', NULL),
(23, 1, 'transaction_type', 'DEB', 'Debit', 1, NULL, '2021-05-12 23:16:57', NULL),
(24, 1, 'transaction_source', 'INT', 'Internal', 1, NULL, '2021-05-14 18:30:48', NULL),
(25, 1, 'transaction_source', 'EXT', 'External', 1, NULL, '2021-05-12 23:16:57', NULL),
(26, 1, 'log_type', 'INS', 'Insert', 1, NULL, '2021-05-12 23:16:57', NULL),
(27, 1, 'log_type', 'UPD', 'Update', 1, NULL, '2021-05-12 23:16:57', NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `payment`
--

CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `document` longblob NOT NULL,
  `bill_id` int(11) DEFAULT NULL,
  `transaction_sequence_number` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `payment`
--
DELIMITER $$
CREATE TRIGGER `send_payment_notification` AFTER INSERT ON `payment` FOR EACH ROW BEGIN
    SET @bill_number := (SELECT CONCAT('BILL', LPAD(bill.subscription_id, 7, '0000000'), LPAD(bill.id, 4, '0000')) FROM bill WHERE id = NEW.bill_id);
    SET @subscriber_user_id := (SELECT sub.user_id FROM bill INNER JOIN subscription AS sub ON sub.id = bill.subscription_id WHERE bill.id = NEW.bill_id);
    SET @provider_user_id := (SELECT provider.user_id FROM bill INNER JOIN subscription AS sub ON sub.id = bill.subscription_id INNER JOIN provider ON provider.id = sub.provider_id WHERE bill.id = NEW.bill_id);
    SET @succeeded_status_id := (SELECT id FROM parameter WHERE code = 'promise_status' AND value = 'SUC');
	
    UPDATE promise SET status_id = @succeeded_status_id WHERE bill_id = NEW.bill_id;

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@bill_number, ' numaralı faturanızın ödemesi yapılmıştır. Teşekkür ederiz.'), @subscriber_user_id, NEW.created_by);

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@bill_number, ' numaralı faturanız abone tarafından ödenmiştir.'), @provider_user_id, NEW.created_by);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `promise`
--

CREATE TABLE `promise` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `bill_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `promise_date` date NOT NULL,
  `due_date` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `promise`
--
DELIMITER $$
CREATE TRIGGER `send_promise_adding_notification` AFTER INSERT ON `promise` FOR EACH ROW BEGIN
    SET @bill_number := (SELECT CONCAT('BILL', LPAD(bill.subscription_id, 7, '0000000'), LPAD(bill.id, 4, '0000')) FROM bill WHERE id = NEW.bill_id);
    SET @user_id := (SELECT sub.user_id FROM bill INNER JOIN subscription AS sub ON sub.id = bill.subscription_id WHERE bill.id = NEW.bill_id);

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT('Ödeme sözünüz eklenmiştir. Detaylar; Fatura No: ', @bill_number, ', Söz Verilen Ödeme Tarihi: ', NEW.due_date, ', Söz Verilen Tarihi: ', NEW.promise_date), @user_id, NEW.created_by);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `send_promise_updating_notification` AFTER UPDATE ON `promise` FOR EACH ROW BEGIN
    SET @bill_number := (SELECT CONCAT('BILL', LPAD(bill.subscription_id, 7, '0000000'), LPAD(bill.id, 4, '0000')) FROM bill WHERE id = NEW.bill_id);
    SET @user_id := (SELECT sub.user_id FROM bill INNER JOIN subscription AS sub ON sub.id = bill.subscription_id WHERE bill.id = NEW.bill_id);

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT('Ödeme sözünüz güncellenmiştir. Detaylar; Fatura No: ', @bill_number, ', Söz Verilen Ödeme Tarihi: ', NEW.due_date, ', Söz Verilen Tarihi: ', NEW.promise_date), @user_id, NEW.created_by);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `provider`
--

CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `user_id` int(11) DEFAULT NULL,
  `service_type_id` int(11) NOT NULL,
  `corporate_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `sequence`
--

CREATE TABLE `sequence` (
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `increment` int(11) NOT NULL DEFAULT 1,
  `min_value` int(11) NOT NULL DEFAULT 1,
  `max_value` bigint(20) DEFAULT 1,
  `cur_value` bigint(20) DEFAULT 1,
  `cycle` tinyint(1) NOT NULL DEFAULT 0
) ;

--
-- Tablo döküm verisi `sequence`
--

INSERT INTO `sequence` (`name`, `increment`, `min_value`, `max_value`, `cur_value`, `cycle`) VALUES
('trn_seq', 1, 1000000, 10000000, 1000001, 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `subscription`
--

CREATE TABLE `subscription` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `user_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `end_date` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `subscription`
--
DELIMITER $$
CREATE TRIGGER `send_subscription_creating_notification` AFTER INSERT ON `subscription` FOR EACH ROW BEGIN
    SET @subscription_number := (SELECT CONCAT(LPAD(NEW.provider_id, 4, '0000'), LPAD(NEW.user_id, 4, '0000')));
    SET @provider_user_id := (SELECT user_id FROM provider WHERE id = NEW.provider_id);
    SET @corporate_name := (SELECT corporate_name FROM provider WHERE id = NEW.provider_id);
    SET @username := (SELECT username FROM user WHERE id = NEW.user_id);

    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@corporate_name, ' şirketi için aboneliğiniz eklenmiştir. Abone No: ', @subscription_number), NEW.user_id, NEW.created_by);
    INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@username, ' adlı kullanıcı aboneniz olarak eklenmiştir. Abone No: ', @subscription_number), @provider_user_id, NEW.created_by);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `transaction_type_id` int(11) NOT NULL,
  `transaction_source_id` int(11) NOT NULL,
  `channel_type_id` int(11) NOT NULL,
  `transaction_sequence_number` int(11) NOT NULL,
  `source_id` int(11) NOT NULL,
  `destination_id` int(11) NOT NULL,
  `transaction_amount` decimal(16,2) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tetikleyiciler `transaction`
--
DELIMITER $$
CREATE TRIGGER `send_transfer_notification` AFTER INSERT ON `transaction` FOR EACH ROW BEGIN
    SET @source := (SELECT CASE
		WHEN NEW.transaction_source_id = 24 AND NEW.transaction_type_id = 22 THEN
			(SELECT LPAD(`sw`.`user_id`, 11, '10000000000') FROM `wallet` AS sw WHERE sw.id = NEW.source_id)
		WHEN NEW.transaction_source_id = 25 AND NEW.transaction_type_id = 22 THEN
			(SELECT CONCAT(LEFT(sc.card_number, 4), '-', SUBSTR(sc.card_number, 6, 2),'**-****-**', RIGHT(sc.card_number, 4)) FROM `card` AS sc WHERE sc.id = NEW.source_id)
	END);
    SET @destination := (SELECT CASE
		WHEN NEW.transaction_source_id = 24 AND NEW.transaction_type_id = 23 THEN
			(SELECT LPAD(`dw`.`user_id`, 11, '10000000000') FROM `wallet` AS dw WHERE dw.id = NEW.destination_id)
		WHEN NEW.transaction_source_id = 25 AND NEW.transaction_type_id = 23 THEN
			(SELECT CONCAT(LEFT(dc.card_number, 4), '-', SUBSTR(dc.card_number, 6, 2),'**-****-**', RIGHT(dc.card_number, 4)) FROM `card` AS dc WHERE dc.id = NEW.destination_id)
	END);
    SET @source_user_id := (SELECT CASE
		WHEN NEW.transaction_source_id = 24 AND NEW.transaction_type_id = 22 THEN
			(SELECT user_id FROM `wallet` AS sw WHERE sw.id = NEW.source_id)
		WHEN NEW.transaction_source_id = 25 AND NEW.transaction_type_id = 22 THEN
			(SELECT user_id FROM `card` AS sc WHERE sc.id = NEW.source_id)
	END);
    SET @destination_user_id := (SELECT CASE
		WHEN NEW.transaction_source_id = 24 AND NEW.transaction_type_id = 23 THEN
			(SELECT user_id FROM `wallet` AS dw WHERE dw.id = NEW.destination_id)
		WHEN NEW.transaction_source_id = 25 AND NEW.transaction_type_id = 23 THEN
			(SELECT user_id FROM `card` AS dc WHERE dc.id = NEW.destination_id)
	END);
	
    IF NEW.transaction_type_id = 22 THEN
		INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@source, ' cüzdan/kart-ınızdan ', NEW.transaction_amount, ' ₺ tutarında para gönderilmiştir.'), @source_user_id, NEW.created_by);
	ELSE IF NEW.transaction_type_id = 23 THEN
		INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT(@destination, ' cüzdanınıza ', NEW.transaction_amount, ' ₺ tutarında para geldi.'), @destination_user_id, NEW.created_by);
	 END IF;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `user_type_id` int(11) DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
) ;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `is_valid`, `user_type_id`, `username`, `password`, `email`, `phone`, `address`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'admin', '123456', 'admin@admin.com', '543-210-1122', 'test', 1, NULL, '2021-05-13 20:06:50', NULL);

--
-- Tetikleyiciler `user`
--
DELIMITER $$
CREATE TRIGGER `create_wallet` AFTER INSERT ON `user` FOR EACH ROW BEGIN
    SET @sub_type_id := (SELECT id FROM parameter WHERE code = 'user_type' AND value = 'SUB');
    SET @prv_type_id := (SELECT id FROM parameter WHERE code = 'user_type' AND value = 'PRO');
	
    IF NEW.user_type_id = @sub_type_id OR NEW.user_type_id = @prv_type_id THEN
		INSERT INTO wallet(`user_id`, `created_by`) VALUES (NEW.id, NEW.created_by);
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `prevent_updating_user_type` AFTER UPDATE ON `user` FOR EACH ROW BEGIN
	IF NEW.user_type_id != OLD.user_type_id THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Kullanıcı tipi değiştirilemez!';
	END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `send_user_creating_notification` AFTER INSERT ON `user` FOR EACH ROW BEGIN
   INSERT INTO notification (text, user_id, created_by) VALUES (CONCAT('Sn. ', NEW.username, ', kullanıcı hesabınız oluşturulmuştur.'), NEW.id, NEW.created_by);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `wallet`
--

CREATE TABLE `wallet` (
  `id` int(11) NOT NULL,
  `is_valid` tinyint(1) DEFAULT 1,
  `user_id` int(11) NOT NULL,
  `balance` decimal(6,2) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP()
);

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_creditors`
--
DROP TABLE IF EXISTS `v_creditors`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_creditors`  AS  select `subscription`.`provider_id` AS `provider_id`,max(`provider`.`corporate_name`) AS `corporate_name`,count(`bill`.`id`) AS `total_count`,sum(`bill`.`amount`) AS `total_amount` from (((`bill` join `subscription` on(`subscription`.`id` = `bill`.`subscription_id`)) join `provider` on(`provider`.`id` = `subscription`.`provider_id`)) left join `payment` on(`payment`.`bill_id` = `bill`.`id`)) where `payment`.`id` is null group by `subscription`.`provider_id` ;

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_debtors`
--
DROP TABLE IF EXISTS `v_debtors`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_debtors`  AS  select `subscription`.`user_id` AS `user_id`,max(`user`.`username`) AS `username`,count(`bill`.`id`) AS `total_count`,sum(`bill`.`amount`) AS `total_amount` from (((`bill` join `subscription` on(`subscription`.`id` = `bill`.`subscription_id`)) join `user` on(`user`.`id` = `subscription`.`user_id`)) left join `payment` on(`payment`.`bill_id` = `bill`.`id`)) where `payment`.`id` is null group by `subscription`.`user_id` ;

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_late_paids`
--
DROP TABLE IF EXISTS `v_late_paids`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_late_paids`  AS  select `payment`.`id` AS `payment_id`,`payment`.`bill_id` AS `bill_id`,concat('BILL',lpad(`bill`.`subscription_id`,4,'0000'),lpad(`bill`.`id`,4,'0000')) AS `bill_number`,`transaction`.`transaction_sequence_number` AS `transaction_sequence_number`,`bill`.`subscription_id` AS `subscription_id`,concat(lpad(`sub`.`provider_id`,4,'0000'),lpad(`sub`.`user_id`,4,'0000')) AS `subscription_number`,`sub`.`provider_id` AS `provider_id`,`provider`.`corporate_name` AS `corporate_name`,`transaction`.`transaction_amount` AS `transaction_amount` from ((((`payment` join `transaction` on(`transaction`.`transaction_sequence_number` = `payment`.`transaction_sequence_number`)) join `bill` on(`bill`.`id` = `payment`.`bill_id`)) join `subscription` `sub` on(`bill`.`subscription_id` = `sub`.`id`)) join `provider` on(`provider`.`id` = `sub`.`provider_id`)) where `bill`.`due_date` < `transaction`.`created_at` and `payment`.`is_valid` = 1 group by `payment`.`bill_id` ;

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_overdue_bills`
--
DROP TABLE IF EXISTS `v_overdue_bills`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_overdue_bills`  AS  select `bill`.`id` AS `bill_id`,concat('BILL',lpad(`bill`.`subscription_id`,4,'0000'),lpad(`bill`.`id`,4,'0000')) AS `bill_number`,`bill`.`first_read_date` AS `first_read_date`,`bill`.`last_read_date` AS `last_read_date`,`bill`.`start_date` AS `start_date`,`bill`.`due_date` AS `due_date`,`bill`.`subscription_id` AS `subscription_id`,concat(lpad(`sub`.`provider_id`,4,'0000'),lpad(`sub`.`user_id`,4,'0000')) AS `subscription_number`,`sub`.`provider_id` AS `provider_id`,`provider`.`corporate_name` AS `corporate_name`,`sub`.`user_id` AS `user_id`,`user`.`username` AS `username`,`bill`.`amount` AS `bill_amount` from ((((`bill` join `subscription` `sub` on(`sub`.`id` = `bill`.`subscription_id`)) join `provider` on(`provider`.`id` = `sub`.`provider_id`)) join `user` on(`user`.`id` = `sub`.`user_id`)) left join `payment` on(`payment`.`bill_id` = `bill`.`id`)) where `payment`.`id` is null and `bill`.`due_date` < CURRENT_TIMESTAMP() ;

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_transactions`
--
DROP TABLE IF EXISTS `v_transactions`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transactions`  AS  select `t1`.`transaction_sequence_number` AS `transaction_sequence_number`,`t1`.`transaction_amount` AS `transaction_amount`,`t1`.`channel_type_id` AS `channel_type_id`,`t1`.`source_id` AS `source_id`,case when `t1`.`transaction_source_id` = 24 and `t1`.`transaction_type_id` = 22 then (select lpad(`sw`.`user_id`,11,'10000000000') from `wallet` `sw` where `sw`.`id` = `t1`.`source_id`) when `t1`.`transaction_source_id` = 25 and `t1`.`transaction_type_id` = 22 then (select concat(left(`sc`.`card_number`,4),'-****-****-**',right(`sc`.`card_number`,2)) from `card` `sc` where `sc`.`id` = `t1`.`source_id`) end AS `source`,`t2`.`destination_id` AS `destination_id`,case when `t2`.`transaction_source_id` = 24 and `t2`.`transaction_type_id` = 23 then (select lpad(`dw`.`user_id`,11,'10000000000') from `wallet` `dw` where `dw`.`id` = `t2`.`destination_id`) when `t2`.`transaction_source_id` = 25 and `t2`.`transaction_type_id` = 23 then (select concat(left(`dc`.`card_number`,4),'-****-****-**',right(`dc`.`card_number`,2)) from `card` `dc` where `dc`.`id` = `t2`.`destination_id`) end AS `destination`,`t1`.`created_by` AS `created_by`,`t1`.`updated_by` AS `updated_by`,`t1`.`created_at` AS `created_at`,`t1`.`updated_at` AS `updated_at`,row_number() over ( partition by `t1`.`transaction_sequence_number`) AS `row_num` from (`transaction` `t1` join `transaction` `t2`) where `t1`.`transaction_sequence_number` = `t2`.`transaction_sequence_number` and `t1`.`id` <> `t2`.`id` group by `t1`.`transaction_sequence_number` having `source` is not null ;

-- --------------------------------------------------------

--
-- Görünüm yapısı `v_unpaids`
--
DROP TABLE IF EXISTS `v_unpaids`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_unpaids`  AS  select `bill`.`id` AS `bill_id`,concat('BILL',lpad(`bill`.`subscription_id`,4,'0000'),lpad(`bill`.`id`,4,'0000')) AS `bill_number`,cast(`bill`.`first_read_date` as date) AS `first_read_date`,cast(`bill`.`last_read_date` as date) AS `last_read_date`,cast(`bill`.`start_date` as date) AS `start_date`,cast(`bill`.`due_date` as date) AS `due_date`,`bill`.`subscription_id` AS `subscription_id`,concat(lpad(`sub`.`provider_id`,4,'0000'),lpad(`sub`.`user_id`,4,'0000')) AS `subscription_number`,`sub`.`provider_id` AS `provider_id`,`provider`.`corporate_name` AS `corporate_name`,`sub`.`user_id` AS `user_id`,`user`.`username` AS `username`,`bill`.`amount` AS `bill_amount` from ((((`bill` join `subscription` `sub` on(`bill`.`subscription_id` = `sub`.`id`)) join `provider` on(`provider`.`id` = `sub`.`provider_id`)) join `user` on(`user`.`id` = `sub`.`user_id`)) left join `payment` on(`bill`.`id` = `payment`.`bill_id`)) where `payment`.`id` is null ;

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`id`),
  ADD INDEX `subscription_id` (`subscription_id` ASC),
  ADD CONSTRAINT `bill_ibfk_1`
    FOREIGN KEY (`subscription_id`)
    REFERENCES `paymentplandb`.`subscription` (`id`);

--
-- Tablo için indeksler `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `branch_type_id_branch_code` (`branch_type_id` ASC, `branch_code` ASC),
  ADD CONSTRAINT `branch_ibfk_1`
    FOREIGN KEY (`branch_type_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`);

--
-- Tablo için indeksler `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `user_id_card_number` (`user_id` ASC, `card_number` ASC),
  ADD CONSTRAINT `card_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`);

--
-- Tablo için indeksler `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`user_id`, `branch_id`),
  ADD CONSTRAINT `employee_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`),
  ADD CONSTRAINT `employee_ibfk_2`
    FOREIGN KEY (`branch_id`)
    REFERENCES `paymentplandb`.`branch` (`id`);

--
-- Tablo için indeksler `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD INDEX `user_id` (`user_id` ASC),
  ADD CONSTRAINT `notification_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`);

--
-- Tablo için indeksler `parameter`
--
ALTER TABLE `parameter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `code` (`code` ASC, `value` ASC);

--
-- Tablo için indeksler `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `transaction_sequence_number` (`transaction_sequence_number` ASC)
  ADD CONSTRAINT `payment_ibfk_1` 
    FOREIGN KEY (`transaction_sequence_number`)
    REFERENCES `paymentplandb`.`transaction` (`transaction_sequence_number`)
  ADD CONSTRAINT `payment_ibfk_2`
    FOREIGN KEY (`bill_id`)
    REFERENCES `paymentplandb`.`bill` (`id`);

--
-- Tablo için indeksler `promise`
--
ALTER TABLE `promise`
  ADD PRIMARY KEY (`id`),
  ADD INDEX `bill_id` (`bill_id` ASC),
  ADD INDEX `status_id` (`status_id` ASC),
  ADD CONSTRAINT `promise_ibfk_1`
    FOREIGN KEY (`bill_id`)
    REFERENCES `paymentplandb`.`bill` (`id`),
  ADD CONSTRAINT `promise_ibfk_2`
    FOREIGN KEY (`status_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`);

--
-- Tablo için indeksler `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `service_type_id_corporate_name` (`service_type_id` ASC, `corporate_name` ASC),
  ADD INDEX `user_id` (`user_id` ASC),
  ADD CONSTRAINT `provider_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`),
  ADD CONSTRAINT `provider_ibfk_2`
    FOREIGN KEY (`service_type_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`);

--
-- Tablo için indeksler `sequence`
--
ALTER TABLE `sequence`
  ADD PRIMARY KEY (`name`);

--
-- Tablo için indeksler `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `user_id_provider_id` (`user_id` ASC, `provider_id` ASC),
  ADD INDEX `provider_id` (`provider_id` ASC),
  ADD CONSTRAINT `subscription_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`),
  ADD CONSTRAINT `subscription_ibfk_2`
    FOREIGN KEY (`provider_id`)
    REFERENCES `paymentplandb`.`provider` (`id`);

--
-- Tablo için indeksler `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD INDEX `transaction_type_id` (`transaction_type_id` ASC),
  ADD INDEX `transaction_source_id` (`transaction_source_id` ASC),
  ADD INDEX `channel_type_id` (`channel_type_id` ASC),
  ADD CONSTRAINT `transaction_ibfk_1`
    FOREIGN KEY (`transaction_type_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`),
  ADD CONSTRAINT `transaction_ibfk_2`
    FOREIGN KEY (`transaction_source_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`),
  ADD CONSTRAINT `transaction_ibfk_3`
    FOREIGN KEY (`channel_type_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `username` (`username` ASC),
  ADD UNIQUE INDEX `email` (`email` ASC),
  ADD UNIQUE INDEX `username_email` (`username` ASC, `email` ASC),
  ADD INDEX `user_type_id` (`user_type_id` ASC),
  ADD CONSTRAINT `user_ibfk_1`
    FOREIGN KEY (`user_type_id`)
    REFERENCES `paymentplandb`.`parameter` (`id`);

--
-- Tablo için indeksler `wallet`
--
ALTER TABLE `wallet`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `user_id` (`user_id` ASC),
  ADD CONSTRAINT `wallet_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymentplandb`.`user` (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `bill`
--
ALTER TABLE `bill`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `branch`
--
ALTER TABLE `branch`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `card`
--
ALTER TABLE `card`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `parameter`
--
ALTER TABLE `parameter`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- Tablo için AUTO_INCREMENT değeri `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `promise`
--
ALTER TABLE `promise`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `provider`
--
ALTER TABLE `provider`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `subscription`
--
ALTER TABLE `subscription`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Tablo için AUTO_INCREMENT değeri `wallet`
--
ALTER TABLE `wallet`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;


DELIMITER $$
--
-- Yordamlar
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_transaction` (IN `transaction_type_id` INT, IN `transaction_source_id` INT, IN `channel_type_id` INT, IN `source_id` INT, IN `destination_id` INT, IN `transaction_amount` DECIMAL(6,2), IN `created_by` INT, IN `transaction_sequence_number` INT, OUT `result_val` TINYINT(1))
BEGIN
	DECLARE transaction_type VARCHAR(255);
	DECLARE transaction_source VARCHAR(255);
	DECLARE source_balance VARCHAR(255);
	DECLARE source_wallet_id INT;
	DECLARE credit_type_id INT;
	DECLARE debit_type_id INT;
    
  SELECT value INTO transaction_type FROM parameter WHERE id = transaction_type_id LIMIT 1;
  SELECT value INTO transaction_source FROM parameter WHERE id = transaction_source_id LIMIT 1;
  
  SELECT id INTO credit_type_id FROM parameter WHERE code = 'transaction_type' AND value = 'CRE';
  SELECT id INTO debit_type_id FROM parameter WHERE code = 'transaction_type' AND value = 'DEB';
    
	IF transaction_type = 'CRE' THEN -- Para çekme
		IF transaction_source = 'INT' THEN -- İç kanal üzerinden yapılan transfer cüzdanlar arasında yapılabilir
			SELECT COALESCE(balance, 0) INTO source_balance FROM wallet WHERE id = source_id AND is_valid = 1;
			IF source_balance < transaction_amount THEN -- Yeterli bakiye kontrolü
				SET result_val = -1;
			END IF;
            
			UPDATE wallet SET balance -= transaction_amount WHERE id = source_id;
		-- ELSE -- Dış kanal transferler karttan yapılır (bakiyeye bakamayız)
		END IF;
        	
		INSERT INTO transaction (`transaction_type_id`, `transaction_source_id`, `channel_type_id`, `transaction_sequence_number`, `source_id`, `destination_id`, `transaction_amount`, `created_by`, `created_at`)
		VALUES (credit_type_id, transaction_source_id, channel_type_id, transaction_sequence_number, source_id, destination_id, transaction_amount, created_by, CURRENT_TIMESTAMP());
    ELSE -- Para yatırma
		IF transaction_source = 'INT' THEN
			UPDATE wallet SET balance += transaction_amount WHERE id = destination_id;
		END IF;
		
		INSERT INTO transaction (`transaction_type_id`, `transaction_source_id`, `channel_type_id`, `transaction_sequence_number`, `source_id`, `destination_id`, `transaction_amount`, `created_by`, `created_at`)
		VALUES (debit_type_id, transaction_source_id, channel_type_id, transaction_sequence_number, source_id, destination_id, transaction_amount, created_by, CURRENT_TIMESTAMP());
	END IF;
    
    SET result_val = 1;
END$$

--
-- İşlevler
--
CREATE DEFINER=`root`@`localhost` FUNCTION `next_value` (`seq_name` VARCHAR(100)) RETURNS BIGINT(20)
BEGIN 
	DECLARE cur_val BIGINT;
 
    SELECT
        cur_value INTO cur_val
    FROM
        sequence
    WHERE
        name = seq_name;
 
    IF cur_val IS NOT NULL THEN
        UPDATE
            sequence
        SET
            cur_value = IF (
                (cur_value + increment) > max_value OR (cur_value + increment) < min_value,
                IF (
                    cycle = TRUE,
                    IF (
                        (cur_value + increment) > max_value,
                        min_value, 
                        max_value 
                    ),
                    NULL
                ),
                cur_value + increment
            )
        WHERE
            name = seq_name;
    END IF; 
    RETURN cur_val;
END$$

DELIMITER ;

COMMIT;
