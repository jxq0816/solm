/*
Navicat MySQL Data Transfer

Source Server         : solm
Source Server Version : 50552
Source Host           : 120.27.196.222:3306
Source Database       : solm

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-04-01 18:18:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1a9bf0a64a7e4981b95d12f480f53f57', '4');
INSERT INTO `sys_user_role` VALUES ('1d4f3900d29c4a429614376779e51382', '6');
INSERT INTO `sys_user_role` VALUES ('2f52bfcee8d44ac3a9e6fb650512376a', '05aa76f4d83e42819f0b719c6f2df41e');
INSERT INTO `sys_user_role` VALUES ('3558a04cf3474d90894a31887207966a', '05aa76f4d83e42819f0b719c6f2df41e');
INSERT INTO `sys_user_role` VALUES ('3558a04cf3474d90894a31887207966a', '66f2c2710c6e4d4ea1151ee83ebba322');
INSERT INTO `sys_user_role` VALUES ('51e09caebbe6496e938a1c9e85e54d7a', '66f2c2710c6e4d4ea1151ee83ebba322');
INSERT INTO `sys_user_role` VALUES ('65b079f725e14faf9753a3661eb548c9', '645ae7000fe84dd0b54a286ba1b475c6');
INSERT INTO `sys_user_role` VALUES ('e3b4cbbc2aa74d0793b57a4a735d3a64', '6');
