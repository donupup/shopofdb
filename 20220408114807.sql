/*
MySQL Backup
Database: shop2
Backup Time: 2022-04-08 11:48:07
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `shop2`.`bms_cart`;
DROP TABLE IF EXISTS `shop2`.`bms_category`;
DROP TABLE IF EXISTS `shop2`.`bms_good`;
DROP TABLE IF EXISTS `shop2`.`bms_good_in`;
DROP TABLE IF EXISTS `shop2`.`bms_good_sale`;
DROP TABLE IF EXISTS `shop2`.`bms_provider`;
DROP TABLE IF EXISTS `shop2`.`bms_role`;
DROP TABLE IF EXISTS `shop2`.`bms_vip`;
DROP TABLE IF EXISTS `shop2`.`ums_user`;
CREATE TABLE `bms_cart` (
  `id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `good_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `num` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `good_id` (`good_id`),
  KEY `vip_id` (`vip_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `bms_cart_ibfk_1` FOREIGN KEY (`good_id`) REFERENCES `bms_good` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bms_cart_ibfk_2` FOREIGN KEY (`vip_id`) REFERENCES `bms_vip` (`vid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bms_cart_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `bms_category` (
  `cid` varchar(30) NOT NULL,
  `cname` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `bms_good` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID',
  `goodname` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `category_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品种类id',
  `pricein` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '进价',
  `pricesell` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `salenum` int NOT NULL DEFAULT '0' COMMENT '售出数',
  `storage` int NOT NULL DEFAULT '0' COMMENT '库存',
  `shelflife` int DEFAULT '0' COMMENT '保质期',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime DEFAULT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `provider_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `specification` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `good_name` (`goodname`) USING BTREE,
  KEY `good_create_time` (`create_time`) USING BTREE,
  KEY `goodcategory` (`category_id`),
  CONSTRAINT `goodcategory` FOREIGN KEY (`category_id`) REFERENCES `bms_category` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品表';
CREATE TABLE `bms_good_in` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `provider_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '供货商id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '操作员id',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `good_in_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ingood_id` (`good_id`),
  KEY `inprovider_id` (`provider_id`),
  KEY `user_id_goodin` (`user_id`),
  CONSTRAINT `ingood_id` FOREIGN KEY (`good_id`) REFERENCES `bms_good` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `inprovider_id` FOREIGN KEY (`provider_id`) REFERENCES `bms_provider` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id_goodin` FOREIGN KEY (`user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品入库表';
CREATE TABLE `bms_good_sale` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `good_sold_time` datetime DEFAULT NULL COMMENT '时间',
  `vip_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vipid',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '售货员id',
  `bio` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `goodsale_id` (`good_id`),
  KEY `salevip_id` (`vip_id`),
  KEY `saleuser_id` (`user_id`),
  CONSTRAINT `goodsale_id` FOREIGN KEY (`good_id`) REFERENCES `bms_good` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `saleuser_id` FOREIGN KEY (`user_id`) REFERENCES `ums_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `salevip_id` FOREIGN KEY (`vip_id`) REFERENCES `bms_vip` (`vid`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `id` varchar(30) NOT NULL,
  `rolename` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `bms_vip` (
  `vsex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `vid` varchar(20) NOT NULL,
  `vname` varchar(20) DEFAULT NULL,
  `vphone` varchar(20) DEFAULT NULL,
  `vaddress` varchar(20) DEFAULT NULL,
  `vbalance` float(12,2) DEFAULT NULL,
  `vage` int DEFAULT NULL,
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
  KEY `user_create_time` (`create_time`) USING BTREE,
  KEY `role` (`role_id`),
  CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `bms_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';
BEGIN;
LOCK TABLES `shop2`.`bms_cart` WRITE;
DELETE FROM `shop2`.`bms_cart`;
INSERT INTO `shop2`.`bms_cart` (`id`,`vip_id`,`good_id`,`user_id`,`num`,`price`,`add_time`) VALUES ('1511601474013097985', '1510217057403187202', '1512080403370926081', '1', 2, 6.00, '2022-04-06 15:07:30'),('1512259169774731266', '1', '1512259022869233666', '1', 1, 3.00, '2022-04-08 10:40:57');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_category` WRITE;
DELETE FROM `shop2`.`bms_category`;
INSERT INTO `shop2`.`bms_category` (`cid`,`cname`) VALUES ('1', '休闲零食'),('10', '新鲜蔬菜'),('2', '酒水饮料'),('3', '电子数码'),('4', '速冻速食'),('5', '时令水果'),('6', '美护百货'),('7', '粮油调味'),('8', '熟食面点'),('9', '肉蛋水产');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good` WRITE;
DELETE FROM `shop2`.`bms_good`;
INSERT INTO `shop2`.`bms_good` (`id`,`goodname`,`category_id`,`pricein`,`pricesell`,`salenum`,`storage`,`shelflife`,`bio`,`create_time`,`modify_time`,`provider_id`,`specification`) VALUES ('1', '大骨面', '1', 2.00, 3.00, 9, 26, 180, '', '2022-04-02 14:16:57', '2022-04-08 11:44:19', '2', '100g/袋'),('1510250437951893506', '哇哈哈', '1', 2.00, 5.00, 13, 11, 180, '', '2022-04-02 21:38:58', '2022-04-02 22:14:05', '1', '500ml/瓶'),('1512054474221400066', '蔬菜套装', '10', 3.00, 6.00, 2, 20, 10, '蔬菜', '2022-04-07 21:07:34', '2022-04-07 21:07:34', '1512052421268869122', '500g/包'),('1512080403370926081', '冰红茶', '2', 2.00, 3.00, 0, 6, 180, '冰红茶', '2022-04-07 22:50:36', '2022-04-07 22:50:36', '1510195385518358530', '500ml/瓶'),('1512255083218206722', '鸡蛋套餐', '9', 3.00, 7.00, 2, 18, 100, '鸡蛋', '2022-04-08 10:24:43', '2022-04-08 10:24:43', '1512052421268869122', '100g/篮'),('1512257395546390529', '洗面奶', '6', 20.00, 30.00, 1, 20, 180, '洗面奶', '2022-04-08 10:33:54', '2022-04-08 10:33:54', '1511609497729163266', '150g/支'),('1512257831531708417', '花生油', '7', 30.00, 36.00, 1, 19, 180, '油', '2022-04-08 10:35:38', '2022-04-08 10:35:38', '1512257626430242817', '20'),('1512259022869233666', '面包', '8', 1.00, 3.00, 1, 20, 180, '面包', '2022-04-08 10:40:22', '2022-04-08 10:40:22', '1510195385518358530', '200g/袋');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good_in` WRITE;
DELETE FROM `shop2`.`bms_good_in`;
INSERT INTO `shop2`.`bms_good_in` (`id`,`num`,`good_id`,`provider_id`,`user_id`,`bio`,`good_in_time`) VALUES ('1', 3, '1512080403370926081', '2', '1', '6', '2022-04-03 14:09:43'),('1510543742988300289', 2, '1510250437951893506', '1', '1', '123', '2022-04-03 17:04:28'),('1510544652904726530', 1, '1510250437951893506', '1', '1', '加1', '2022-04-03 17:08:04'),('1510964800396500993', 1, '1512080403370926081', '2', '1', '+1', '2022-04-04 20:57:35'),('1511344582250496002', 5, '1512080403370926081', '2', '1', 'excel', '2022-04-05 06:09:00'),('1511662454923374594', 5, '1512080403370926081', '2', '1', 'excel', '2022-04-05 06:09:00'),('1511664328665853954', 5, '1512080403370926081', '2', '1', 'excel', '2022-04-02 14:09:00'),('1511671605799944193', 12, '1510250437951893506', '1', '1', '进货', '2022-04-06 19:46:11'),('1512065263950929921', 12, '1512054474221400066', '1512052421268869122', '1', '采购蔬菜', '2022-04-07 21:50:26'),('1512259444052852737', 1, '1512259022869233666', '1510195385518358530', '1', '+1', '2022-04-08 10:42:03'),('1512259528010235905', 1, '1512257395546390529', '1511609497729163266', '1', '+1', '2022-04-08 10:42:23');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_good_sale` WRITE;
DELETE FROM `shop2`.`bms_good_sale`;
INSERT INTO `shop2`.`bms_good_sale` (`id`,`num`,`good_id`,`good_sold_time`,`vip_id`,`user_id`,`bio`) VALUES ('1', 2, '1512080403370926081', '2022-04-03 19:14:35', '1', '1', '售货'),('1510639976885288961', 1, '1512080403370926081', '2022-04-03 21:26:51', '1', '1', '12'),('1510640237544521729', 1, '1512080403370926081', '2022-04-03 12:27:54', '1510217057403187202', '1', '1'),('1510643115642363906', 1, '1510250437951893506', '2022-04-03 12:39:20', '1510218882743336961', '1', '1'),('1510645472937680898', 2, '1510250437951893506', '2022-04-03 12:48:42', '1', '1', 'id'),('1510964101889695745', 5, '1510250437951893506', '2022-04-04 20:54:49', '1510217057403187202', '1', '哇哈哈'),('1511605638814343170', 2, '1512080403370926081', '2022-04-06 15:24:03', '1', '1', '王五购物车结算'),('1511605900354314241', 2, '1512080403370926081', '2022-04-06 15:25:06', '1', '1', '王五购物车结算1'),('1511614493866639361', 2, '1510250437951893506', '2022-04-06 15:59:14', '1', '1', '王五购物车结算'),('1511623801362759681', 1, '1512080403370926081', '2022-04-06 16:36:14', '1', '1', '王五购物车结算'),('1511625741236748290', 1, '1510250437951893506', '2022-04-06 16:43:56', '1', '1', '王五购物车结算'),('1511647887350689794', 1, '1512080403370926081', '2022-04-06 18:11:56', '1', '1', '王五购物车结算'),('1511660961436577793', 1, '1512080403370926081', '2022-04-06 19:03:53', '1', '1', '王五购物车结算'),('1511668700732719106', 1, '1510250437951893506', '2022-04-06 19:34:38', '1510218882743336961', '1', '刘六购物车结算'),('1511911983555842049', 1, '1510250437951893506', '2022-04-07 11:41:22', '1', '1511910797322784770', '王五购物车结算'),('1512065340656361473', 2, '1512054474221400066', '2022-04-07 21:50:45', '1', '1', '王五购物车结算'),('1512088809674670081', 5, '1', '2022-04-07 23:24:00', '1510218882743336961', '1', '刘六购物车结算'),('1512258195224002561', 1, '1512257395546390529', '2022-04-08 10:37:05', '1', '1', '王五购物车结算'),('1512258195307888642', 1, '1512255083218206722', '2022-04-08 10:37:05', '1', '1', '王五购物车结算'),('1512258195362414593', 1, '1512257831531708417', '2022-04-08 10:37:05', '1', '1', '王五购物车结算'),('1512259100631629825', 1, '1', '2022-04-08 10:40:41', '1', '1', '王五购物车结算'),('1512259141312184322', 1, '1512259022869233666', '2022-04-08 10:40:50', '1', '1', '王五购物车结算'),('1512267833013256194', 1, '1512255083218206722', '2022-04-08 11:15:23', '1510217057403187202', '1', '张三购物车结算');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_provider` WRITE;
DELETE FROM `shop2`.`bms_provider`;
INSERT INTO `shop2`.`bms_provider` (`pid`,`pname`,`pphone`,`paddress`,`plinkman`) VALUES ('1', '哇哈哈', '1354658456789', '北京', '小王'),('1510195385518358530', '统一', '12876549835', '武汉', '小卢'),('1510495391009845249', '苹果', '123456', '美国', 'jobs'),('1511609497729163266', '欧莱雅', '12324', '上海', '小张'),('1512052421268869122', '河马生鲜', '15497856324', '长沙', 'allen'),('1512257626430242817', '金龙鱼', '16485236945', '济南', '小林'),('2', '白象', '1235489', '武汉', '小刘');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_role` WRITE;
DELETE FROM `shop2`.`bms_role`;
INSERT INTO `shop2`.`bms_role` (`id`,`rolename`) VALUES ('1', '管理员'),('2', '销售'),('3', '进货员'),('4', '数据分析员');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`bms_vip` WRITE;
DELETE FROM `shop2`.`bms_vip`;
INSERT INTO `shop2`.`bms_vip` (`vsex`,`vid`,`vname`,`vphone`,`vaddress`,`vbalance`,`vage`) VALUES ('男', '1', '王五', '213549845', '开福区', 404.00, 21),('女', '1510217057403187202', '张三', '17856215789', '上海', 68.00, 30),('男', '1510218882743336961', '刘六', '27516489', '天津', 139.00, 40),('男', '1510494542086909954', '何何', '12324', '地方', 233.00, 16),('女', '1512261610750607361', '李四', '15645236548', '市区', 300.00, 19);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `shop2`.`ums_user` WRITE;
DELETE FROM `shop2`.`ums_user`;
INSERT INTO `shop2`.`ums_user` (`id`,`username`,`alias`,`password`,`avatar`,`email`,`mobile`,`token`,`bio`,`active`,`status`,`role_id`,`create_time`,`modify_time`,`stu_no`) VALUES ('1', 'admin', '管理', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', '2998128602@qq.com', '17553858297', '', '3', b'1', b'1', '1', '2022-04-02 20:15:30', NULL, '201902001032'),('1510499195180658689', 'buyer', 'buyer', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', NULL, '19848021365', '', '自由职业者', b'1', b'1', '3', '2022-04-03 14:07:27', NULL, '201902000000123'),('1511910797322784770', 'saler', 'saler', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', NULL, '19817969545', '', '自由职业者', b'1', b'1', '2', '2022-04-07 11:36:39', NULL, '201900003'),('1512262291356123137', 'analyzer', 'analyzer', '4297f44b13955235245b2497399d7a93', 'https://s3.ax1x.com/2020/12/01/DfHNo4.jpg', NULL, '19817969545', '', '自由职业者', b'1', b'1', '4', '2022-04-08 10:53:21', NULL, '201902001001');
UNLOCK TABLES;
COMMIT;
