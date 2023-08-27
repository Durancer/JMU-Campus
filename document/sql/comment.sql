/*
 Navicat Premium Data Transfer

 Source Server         : aliyun2
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 47.120.2.95:3306
 Source Schema         : comment

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 27/08/2023 15:20:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `post_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '帖子id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `to_user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '回复用户id',
  `root_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '根评论id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `hot` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '热度',
  `type` enum('root','answer') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论类型 根评论 | 回复评论',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_like
-- ----------------------------
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like`  (
  `like_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `comment_id` int(0) NOT NULL COMMENT '评论id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
