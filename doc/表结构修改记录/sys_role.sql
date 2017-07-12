/*
Navicat MySQL Data Transfer

Source Server         : solm
Source Server Version : 50552
Source Host           : 120.27.196.222:3306
Source Database       : solm

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-04-01 18:13:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `enname` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`enname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '1466444e6129478cbe2cd2434d727388', '西门子中国_测试3_客服', 'test_service', 'user', '8', '1', '1', '1', '2017-03-30 10:34:10', '1', '2017-03-30 10:42:56', '', '0');
INSERT INTO `sys_role` VALUES ('1', '1', '系统管理员', 'admin', 'assignment', '1', '1', '1', '1', '2013-05-27 08:00:00', '1', '2017-01-17 19:04:12', '', '0');
INSERT INTO `sys_role` VALUES ('4', '163af83fb744440a8d070dc480d3ebec', '西门子家电客服主管', 'siemens_service_lead', 'security-role', '4', '1', '1', '1', '2013-05-27 08:00:00', '1', '2017-03-30 10:08:40', '', '0');
INSERT INTO `sys_role` VALUES ('6', '163af83fb744440a8d070dc480d3ebec', '西门子家电客服', 'siemens_service', 'user', '8', '1', '1', '1', '2013-05-27 08:00:00', '1', '2017-03-30 10:01:51', '', '0');
INSERT INTO `sys_role` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', 'a02cf134ec3640969826d0e6daadb4bb', '西门子中国客服主管', 'siemens_china_service_lead', 'assignment', '4', '1', '1', '1', '2017-03-30 10:10:14', '1', '2017-04-01 17:28:26', '', '0');
INSERT INTO `sys_role` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '77875b25612f4e3d8b3c49887ed56a94', '西门子中国客服', 'siemens_china_service', 'user', '8', '1', '1', '1', '2017-01-24 11:41:16', '1', '2017-03-30 10:02:52', '', '0');
