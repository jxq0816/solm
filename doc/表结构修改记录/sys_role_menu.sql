/*
Navicat MySQL Data Transfer

Source Server         : solm
Source Server Version : 50552
Source Host           : 120.27.196.222:3306
Source Database       : solm

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-04-01 18:12:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '0f105f23f62549a2b22f7f573944c163');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '1');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '27');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '28');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '29');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '2aa2007bdea8486692a3e5fb181c0a16');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '30');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '6e6e00b85f6a4108978932436314f002');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '745ba61f7a2e442a84690fbbe1e0954e');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '7e65911542ad4386afddcec5956a1b85');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '88a2757fa8f843b29ea0a52bcfaf2484');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '89');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', '9eb90fe6b52b45089f52b7cab9ee720d');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', 'c977c4ca6c6041659afa9b77444a9f8d');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', 'd5fab999639e46f6a3d406a126c29b39');
INSERT INTO `sys_role_menu` VALUES ('05aa76f4d83e42819f0b719c6f2df41e', 'f52880e90fe2447db851e8ba78fcb773');
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '21');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '23');
INSERT INTO `sys_role_menu` VALUES ('1', '24');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('1', '32');
INSERT INTO `sys_role_menu` VALUES ('1', '33');
INSERT INTO `sys_role_menu` VALUES ('1', '34');
INSERT INTO `sys_role_menu` VALUES ('1', '35');
INSERT INTO `sys_role_menu` VALUES ('1', '36');
INSERT INTO `sys_role_menu` VALUES ('1', '37');
INSERT INTO `sys_role_menu` VALUES ('1', '38');
INSERT INTO `sys_role_menu` VALUES ('1', '39');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '40');
INSERT INTO `sys_role_menu` VALUES ('1', '41');
INSERT INTO `sys_role_menu` VALUES ('1', '42');
INSERT INTO `sys_role_menu` VALUES ('1', '43');
INSERT INTO `sys_role_menu` VALUES ('1', '44');
INSERT INTO `sys_role_menu` VALUES ('1', '45');
INSERT INTO `sys_role_menu` VALUES ('1', '46');
INSERT INTO `sys_role_menu` VALUES ('1', '47');
INSERT INTO `sys_role_menu` VALUES ('1', '48');
INSERT INTO `sys_role_menu` VALUES ('1', '49');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '50');
INSERT INTO `sys_role_menu` VALUES ('1', '51');
INSERT INTO `sys_role_menu` VALUES ('1', '52');
INSERT INTO `sys_role_menu` VALUES ('1', '53');
INSERT INTO `sys_role_menu` VALUES ('1', '54');
INSERT INTO `sys_role_menu` VALUES ('1', '55');
INSERT INTO `sys_role_menu` VALUES ('1', '56');
INSERT INTO `sys_role_menu` VALUES ('1', '57');
INSERT INTO `sys_role_menu` VALUES ('1', '58');
INSERT INTO `sys_role_menu` VALUES ('1', '59');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '60');
INSERT INTO `sys_role_menu` VALUES ('1', '61');
INSERT INTO `sys_role_menu` VALUES ('1', '62');
INSERT INTO `sys_role_menu` VALUES ('1', '63');
INSERT INTO `sys_role_menu` VALUES ('1', '64');
INSERT INTO `sys_role_menu` VALUES ('1', '65');
INSERT INTO `sys_role_menu` VALUES ('1', '66');
INSERT INTO `sys_role_menu` VALUES ('1', '67');
INSERT INTO `sys_role_menu` VALUES ('1', '68');
INSERT INTO `sys_role_menu` VALUES ('1', '69');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '70');
INSERT INTO `sys_role_menu` VALUES ('1', '71');
INSERT INTO `sys_role_menu` VALUES ('1', '72');
INSERT INTO `sys_role_menu` VALUES ('1', '73');
INSERT INTO `sys_role_menu` VALUES ('1', '74');
INSERT INTO `sys_role_menu` VALUES ('1', '79');
INSERT INTO `sys_role_menu` VALUES ('1', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '80');
INSERT INTO `sys_role_menu` VALUES ('1', '81');
INSERT INTO `sys_role_menu` VALUES ('1', '82');
INSERT INTO `sys_role_menu` VALUES ('1', '84');
INSERT INTO `sys_role_menu` VALUES ('1', '85');
INSERT INTO `sys_role_menu` VALUES ('1', '88');
INSERT INTO `sys_role_menu` VALUES ('1', '89');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '90');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '13');
INSERT INTO `sys_role_menu` VALUES ('2', '14');
INSERT INTO `sys_role_menu` VALUES ('2', '15');
INSERT INTO `sys_role_menu` VALUES ('2', '16');
INSERT INTO `sys_role_menu` VALUES ('2', '17');
INSERT INTO `sys_role_menu` VALUES ('2', '18');
INSERT INTO `sys_role_menu` VALUES ('2', '19');
INSERT INTO `sys_role_menu` VALUES ('2', '2');
INSERT INTO `sys_role_menu` VALUES ('2', '20');
INSERT INTO `sys_role_menu` VALUES ('2', '21');
INSERT INTO `sys_role_menu` VALUES ('2', '22');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '28');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '30');
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '13');
INSERT INTO `sys_role_menu` VALUES ('3', '14');
INSERT INTO `sys_role_menu` VALUES ('3', '15');
INSERT INTO `sys_role_menu` VALUES ('3', '16');
INSERT INTO `sys_role_menu` VALUES ('3', '17');
INSERT INTO `sys_role_menu` VALUES ('3', '18');
INSERT INTO `sys_role_menu` VALUES ('3', '19');
INSERT INTO `sys_role_menu` VALUES ('3', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '20');
INSERT INTO `sys_role_menu` VALUES ('3', '21');
INSERT INTO `sys_role_menu` VALUES ('3', '22');
INSERT INTO `sys_role_menu` VALUES ('3', '27');
INSERT INTO `sys_role_menu` VALUES ('3', '28');
INSERT INTO `sys_role_menu` VALUES ('3', '29');
INSERT INTO `sys_role_menu` VALUES ('3', '30');
INSERT INTO `sys_role_menu` VALUES ('4', '0f105f23f62549a2b22f7f573944c163');
INSERT INTO `sys_role_menu` VALUES ('4', '1');
INSERT INTO `sys_role_menu` VALUES ('4', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('4', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('4', '27');
INSERT INTO `sys_role_menu` VALUES ('4', '28');
INSERT INTO `sys_role_menu` VALUES ('4', '29');
INSERT INTO `sys_role_menu` VALUES ('4', '2aa2007bdea8486692a3e5fb181c0a16');
INSERT INTO `sys_role_menu` VALUES ('4', '30');
INSERT INTO `sys_role_menu` VALUES ('4', '6e6e00b85f6a4108978932436314f002');
INSERT INTO `sys_role_menu` VALUES ('4', '745ba61f7a2e442a84690fbbe1e0954e');
INSERT INTO `sys_role_menu` VALUES ('4', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('4', '7e65911542ad4386afddcec5956a1b85');
INSERT INTO `sys_role_menu` VALUES ('4', '88a2757fa8f843b29ea0a52bcfaf2484');
INSERT INTO `sys_role_menu` VALUES ('4', '89');
INSERT INTO `sys_role_menu` VALUES ('4', '9eb90fe6b52b45089f52b7cab9ee720d');
INSERT INTO `sys_role_menu` VALUES ('4', 'c977c4ca6c6041659afa9b77444a9f8d');
INSERT INTO `sys_role_menu` VALUES ('4', 'd5fab999639e46f6a3d406a126c29b39');
INSERT INTO `sys_role_menu` VALUES ('4', 'f52880e90fe2447db851e8ba78fcb773');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('5', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('5', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('6', '0f105f23f62549a2b22f7f573944c163');
INSERT INTO `sys_role_menu` VALUES ('6', '1');
INSERT INTO `sys_role_menu` VALUES ('6', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('6', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('6', '2aa2007bdea8486692a3e5fb181c0a16');
INSERT INTO `sys_role_menu` VALUES ('6', '6e6e00b85f6a4108978932436314f002');
INSERT INTO `sys_role_menu` VALUES ('6', '745ba61f7a2e442a84690fbbe1e0954e');
INSERT INTO `sys_role_menu` VALUES ('6', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('6', '7e65911542ad4386afddcec5956a1b85');
INSERT INTO `sys_role_menu` VALUES ('6', '88a2757fa8f843b29ea0a52bcfaf2484');
INSERT INTO `sys_role_menu` VALUES ('6', '89');
INSERT INTO `sys_role_menu` VALUES ('6', '9eb90fe6b52b45089f52b7cab9ee720d');
INSERT INTO `sys_role_menu` VALUES ('6', 'c977c4ca6c6041659afa9b77444a9f8d');
INSERT INTO `sys_role_menu` VALUES ('6', 'd5fab999639e46f6a3d406a126c29b39');
INSERT INTO `sys_role_menu` VALUES ('6', 'f52880e90fe2447db851e8ba78fcb773');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '0f105f23f62549a2b22f7f573944c163');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '1');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '27');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '28');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '29');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '2aa2007bdea8486692a3e5fb181c0a16');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '30');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '6e6e00b85f6a4108978932436314f002');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '745ba61f7a2e442a84690fbbe1e0954e');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '7e65911542ad4386afddcec5956a1b85');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '88a2757fa8f843b29ea0a52bcfaf2484');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '89');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', '9eb90fe6b52b45089f52b7cab9ee720d');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', 'c977c4ca6c6041659afa9b77444a9f8d');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', 'd5fab999639e46f6a3d406a126c29b39');
INSERT INTO `sys_role_menu` VALUES ('645ae7000fe84dd0b54a286ba1b475c6', 'f52880e90fe2447db851e8ba78fcb773');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '0f105f23f62549a2b22f7f573944c163');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '1');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '1096e742c47f47bcae1ace0002d4a9aa');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '1503e54b4e974f2d8a831349dfced313');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '27');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '28');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '29');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '2aa2007bdea8486692a3e5fb181c0a16');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '30');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '6e6e00b85f6a4108978932436314f002');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '745ba61f7a2e442a84690fbbe1e0954e');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '79ad1a8cfe094dd89f7b3529c03f73f1');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '7e65911542ad4386afddcec5956a1b85');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '88a2757fa8f843b29ea0a52bcfaf2484');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '89');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', '9eb90fe6b52b45089f52b7cab9ee720d');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', 'c977c4ca6c6041659afa9b77444a9f8d');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', 'd5fab999639e46f6a3d406a126c29b39');
INSERT INTO `sys_role_menu` VALUES ('66f2c2710c6e4d4ea1151ee83ebba322', 'f52880e90fe2447db851e8ba78fcb773');
