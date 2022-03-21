/*
MySQL Backup
Database: shop
Backup Time: 2022-03-21 10:41:00
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `shop`.`bms_good`;
DROP TABLE IF EXISTS `shop`.`bms_good_in`;
DROP TABLE IF EXISTS `shop`.`bms_good_out`;
DROP TABLE IF EXISTS `shop`.`bms_good_sale`;
DROP TABLE IF EXISTS `shop`.`bms_tip`;
DROP TABLE IF EXISTS `shop`.`ums_user`;
CREATE TABLE `bms_good` (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID',
  `goodname` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `pricein` int NOT NULL DEFAULT '0' COMMENT '进价',
  `pricesell` int NOT NULL DEFAULT '0' COMMENT '售价',
  `salenum` int NOT NULL DEFAULT '0' COMMENT '售出数',
  `storage` int NOT NULL DEFAULT '0' COMMENT '库存',
  `shelflife` int NOT NULL DEFAULT '0' COMMENT '保质期',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品描述',
  `outofdate` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否过期，1：是，0：否',
  `create_time` datetime NOT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `good_name` (`goodname`) USING BTREE,
  KEY `good_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品表';
CREATE TABLE `bms_good_in` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `goodname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `good_in_time` datetime DEFAULT NULL COMMENT '时间',
  `inPrice` int NOT NULL COMMENT '进价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品入库表';
CREATE TABLE `bms_good_out` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `goodname` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `good_out_time` datetime DEFAULT NULL COMMENT '时间',
  `good_deleted` bit(1) DEFAULT b'0' COMMENT '状态，1：已彻底删除，0：未删除，仅出货',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品出库表';
CREATE TABLE `bms_good_sale` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `goodname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `num` int NOT NULL DEFAULT '0' COMMENT '数量',
  `good_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品id',
  `good_sold_time` datetime DEFAULT NULL COMMENT '时间',
  `priceinNow` int NOT NULL DEFAULT '0' COMMENT '当前进价',
  `pricesellNow` int NOT NULL DEFAULT '0' COMMENT '当前售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='商品销售表';
CREATE TABLE `bms_tip` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '作者',
  `type` tinyint NOT NULL COMMENT '1：使用，0：过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24864 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='每日赠言';
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
  `role_id` varchar(4) DEFAULT NULL COMMENT '用户角色',
  `create_time` datetime NOT NULL COMMENT '加入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `stu_no` varchar(255) NOT NULL COMMENT '学号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`username`) USING BTREE,
  KEY `user_email` (`email`) USING BTREE,
  KEY `user_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';