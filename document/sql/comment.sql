/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : comment

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 29/03/2023 19:07:29
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
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table comment
-- ----------------------------
DROP TRIGGER IF EXISTS `rootIdTg`;
delimiter ;;
CREATE TRIGGER `rootIdTg` BEFORE INSERT ON `comment` FOR EACH ROW IF NEW.type = 'root' then
	set NEW.root_id = NEW.id;
end if
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
