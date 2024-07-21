/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : wsa

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 21/07/2024 23:12:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `roleId` int(20) NOT NULL,
  `authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` VALUES (1, 'organizer', 1, 'organizer', 1);
INSERT INTO `authorities` VALUES (2, 'admin', 3, 'admin', 2);
INSERT INTO `authorities` VALUES (3, 'volunteer', 2, 'volunteer', 3);

-- ----------------------------
-- Table structure for credentials
-- ----------------------------
DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `volunteerId` int(11) DEFAULT NULL,
  `credentialName` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `credentialUrl` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of credentials
-- ----------------------------
INSERT INTO `credentials` VALUES (1, 1, 'test', 'http://localhost:8080/test.png');

-- ----------------------------
-- Table structure for emails
-- ----------------------------
DROP TABLE IF EXISTS `emails`;
CREATE TABLE `emails`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `toEmail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('pending','sent','failed') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'pending',
  `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sendAt` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eventfacilities
-- ----------------------------
DROP TABLE IF EXISTS `eventfacilities`;
CREATE TABLE `eventfacilities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eventId` int(11) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eventfacilities
-- ----------------------------
INSERT INTO `eventfacilities` VALUES (7, 5, 'Transform Hospital Group - Cardiff Clinic');
INSERT INTO `eventfacilities` VALUES (8, 5, 'St David\'s Hospital');
INSERT INTO `eventfacilities` VALUES (9, 5, 'Fat Freezing Cardiff - Portland Body Clinic');
INSERT INTO `eventfacilities` VALUES (10, 5, 'Life Line Screening');
INSERT INTO `eventfacilities` VALUES (11, 5, 'Cathedral Road Clinic');
INSERT INTO `eventfacilities` VALUES (12, 5, 'Veincentre');
INSERT INTO `eventfacilities` VALUES (13, 5, 'Elated hair salon & beauty clinic');
INSERT INTO `eventfacilities` VALUES (14, 5, 'Crwys Medical Centre');
INSERT INTO `eventfacilities` VALUES (15, 5, 'St David\'s Children\'s Centre');
INSERT INTO `eventfacilities` VALUES (16, 5, 'Boots Hearingcare Cardiff');
INSERT INTO `eventfacilities` VALUES (17, 5, 'Medical Systems Ltd');
INSERT INTO `eventfacilities` VALUES (18, 5, 'Ysbyty Calon y Ddraig - Dragon\'s Heart Hospital');
INSERT INTO `eventfacilities` VALUES (19, 5, 'Whitchurch Road Surgery');
INSERT INTO `eventfacilities` VALUES (20, 4, 'CAU');
INSERT INTO `eventfacilities` VALUES (21, 4, 'Cardiff Royal Infirmary');

-- ----------------------------
-- Table structure for eventregistrations
-- ----------------------------
DROP TABLE IF EXISTS `eventregistrations`;
CREATE TABLE `eventregistrations`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) NOT NULL,
  `volunteerId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `status` enum('pending','accepted','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'pending',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eventregistrations
-- ----------------------------
INSERT INTO `eventregistrations` VALUES (1, 4, 1, 2, 'accepted');
INSERT INTO `eventregistrations` VALUES (2, 4, 2, 1, 'accepted');

-- ----------------------------
-- Table structure for eventroles
-- ----------------------------
DROP TABLE IF EXISTS `eventroles`;
CREATE TABLE `eventroles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) NOT NULL,
  `roleName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `roleDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `volunteerCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eventroles
-- ----------------------------
INSERT INTO `eventroles` VALUES (10, 5, 'role2', NULL, 2);
INSERT INTO `eventroles` VALUES (11, 4, 'role1', NULL, 2);
INSERT INTO `eventroles` VALUES (12, 4, 'role2', NULL, 1);
INSERT INTO `eventroles` VALUES (13, 4, 'role3', NULL, 3);

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organizerId` bigint(20) NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('Awaiting review','Passed','Rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Awaiting review',
  `pointsAwarded` int(11) DEFAULT 0,
  `startDate` datetime(0) NOT NULL,
  `endDate` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of events
-- ----------------------------
INSERT INTO `events` VALUES (1, 1, 'event1', 'test event', 'cardiff', 'Passed', 100, '2024-07-12 04:26:05', '2024-07-12 07:26:16');
INSERT INTO `events` VALUES (4, 1, 'test2', 'test2', '15 Pen-Y-Lan Rd, Cardiff CF24 3PG, UK', 'Awaiting review', 77, '2024-07-20 09:00:00', '2024-07-20 22:00:00');
INSERT INTO `events` VALUES (5, 1, 'tes', 'tes', 'Julian Hodge Building, Colum Dr, Cardiff CF10 3EU, UK', 'Awaiting review', 2, '2024-07-14 01:08:36', '2024-07-14 09:06:42');

-- ----------------------------
-- Table structure for messages
-- ----------------------------
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) DEFAULT NULL,
  `senderId` bigint(20) DEFAULT NULL,
  `content` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `timestamp` datetime(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `event_id`(`eventId`) USING BTREE,
  INDEX `sender_id`(`senderId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of messages
-- ----------------------------
INSERT INTO `messages` VALUES (1, 4, 1, 'test message', '2024-07-21 22:06:41');
INSERT INTO `messages` VALUES (2, 4, 1, '1', '2024-07-21 22:30:18');
INSERT INTO `messages` VALUES (3, 4, 1, '2', '2024-07-21 22:55:21');
INSERT INTO `messages` VALUES (4, 4, 1, 'test', '2024-07-21 22:55:36');
INSERT INTO `messages` VALUES (5, 4, 1, '123', '2024-07-21 22:55:45');
INSERT INTO `messages` VALUES (6, 4, 1, 'test time', '2024-07-21 23:01:00');
INSERT INTO `messages` VALUES (7, 4, 1, 'test time2', '2024-07-21 23:04:25');

-- ----------------------------
-- Table structure for organizer
-- ----------------------------
DROP TABLE IF EXISTS `organizer`;
CREATE TABLE `organizer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `organizationName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `organizationDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for redemptionrecords
-- ----------------------------
DROP TABLE IF EXISTS `redemptionrecords`;
CREATE TABLE `redemptionrecords`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `pointsUsed` int(11) NOT NULL,
  `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rewardstore
-- ----------------------------
DROP TABLE IF EXISTS `rewardstore`;
CREATE TABLE `rewardstore`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `itemDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `pointsRequired` int(11) NOT NULL,
  `stock` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`roleId`) USING BTREE,
  UNIQUE INDEX `role_name`(`roleName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (3, 'admin');
INSERT INTO `roles` VALUES (1, 'organizer');
INSERT INTO `roles` VALUES (2, 'volunteer');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginId` bigint(50) DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 1, 'organizer', '07961555608', '$2a$10$Xr6WrdQBrtCumI6T9UN8cOaLkgNUQzEw5sIyw3lDjMSFOrOo2KlRi', '13253348930@163.com', 'https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png', 1);
INSERT INTO `users` VALUES (2, 2, 'admin', '13253348930', '$2a$10$oerriH6/v5CkxPgoIJpNv.jJj1h7ATIqfpXmlyHDPbR2M7HXJpUoe', 'gongj13@cardiff.ac.uk', 'https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png', 1);
INSERT INTO `users` VALUES (3, 3, 'volunteer', '13370776044', '$10$oerriH6/v5CkxPgoIJpNv.jJj1h7ATIqfpXmlyHDPbR2M7HXJpUoe', '543800896@qq.com', 'https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png', 1);
INSERT INTO `users` VALUES (4, 4, 'volunteer2', '15616547894', '$10$Xr6WrdQBrtCumI6T9UN8cOaLkgNUQzEw5sIyw3lDjMSFOrOo2KlRi', 'gjaiodjoia@163.com', 'https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png', 1);

-- ----------------------------
-- Table structure for volunteer
-- ----------------------------
DROP TABLE IF EXISTS `volunteer`;
CREATE TABLE `volunteer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `experience` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `firstAid` tinyint(1) DEFAULT NULL,
  `dbs` tinyint(1) DEFAULT NULL,
  `kudosPoints` int(11) DEFAULT 0,
  `eventCount` int(11) DEFAULT 0,
  `level` enum('Newbie','Bronze','Silver','Gold') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Newbie',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of volunteer
-- ----------------------------
INSERT INTO `volunteer` VALUES (1, 3, 'Olympic', 1, 1, 7, 2, 'Newbie');
INSERT INTO `volunteer` VALUES (2, 4, 'Olympic', 1, 0, 11, 1, 'Newbie');

-- ----------------------------
-- Table structure for volunteerratings
-- ----------------------------
DROP TABLE IF EXISTS `volunteerratings`;
CREATE TABLE `volunteerratings`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) NOT NULL,
  `organizerId` bigint(20) NOT NULL,
  `volunteerId` bigint(20) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
