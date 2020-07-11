/*
 Navicat Premium Data Transfer

 Source Server         : testAli
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : rm-wz971ui03hdi94s1cao.mysql.rds.aliyuncs.com:3306
 Source Schema         : dealate

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 09/07/2020 11:31:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `command_level` int(8) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teachers` json NULL,
  `credit` decimal(4, 2) NOT NULL,
  `grade` json NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `students` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `student_id` int(8) NOT NULL,
  `course_id` int(8) NOT NULL,
  `score` decimal(6, 3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` int(5) NOT NULL,
  `grade` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `major` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `clazz` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NULL DEFAULT NULL,
  `course` json NULL,
  `enrollment_time` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` int(5) NOT NULL,
  `major` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flag` int(5) NOT NULL,
  `account` int(8) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
