/*
 Navicat Premium Data Transfer

 Source Server         : mysqllocalhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : demo2

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 18/02/2020 17:02:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `passWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userType` int(2) NULL DEFAULT NULL,
  `userPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userEmail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(2) NULL DEFAULT NULL,
  `serCreateTime` datetime(0) NULL DEFAULT NULL,
  `userDetails` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0c22f339738b4b2a92c5ce4f4da9ab74', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('2', '1-1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('26447ab3378b4952adeef8fdf290ec7b', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('3', '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES ('357e3cb54718437a8b02a302ecfcc94a', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('3696307833804c6fb209e12547432895', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('3fb3cd77d7de4a4bb7d745031af51876', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('5006ba25047d49909c0f21cc6dcc3a7e', '1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('698d51a19d8a121ce581499d7b701668', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES ('8ca5ee067b2d49a3935de737f35f371e', '222222', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('90259d2072db4e9ea19766e656273fb0', '1-1', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);
INSERT INTO `user` VALUES ('f02a4da781184902bfb89dac4fcf57b2', '222222', '1', NULL, NULL, NULL, NULL, '2020-10-02 00:00:00', NULL);

SET FOREIGN_KEY_CHECKS = 1;
