/*
Navicat MySQL Data Transfer

Source Server         : solm
Source Server Version : 50552
Source Host           : 120.27.196.222:3306
Source Database       : solm

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-04-01 18:15:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `area_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '归属区域',
  `code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '区域编码',
  `type` char(1) COLLATE utf8_bin NOT NULL COMMENT '机构类型',
  `grade` char(1) COLLATE utf8_bin NOT NULL COMMENT '机构等级',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '副负责人',
  `access_token` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`),
  KEY `sys_office_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '博晓通', '30', '2', '001', '1', '1', '', '', '', '', '', '', '1', '', '', null, '1', '2017-01-20 17:13:25', '1', '2017-01-20 17:13:25', '', '0');
INSERT INTO `sys_office` VALUES ('1466444e6129478cbe2cd2434d727388', 'dd76d3f892284320aebbec96b90cf9be', '0,dd76d3f892284320aebbec96b90cf9be,', '测试3', '30', '2', '003002', '2', '1', '', '', '', '', '', '', '1', '', '', '', '1', '2017-03-30 09:41:35', '1', '2017-03-30 09:41:35', '', '0');
INSERT INTO `sys_office` VALUES ('163af83fb744440a8d070dc480d3ebec', '2', '0,2,', '西门子家电', '30', '2', '002001', '2', '2', '', '', '', '', '', '', '1', '', '', '', '1', '2017-01-20 17:15:25', '1', '2017-04-01 11:06:11', '', '0');
INSERT INTO `sys_office` VALUES ('2', '0', '0,', '西门子家电有限公司', '30', '2', '002', '1', '1', '', '', '', '', '', '', '1', '', '', '', '1', '2017-01-20 17:13:59', '1', '2017-03-30 10:23:21', '', '0');
INSERT INTO `sys_office` VALUES ('77875b25612f4e3d8b3c49887ed56a94', 'dd76d3f892284320aebbec96b90cf9be', '0,dd76d3f892284320aebbec96b90cf9be,', '西门子中国', '30', '2', '003001', '2', '2', '', '', '', '', '', '', '1', '', '', '', '1', '2017-01-23 19:00:56', '1', '2017-03-30 09:40:31', '', '0');
INSERT INTO `sys_office` VALUES ('a02cf134ec3640969826d0e6daadb4bb', '1', '0,1,', '开发部', '30', '2', '001001', '2', '2', '', '', '', '', '', '', '1', '', '', null, '1', '2017-01-20 17:23:32', '1', '2017-01-20 17:23:32', '', '0');
INSERT INTO `sys_office` VALUES ('dd76d3f892284320aebbec96b90cf9be', '0', '0,', '西门子中国有限公司', '30', '2', '003', '1', '1', '', '', '', '', '', '', '1', '', '', '', '1', '2017-01-23 19:00:34', '1', '2017-03-30 10:23:42', '', '0');
