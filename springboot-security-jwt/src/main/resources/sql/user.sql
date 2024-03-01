/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : springboot-security-jwt

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 01/03/2024 14:53:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `role_id` int DEFAULT NULL COMMENT '用户角色',
  `photo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像地址',
  `status` int DEFAULT '1' COMMENT '用户状态 1 启用 2 停止',
  `register_date` datetime DEFAULT NULL COMMENT '用户注册时间',
  `contact_no` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系方式',
  `del_flag` int DEFAULT '0' COMMENT '0 存在，1 删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `email`, `username`, `password`, `role_id`, `photo`, `status`, `register_date`, `contact_no`, `del_flag`, `create_time`, `update_time`) VALUES (7, 'email_5592fab4bad4', 'admin', '$2a$10$5E6hO2K7sURNLhDgr062ae.xFXBElAw3J18Un7N5yE590lq5wzKFa', 2, 'photo_87fbffd263b6', 0, '2024-03-01 22:46:56', '51', 0, '2024-03-01 22:46:56', '2024-03-01 22:46:56');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
