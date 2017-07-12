/*
Navicat MySQL Data Transfer

Source Server         : solm
Source Server Version : 50552
Source Host           : 120.27.196.222:3306
Source Database       : solm

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-04-01 18:08:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `user_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  `weibo_screen_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `weibo_user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', 'a02cf134ec3640969826d0e6daadb4bb', 'admin', '02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032', '0001', '系统管理员', 'thinkgem@163.com', '8675', '8675', '', '', '115.171.217.253', '2017-04-01 17:45:46', '1', '1', '2013-05-27 08:00:00', '1', '2017-03-24 12:17:09', '最高管理员', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('1a9bf0a64a7e4981b95d12f480f53f57', '2', '163af83fb744440a8d070dc480d3ebec', 'manager', '10653cf6c2ca07aade06c64fd4467a6f93e5005828fe5ff9c219020f', '001', '吴轶伦', '', '', '', '2', '', '122.200.72.67', '2017-04-01 13:48:19', '1', '1', '2017-01-22 18:12:50', '1', '2017-03-30 12:20:51', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('1d4f3900d29c4a429614376779e51382', '2', '163af83fb744440a8d070dc480d3ebec', 'lisi', 'e46fedd6bba8f7728e4fd4681e4933950ae94a5b9e823780ba20d60a', '002', '李四', '', '', '', '3', '', '211.157.175.238', '2017-04-01 17:00:12', '1', '1', '2017-01-20 17:25:18', '1', '2017-03-30 10:44:36', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('2f52bfcee8d44ac3a9e6fb650512376a', 'dd76d3f892284320aebbec96b90cf9be', '1466444e6129478cbe2cd2434d727388', 'jiangxingqi', 'd5eb48eb32b9d32a9a478e19ec977e0bbf6a7bd2ef75e4b59d58b419', '007', '姜兴琪', '', '', '', '3', '', '14.131.1.171', '2017-03-30 11:21:46', '1', '1', '2017-03-30 10:59:08', '1', '2017-03-30 11:21:32', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('3558a04cf3474d90894a31887207966a', 'dd76d3f892284320aebbec96b90cf9be', '1466444e6129478cbe2cd2434d727388', 'hongqi', '3560bd0e47d4aba8c070b92a50676ce986757420fed3f4fdf00a292c', '001', '测试3_洪七', '', '', '', '3', '', null, null, '1', '1', '2017-03-30 10:21:26', '1', '2017-03-30 10:45:45', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('51e09caebbe6496e938a1c9e85e54d7a', 'dd76d3f892284320aebbec96b90cf9be', '77875b25612f4e3d8b3c49887ed56a94', 'maliu', 'e6c66eba7c96c08a56ac0712501e0581dd327151d0209e2948f9c530', '101', '马六', '', '', '', '3', '', '211.157.175.238', '2017-03-30 11:51:38', '1', '1', '2017-01-24 11:40:05', '1', '2017-03-30 10:16:04', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('65b079f725e14faf9753a3661eb548c9', 'dd76d3f892284320aebbec96b90cf9be', '77875b25612f4e3d8b3c49887ed56a94', 'shiqi', 'd09644f7f66e9d75d11b32f541155f2c3645ad009a68f3e14aaba210', '002', '十七', '', '', '', '3', '', '115.171.217.253', '2017-04-01 17:45:58', '1', '1', '2017-03-30 10:53:35', '1', '2017-03-30 10:53:35', '', '0', '张宇Socialmedia', '1717624644');
INSERT INTO `sys_user` VALUES ('e3b4cbbc2aa74d0793b57a4a735d3a64', '2', '163af83fb744440a8d070dc480d3ebec', 'zhangsan', 'b4e29c14c76245a58f19723612adcd58c7794150ab40576a913a5b89', '001', '张三', '', '', '', '3', '', '14.131.1.171', '2017-03-30 15:26:56', '1', '1', '2017-01-20 17:22:03', '1', '2017-03-30 10:17:33', '', '0', '张宇Socialmedia', '1717624644');
