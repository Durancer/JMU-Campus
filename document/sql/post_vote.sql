/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : post_vote

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 02/07/2023 17:11:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`  (
  `vote_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投票id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子id',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票主题',
  `type` enum('multiple','radio') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票类型  单选|多选',
  `cycle` enum('day','week','month','year') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '投票周期  一天|一周|一月|一年',
  `amount` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '总投票人数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vote
-- ----------------------------

-- ----------------------------
-- Table structure for vote_option
-- ----------------------------
DROP TABLE IF EXISTS `vote_option`;
CREATE TABLE `vote_option`  (
  `option_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投票选项id',
  `vote_id` int(11) NOT NULL COMMENT '投票id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '投票选项',
  `num` int(11) NOT NULL DEFAULT 0 COMMENT '投票数量',
  PRIMARY KEY (`option_id`) USING BTREE,
  INDEX `vote_id`(`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vote_option
-- ----------------------------

-- ----------------------------
-- Table structure for vote_record
-- ----------------------------
DROP TABLE IF EXISTS `vote_record`;
CREATE TABLE `vote_record`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投票记录id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '投票用户id',
  `vote_id` int(11) NULL DEFAULT NULL COMMENT '投票id',
  `option_id` int(11) NULL DEFAULT NULL COMMENT '投票选项id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投票时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `option_id`(`option_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vote_record
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
