/*
MySQL Data Transfer
Source Host: localhost
Source Database: cas
Target Host: localhost
Target Database: cas
Date: 2017/12/19 14:04:13
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `sex` varchar(50) DEFAULT NULL,
  `id` varchar(5) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `user` VALUES ('man', 'abcd', 'dc76e9f0c0006e8f919e0c515c66dbba3982f785', 'root');
