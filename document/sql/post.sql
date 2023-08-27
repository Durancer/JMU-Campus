/*
 Navicat Premium Data Transfer

 Source Server         : aliyun2
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 47.120.2.95:3306
 Source Schema         : post

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 27/08/2023 15:20:18
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_mysql500_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for image_annex
-- ----------------------------
DROP TABLE IF EXISTS `image_annex`;
CREATE TABLE `image_annex`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parent_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '图片项目源行id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for like_post
-- ----------------------------
DROP TABLE IF EXISTS `like_post`;
CREATE TABLE `like_post`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `post_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '帖子id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发布内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '帖子创建时间',
  `status` int(0) NULL DEFAULT 0 COMMENT '帖子状态 0为审核中 | 1为公开 | 2被驳回',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_general
-- ----------------------------
DROP TABLE IF EXISTS `post_general`;
CREATE TABLE `post_general`  (
  `post_id` int(0) UNSIGNED NOT NULL COMMENT '帖子id',
  `view_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '阅读量',
  `like_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '点赞量',
  `comment_num` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '评论量',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post_topic
-- ----------------------------
DROP TABLE IF EXISTS `post_topic`;
CREATE TABLE `post_topic`  (
  `post_id` int(0) NULL DEFAULT NULL,
  `topic_id` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '话题名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`  (
  `vote_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投票id',
  `post_id` int(0) NULL DEFAULT NULL COMMENT '帖子id',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票主题',
  `type` enum('multiple','radio') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票类型  单选|多选',
  `cycle` enum('day','week','month','year') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票周期  一天|一周|一月|一年',
  `amount` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总投票人数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vote_option
-- ----------------------------
DROP TABLE IF EXISTS `vote_option`;
CREATE TABLE `vote_option`  (
  `option_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投票选项id',
  `vote_id` int(0) NOT NULL COMMENT '投票id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '投票选项',
  `num` int(0) NOT NULL DEFAULT 0 COMMENT '投票数量',
  PRIMARY KEY (`option_id`) USING BTREE,
  INDEX `vote_id`(`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vote_record
-- ----------------------------
DROP TABLE IF EXISTS `vote_record`;
CREATE TABLE `vote_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '投票记录id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '投票用户id',
  `vote_id` int(0) NULL DEFAULT NULL COMMENT '投票id',
  `option_id` int(0) NULL DEFAULT NULL COMMENT '投票选项id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '投票时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `option_id`(`option_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for image_annex_view
-- ----------------------------
DROP VIEW IF EXISTS `image_annex_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `image_annex_view` AS select `image_annex`.`id` AS `id`,`image_annex`.`parent_id` AS `parent_id`,concat(convert(`config`.`result` using utf8mb4),`image_annex`.`file_name`) AS `img_url` from (`image_annex` join `config`) where (`config`.`param` = 'img_url');

-- ----------------------------
-- View structure for post_view
-- ----------------------------
DROP VIEW IF EXISTS `post_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `post_view` AS select `post`.`id` AS `id`,`post`.`user_id` AS `user_id`,`post`.`content` AS `content`,`post`.`status` AS `status`,`post_general`.`view_num` AS `view_num`,`post_general`.`like_num` AS `like_num`,`post_general`.`comment_num` AS `comment_num`,`post`.`create_time` AS `create_time`,`post`.`title` AS `title` from (`post` join `post_general`) where (`post`.`id` = `post_general`.`post_id`);

SET FOREIGN_KEY_CHECKS = 1;
