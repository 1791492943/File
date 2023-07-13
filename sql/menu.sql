/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : network_disk

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 13/07/2023 16:30:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `menu_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '设置管理员权限', 'superAdmin:add');
INSERT INTO `menu` VALUES (2, '封禁管理员账号', 'superAdmin:ban');
INSERT INTO `menu` VALUES (3, '封禁普通账号', 'admin:ban');
INSERT INTO `menu` VALUES (4, '修改密码', 'user:updatePassword');
INSERT INTO `menu` VALUES (5, '创建文件夹', 'admin:newDirectory');
INSERT INTO `menu` VALUES (6, '移动文件', 'admin:moveFile');
INSERT INTO `menu` VALUES (7, '上传文件', 'user:upload');
INSERT INTO `menu` VALUES (8, '下载文件', 'visitor:downloadFile');
INSERT INTO `menu` VALUES (9, '删除文件', 'admin:deleteFile');

SET FOREIGN_KEY_CHECKS = 1;
