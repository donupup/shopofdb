/*
MySQL Backup
Database: shop
Backup Time: 2022-03-21 10:41:00
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `bms_good`;
DROP TABLE IF EXISTS `bms_good_in`;
DROP TABLE IF EXISTS `bms_good_out`;
DROP TABLE IF EXISTS `bms_good_sale`;
DROP TABLE IF EXISTS `ums_user`;
DROP TABLE IF EXISTS `bms_provider`;
DROP TABLE IF EXISTS `bms_vip`;
DROP TABLE IF EXISTS `bms_category`;
DROP TABLE IF EXISTS `bms_role`;

CREATE TABLE `bms_category` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) DEFAULT NULL,
  `availableNum` int NULL DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `bms_provider` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(40) DEFAULT NULL,
  `pphone` varchar(20) DEFAULT NULL,
  `paddress` varchar(50) DEFAULT NULL,
  `plinkman` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;

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
  `saleprice` int NOT NULL DEFAULT '0' COMMENT '金额',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `good_sold_time` datetime DEFAULT NULL COMMENT '时间',
  `vip_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vipid',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '售货员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品销售表';

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

CREATE TABLE `bms_vip` (
  `vid` varchar(20) NOT NULL,
  `vname` varchar(20) DEFAULT NULL,
  `vphone` varchar(20) DEFAULT NULL,
  `vbirthday` date DEFAULT NULL,
  `vbalance` float(12,2) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `bms_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(32) CHARACTER SET utf8 NOT NULL,
  `availableNum` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;