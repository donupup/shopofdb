/*
MySQL Backup
Database: shop2
Backup Time: 2022-04-04 18:09:20
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `shop2`.`bms_category`;
DROP TABLE IF EXISTS `shop2`.`bms_good`;
DROP TABLE IF EXISTS `shop2`.`bms_good_in`;
DROP TABLE IF EXISTS `shop2`.`bms_good_out`;
DROP TABLE IF EXISTS `shop2`.`bms_good_sale`;
DROP TABLE IF EXISTS `shop2`.`bms_provider`;
DROP TABLE IF EXISTS `shop2`.`bms_role`;
DROP TABLE IF EXISTS `shop2`.`bms_vip`;
DROP TABLE IF EXISTS `shop2`.`ums_user`;
CREATE TABLE `bms_category` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
CREATE TABLE `bms_good` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID',
  `goodname` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `category_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品种类id',
  `pricein` int NOT NULL DEFAULT '0' COMMENT '进价',
  `pricesell` int NOT NULL DEFAULT '0' COMMENT '售价',
  `salenum` int NOT NULL DEFAULT '0' COMMENT '售出数',
  `storage` int NOT NULL DEFAULT '0' COMMENT '库存',
  `shelflife` int NOT NULL DEFAULT '0' COMMENT '保质期',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime NOT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `provider_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `good_name` (`goodname`) USING BTREE,
  KEY `good_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品表';
CREATE TABLE `bms_good_in` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `provider_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供货商id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作员id',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `good_in_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品入库表';
CREATE TABLE `bms_good_out` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作员id',
  `good_out_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品出库表';
CREATE TABLE `bms_good_sale` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `good_sold_time` datetime DEFAULT NULL COMMENT '时间',
  `vip_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vipid',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '售货员id',
  `bio` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品销售表';
CREATE TABLE `bms_provider` (
  `pid` varchar(50) NOT NULL,
  `pname` varchar(40) DEFAULT NULL,
  `pphone` varchar(20) DEFAULT NULL,
  `paddress` varchar(50) DEFAULT NULL,
  `plinkman` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `bms_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rolename` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
CREATE TABLE `bms_vip` (
  `vsex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `vid` varchar(20) NOT NULL,
  `vname` varchar(20) DEFAULT NULL,
  `vphone` varchar(20) DEFAULT NULL,
  `vaddress` varchar(20) DEFAULT NULL,
  `vbalance` float(12,2) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `ums_user` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户昵称（姓名）',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '密码',
  `avatar` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'token',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人简介',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否激活，1：是，0：否',
  `status` bit(1) DEFAULT b'1' COMMENT '状态，1：使用，0：停用',
  `role_id` varchar(4) DEFAULT NULL COMMENT '用户角色id',
  `create_time` datetime NOT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `stu_no` varchar(255) NOT NULL COMMENT '学号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`username`) USING BTREE,
  KEY `user_email` (`email`) USING BTREE,
  KEY `user_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';
BEGIN;
LOCK TABLES `shop2`.`bms_category` WRITE;
DELETE FROM `shop2`.`bms_category`;
INSERT INTO `shop2`.`bms_category` (`cid`,`cname`) VALUES (1, '食品'),(2, '生活用品'),(3, '数码'),(4, '奢侈品');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good` WRITE;
DELETE FROM `shop2`.`bms_good`;
INSERT INTO `shop2`.`bms_good` (`id`,`goodname`,`category_id`,`pricein`,`pricesell`,`salenum`,`storage`,`shelflife`,`bio`,`create_time`,`modify_time`,`provider_id`) VALUES ('1', '大骨面', '1', 2, 3, 0, 20, 0, '大骨面', '2022-04-02 14:16:57', '2022-04-02 14:17:00', '2'),('1510250437951893506', '哇哈哈', '1', 2, 5, 0, 9, 0, '', '2022-04-02 21:38:58', '2022-04-02 22:14:05', '1'),('1510774916071419905', 'iphone13', '3', 2000, 3000, 0, 1, 1000, '手机', '2022-04-04 08:23:03', '2022-04-04 08:23:03', '1510495391009845249');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good_in` WRITE;
DELETE FROM `shop2`.`bms_good_in`;
INSERT INTO `shop2`.`bms_good_in` (`id`,`num`,`good_id`,`provider_id`,`user_id`,`bio`,`good_in_time`) VALUES ('1', 3, '1', '2', '1', '售货', '2022-04-03 14:09:43'),('1510543742988300289', 2, '1510250437951893506', '1', '1', '123', '2022-04-03 17:04:28'),('1510544652904726530', 1, '1510250437951893506', '1', '1', '加1', '2022-04-03 17:08:04');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good_out` WRITE;
DELETE FROM `shop2`.`bms_good_out`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good_sale` WRITE;
DELETE FROM `shop2`.`bms_good_sale`;
INSERT INTO `shop2`.`bms_good_sale` (`id`,`num`,`good_id`,`good_sold_time`,`vip_id`,`user_id`,`bio`) VALUES ('1', 2, '1', '2022-04-03 19:14:35', '1', '1', '售货'),('1510639976885288961', 1, '1', '2022-04-03 21:26:51', '1', '1', '1'),('1510640237544521729', 1, '1', '2022-04-03 12:27:54', '1510217057403187202', '1', '1'),('1510643115642363906', 1, '1510250437951893506', '2022-04-03 12:39:20', '1510218882743336961', '1', '1'),('1510645472937680898', 2, '1510250437951893506', '2022-04-03 12:48:42', '1', '1', 'id'),('1510775038738034690', 1, '1510774916071419905', '2022-04-04 08:23:33', '1510217057403187202', '1', '买手机');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_provider` WRITE;
DELETE FROM `shop2`.`bms_provider`;
INSERT INTO `shop2`.`bms_provider` (`pid`,`pname`,`pphone`,`paddress`,`plinkman`) VALUES ('1', '哇哈哈', '1354658456', '北京', '小王'),('1510195385518358530', '统一', '12876549835', '武汉', '小卢'),('1510495391009845249', '苹果', '123456', '美国', 'jobs'),('2', '白象', '1235489', '武汉', '小刘');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_role` WRITE;
DELETE FROM `shop2`.`bms_role`;
INSERT INTO `shop2`.`bms_role` (`id`,`rolename`) VALUES (1, '管理员'),(2, '销售'),(3, '进货员');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_vip` WRITE;
DELETE FROM `shop2`.`bms_vip`;
INSERT INTO `shop2`.`bms_vip` (`vsex`,`vid`,`vname`,`vphone`,`vaddress`,`vbalance`) VALUES ('男', '1', '王五', '21354984', '开福区', 500.00),('女', '1510217057403187202', '张三', '178562157', '上海', 1.00),('男', '1510218882743336961', '刘六', '2751648', '天津', 33.00),('男', '1510494542086909954', '何何', '12324', '地方', 23.00);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`ums_user` WRITE;
DELETE FROM `shop2`.`ums_user`;
INSERT INTO `shop2`.`ums_user` (`id`,`username`,`alias`,`password`,`avatar`,`email`,`mobile`,`token`,`bio`,`active`,`status`,`role_id`,`create_time`,`modify_time`,`stu_no`) VALUES ('1', 'admin', '管理', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', '2998128602@qq.com', '157486445', '', '自由职业者', b'1', b'1', '1', '2022-04-02 20:15:30', NULL, '201902001032'),('1510499195180658689', 'buyer', 'buyer', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', NULL, NULL, '', '自由职业者', b'1', b'1', '3', '2022-04-03 14:07:27', NULL, '201902000000');
UNLOCK TABLES;
COMMIT;
