/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 29/03/2023 19:07:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '参数',
  `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_mysql500_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源描述',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'default.jpg' COMMENT '用户头像',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'ta还没有个人介绍哦！' COMMENT '个人介绍',
  `sex` enum('0','1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别 0 匿 | 1 boy | 2 girl',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_general
-- ----------------------------
DROP TABLE IF EXISTS `user_general`;
CREATE TABLE `user_general`  (
  `user_id` int(0) UNSIGNED NOT NULL COMMENT '用户id',
  `view_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '总访问量',
  `comment_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '总评论量',
  `post_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '帖子量',
  `like_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '总点赞量',
  `fans_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '总粉丝量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_item
-- ----------------------------
DROP TABLE IF EXISTS `user_item`;
CREATE TABLE `user_item`  (
  `user_id` int(0) UNSIGNED NOT NULL COMMENT '用户id',
  `item_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '资源id',
  `num` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '资源数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for user_item_view
-- ----------------------------
DROP VIEW IF EXISTS `user_item_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_item_view` AS select `user_item`.`user_id` AS `user_id`,`user_item`.`num` AS `num`,`item`.`name` AS `name`,`item`.`desc` AS `desc`,concat(convert(`config`.`result` using utf8mb4),`item`.`image`) AS `image_url`,`user_item`.`item_id` AS `item_id` from ((`user_item` join `item`) join `config`) where ((`user_item`.`item_id` = `item`.`id`) and (`config`.`param` = 'img_url'));

-- ----------------------------
-- View structure for user_view
-- ----------------------------
DROP VIEW IF EXISTS `user_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_view` AS select `user`.`id` AS `id`,`user`.`username` AS `username`,`user`.`nickname` AS `nickname`,concat(convert(`config`.`result` using utf8mb4),`user`.`avatar`) AS `avatar_url`,`user`.`introduce` AS `introduce`,`user`.`sex` AS `sex`,`user`.`phone` AS `phone`,`user`.`create_time` AS `create_time` from (`user` join `config`) where (`config`.`param` = 'img_url');

SET FOREIGN_KEY_CHECKS = 1;
