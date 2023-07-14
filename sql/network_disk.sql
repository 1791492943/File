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

 Date: 14/07/2023 13:51:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件原名',
  `final_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最终名称',
  `file_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `file_path_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径+名称',
  `size` bigint NOT NULL COMMENT '大小',
  `suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '后缀',
  `upload_date` datetime NOT NULL COMMENT '上传时间',
  `upload_user` int NOT NULL COMMENT '上传人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_info
-- ----------------------------

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

-- ----------------------------
-- Table structure for operate
-- ----------------------------
DROP TABLE IF EXISTS `operate`;
CREATE TABLE `operate`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '操作人',
  `create_date` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `behavior` int NULL DEFAULT NULL COMMENT '1:删除 2:移动 3:重命名 4:新建文件夹',
  `file` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '被操作的文件',
  `event` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '事件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operate
-- ----------------------------
INSERT INTO `operate` VALUES (117, 10001, '2023-07-14 10:47:24', 2, 'D:/network-disk/download/新建文本文档.txt', '移动文件到：D:/network-disk/download/新建文件夹');
INSERT INTO `operate` VALUES (118, 10001, '2023-07-14 10:47:30', 2, 'D:/network-disk/download/新建文件夹/新建文本文档.txt', '移动文件到：D:\\network-disk\\download');
INSERT INTO `operate` VALUES (119, 10001, '2023-07-14 10:47:43', 2, 'D:/network-disk/download/新建文件夹(3)/新建文件夹', '移动文件到：D:/network-disk/download/新建文件夹(1)');
INSERT INTO `operate` VALUES (120, 10001, '2023-07-14 10:47:55', 2, 'D:/network-disk/download/新建文本文档.txt', '移动文件到：D:/network-disk/download/新建文件夹(2)');
INSERT INTO `operate` VALUES (121, 10001, '2023-07-14 10:48:42', 1, 'D:\\network-disk\\download\\新建文件夹(2)\\新建文本文档.txt', '45e99cbf-de15-448d-af18-3a39e96fbe20.txt');
INSERT INTO `operate` VALUES (122, 10001, '2023-07-14 10:48:55', 1, 'D:\\network-disk\\download\\45e99cbf-de15-448d-af18-3a39e96fbe20.txt', '869275ad-7406-42aa-8be7-cac022f72ee0.txt');
INSERT INTO `operate` VALUES (123, 10001, '2023-07-14 10:48:56', 1, 'D:\\network-disk\\download\\869275ad-7406-42aa-8be7-cac022f72ee0.txt', '82ae71b3-3e23-4dc5-902d-c7613fac4490.txt');
INSERT INTO `operate` VALUES (124, 10001, '2023-07-14 10:49:47', 1, 'D:\\network-disk\\download\\82ae71b3-3e23-4dc5-902d-c7613fac4490.txt', '244919c1-8b1f-4260-b0ae-39025199a9c2.txt');
INSERT INTO `operate` VALUES (125, 10001, '2023-07-14 10:49:49', 1, 'D:\\network-disk\\download\\244919c1-8b1f-4260-b0ae-39025199a9c2.txt', '35aa5e4c-98a1-41d6-8325-b765a22ac593.txt');
INSERT INTO `operate` VALUES (126, 10001, '2023-07-14 10:49:50', 1, 'D:\\network-disk\\download\\35aa5e4c-98a1-41d6-8325-b765a22ac593.txt', 'd820766c-d9aa-4b95-bd93-43cf78ee7a4b.txt');
INSERT INTO `operate` VALUES (127, 10001, '2023-07-14 10:50:18', 1, 'D:\\network-disk\\download\\d820766c-d9aa-4b95-bd93-43cf78ee7a4b.txt', '01a1da47-4c37-4891-b9ce-6381ec766f29.txt');
INSERT INTO `operate` VALUES (128, 10001, '2023-07-14 10:50:19', 4, 'D:\\network-disk\\download\\新建文件夹', '新建文件夹');
INSERT INTO `operate` VALUES (129, 10001, '2023-07-14 10:50:20', 4, 'D:\\network-disk\\download\\新建文件夹(1)', '新建文件夹');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 'superAdmin');
INSERT INTO `role` VALUES (2, '管理员', 'admin');
INSERT INTO `role` VALUES (3, '用户', 'user');
INSERT INTO `role` VALUES (4, '访客', 'visitor');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 2);
INSERT INTO `role_menu` VALUES (1, 3);
INSERT INTO `role_menu` VALUES (1, 4);
INSERT INTO `role_menu` VALUES (1, 5);
INSERT INTO `role_menu` VALUES (1, 6);
INSERT INTO `role_menu` VALUES (1, 7);
INSERT INTO `role_menu` VALUES (1, 8);
INSERT INTO `role_menu` VALUES (1, 9);
INSERT INTO `role_menu` VALUES (2, 3);
INSERT INTO `role_menu` VALUES (2, 4);
INSERT INTO `role_menu` VALUES (2, 5);
INSERT INTO `role_menu` VALUES (2, 6);
INSERT INTO `role_menu` VALUES (2, 7);
INSERT INTO `role_menu` VALUES (2, 8);
INSERT INTO `role_menu` VALUES (2, 9);
INSERT INTO `role_menu` VALUES (3, 4);
INSERT INTO `role_menu` VALUES (3, 7);
INSERT INTO `role_menu` VALUES (3, 6);
INSERT INTO `role_menu` VALUES (4, 8);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_status` int NOT NULL DEFAULT 0 COMMENT '0:启用 1:禁用',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '10001', '123456', 'admin', 0, '2023-06-14 19:51:26');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
