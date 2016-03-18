/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.136_3306
Source Server Version : 50528
Source Host           : 192.168.1.136:3306
Source Database       : ticket

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2015-09-22 18:53:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_access_system
-- ----------------------------
DROP TABLE IF EXISTS `t_access_system`;
CREATE TABLE `t_access_system` (
  `uuid` varchar(32) NOT NULL,
  `systemIndex` int(11) DEFAULT NULL COMMENT '系统索引号',
  `systemName` varchar(32) DEFAULT NULL COMMENT '请求接入系统名称',
  `appId` varchar(32) DEFAULT NULL COMMENT '系统的id',
  `appKey` varchar(32) DEFAULT NULL COMMENT 'key',
  `userId` varchar(32) DEFAULT NULL COMMENT '提交申请的用户Id',
  `status` int(1) DEFAULT NULL COMMENT '状态（0待审核 1正常 2冻结 3 未通过）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接入系统相关信息';

-- ----------------------------
-- Records of t_access_system
-- ----------------------------
INSERT INTO `t_access_system` VALUES ('1f7b443b2588424d9ba605e5e939e942', '0', '12345', '1e7e508b90404d8fb49b115f23f80e71', 'd09095157a7b4eb2a5ef8726075588e2', '74e4e9efcf3f4a6596a55c5523a22b0e', '1', 'first', '2015-09-15 11:23:14');
INSERT INTO `t_access_system` VALUES ('266ea499d3134194ac3ddf59a9a0afb2', '0', 'jsy', '01f3d1a2437e460380ba596b1d47075a', '7d43deaaac8544d2910ee9fe850c0713', '74e4e9efcf3f4a6596a55c5523a22b0e', '2', 'first', '2015-09-15 11:24:19');
INSERT INTO `t_access_system` VALUES ('41e0b1f33aee4b8594b859822c7da135', null, '123', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '3', null, '2015-09-19 16:34:46');
INSERT INTO `t_access_system` VALUES ('68135291921c45248f557b56317ba861', '0', '123', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '0', null, '2015-09-19 16:39:07');
INSERT INTO `t_access_system` VALUES ('8b7be7dc84364fea93cd0052d34847f5', '0', '123', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '0', null, '2015-09-19 16:34:08');
INSERT INTO `t_access_system` VALUES ('9945f56835cb46efb96100ba5cec0354', '0', '123', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '0', null, '2015-09-19 16:39:15');
INSERT INTO `t_access_system` VALUES ('9a90ce37fc6b468cbff798a904c1f158', '0', '1234567890', '23c413ebc7894725b330769073644b3a', '5a3a675e604a4ea39043b9fc239ddad0', '74e4e9efcf3f4a6596a55c5523a22b0e', '1', null, '2015-09-15 17:22:31');
INSERT INTO `t_access_system` VALUES ('adfd52dc48fc48ab96ff94561ebcd4e1', '0', 'jsy', '1b7342230432427aabc6aad60fe55c5a', '696d292c40834e59b397319de4060ab6', '74e4e9efcf3f4a6596a55c5523a22b0e', '1', 'first', '2015-09-15 13:38:57');
INSERT INTO `t_access_system` VALUES ('f0c9f891353243bcbe5d5efe0f114ed7', '0', '123', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '0', null, '2015-09-19 16:38:25');
INSERT INTO `t_access_system` VALUES ('f3b973d2e1a34947b2f065a754ff5064', '9', 'asd', null, null, '74e4e9efcf3f4a6596a55c5523a22b0e', '1', null, '2015-09-19 16:32:55');
INSERT INTO `t_access_system` VALUES ('faijeoifjaijfaj;fkjiejfpoiwjfpeo', '8', 'fanaer', '53c413gbc7894725b125769863604bh4', 'a123', '74e4e9efcf3f4a6596a55c5523a22b0e', '1', null, '2015-09-15 09:48:28');

-- ----------------------------
-- Table structure for t_commsequence
-- ----------------------------
DROP TABLE IF EXISTS `t_commsequence`;
CREATE TABLE `t_commsequence` (
  `name` varchar(32) NOT NULL,
  `id` bigint(20) NOT NULL,
  `note` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_commsequence
-- ----------------------------
INSERT INTO `t_commsequence` VALUES ('t_access_system', '9', '接入系统序号');

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `uuid` varchar(32) NOT NULL,
  `name` varchar(16) DEFAULT NULL COMMENT '部门名称',
  `createTime` datetime DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `status` int(1) DEFAULT NULL COMMENT '0关闭，1正常',
  `systemIndex` int(11) DEFAULT NULL COMMENT '系统uuId',
  `allotType` int(1) DEFAULT NULL COMMENT '分配类型 0 自动分配 1手动分配',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门\r\n';

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('43irlp3p4l6k7m35m23b6b4n6md8b2k4', '产品部', '2015-09-15 14:56:12', null, '1', '0', null);
INSERT INTO `t_department` VALUES ('6447dcb19b654833802ed90fe4cdf7b7', 'adsas', '2015-09-21 09:47:12', null, '1', '0', null);
INSERT INTO `t_department` VALUES ('874f587f48a149bc938e6084bc55fdda', 'dsaf', '2015-09-21 09:43:32', null, '1', '0', null);
INSERT INTO `t_department` VALUES ('asdaegfaiuhefahfeuihawiuehfawueh', '研发部', '2015-09-14 18:27:17', null, '1', '0', null);

-- ----------------------------
-- Table structure for t_fanaer_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_fanaer_knowledge`;
CREATE TABLE `t_fanaer_knowledge` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL COMMENT '提交人Id',
  `question` varchar(32) DEFAULT NULL COMMENT '问题',
  `answer` varchar(1024) DEFAULT NULL COMMENT '回答',
  `attatmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attatmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态（1正常，2待审核，3失效, 4删除）',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fanaer_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for t_fanaer_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_fanaer_reply`;
CREATE TABLE `t_fanaer_reply` (
  `uuid` varchar(32) NOT NULL,
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `userId` varchar(32) DEFAULT NULL COMMENT '工作人员Id',
  `attachmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attachmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `replyContent` varchar(1024) DEFAULT NULL COMMENT '回复内容',
  `status` int(1) DEFAULT NULL COMMENT '0工单处理回复 1客户回复  2已删除',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fanaer_reply
-- ----------------------------

-- ----------------------------
-- Table structure for t_fanaer_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_fanaer_ticket`;
CREATE TABLE `t_fanaer_ticket` (
  `uuid` varchar(32) NOT NULL,
  `question` varchar(64) DEFAULT NULL COMMENT '问题',
  `content` varchar(1024) DEFAULT NULL COMMENT '问题描述',
  `level` int(1) DEFAULT NULL COMMENT '紧急程度',
  `outerUserId` varchar(32) DEFAULT NULL COMMENT '客户Id',
  `attamentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `attamentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `status` int(1) DEFAULT NULL COMMENT '状态（0 等待处理，1正在处理，2等待用户反馈，3问题关闭,4已删除）',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `ownerId` varchar(32) DEFAULT NULL COMMENT '问题持有人Id',
  `createTime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '紧急原因',
  `closeTime` datetime DEFAULT NULL COMMENT '工单关闭时间',
  `email` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fanaer_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for t_fanaer_ticket_move
-- ----------------------------
DROP TABLE IF EXISTS `t_fanaer_ticket_move`;
CREATE TABLE `t_fanaer_ticket_move` (
  `uuid` varchar(32) NOT NULL,
  `currentUserId` varchar(32) DEFAULT NULL COMMENT '问题当前所有者',
  `lastUserId` varchar(32) DEFAULT NULL COMMENT '问题上一个所有者',
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `createtime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '流转原因',
  `status` int(1) DEFAULT NULL COMMENT '0待审核 1 通过 2驳回 3已删除',
  `refuseReason` varchar(512) DEFAULT NULL COMMENT '驳回原因',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fanaer_ticket_move
-- ----------------------------

-- ----------------------------
-- Table structure for t_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge`;
CREATE TABLE `t_knowledge` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL COMMENT '提交人Id',
  `question` varchar(32) DEFAULT NULL COMMENT '问题',
  `answer` varchar(1024) DEFAULT NULL COMMENT '回答',
  `attatmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attatmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态（1正常，2待审核，3失效, 4删除）',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='知识库';

-- ----------------------------
-- Records of t_knowledge
-- ----------------------------
INSERT INTO `t_knowledge` VALUES ('aefaefaefaefawefwaefawefaweawfa2', '2d2b7e1d31be4edc9d00f04f8d2b3f5e', 'dfe', '的vfes', '', '', 'b13091bfd814453ea0b079d68bf3e446', '2015-09-15 16:58:59', '1');
INSERT INTO `t_knowledge` VALUES ('aefaefaefaefawefwaefawefaweawfaw', '2d2b7e1d31be4edc9d00f04f8d2b3f5e', '阿大声道', '阿斯达', null, null, 'b13091bfd814453ea0b079d68bf3e446', '2015-09-15 16:58:59', '1');

-- ----------------------------
-- Table structure for t_question_type
-- ----------------------------
DROP TABLE IF EXISTS `t_question_type`;
CREATE TABLE `t_question_type` (
  `uuid` varchar(32) NOT NULL,
  `departmentId` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `typeName` varchar(32) DEFAULT NULL COMMENT '问题分类名称',
  `parentId` varchar(32) DEFAULT NULL COMMENT '问题大分类（如果是小分类，则为父类问题类型Id）',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0禁用，1正常，2已删除',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `systemIndex` int(11) DEFAULT NULL COMMENT '系统Id',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题分类\r\n';

-- ----------------------------
-- Records of t_question_type
-- ----------------------------
INSERT INTO `t_question_type` VALUES ('3869c309bc03446388870f67cb428018', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '123123 ', 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-22 16:59:00', '1', null, '0');
INSERT INTO `t_question_type` VALUES ('ajefiajwoiefjoaiaweofjoawifejpia', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '研究类', '0', '2015-09-18 14:02:34', '1', null, '0');
INSERT INTO `t_question_type` VALUES ('asdasdfawefawfefawfewafawefawefw', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '技术类', '0', '2015-09-16 17:46:19', '1', '', '0');
INSERT INTO `t_question_type` VALUES ('b13091bfd814453ea0b079d68bf3e446', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '大神大神的', 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-17 09:46:05', '1', null, '0');
INSERT INTO `t_question_type` VALUES ('e3026677bbb84e9090539c70eec37fb4', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '12312', 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-22 16:58:25', '1', null, '0');
INSERT INTO `t_question_type` VALUES ('fgserg43tge56ge4g5w45gw3tg3w4g34', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '产品类', 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-16 17:46:19', '1', null, '0');
INSERT INTO `t_question_type` VALUES ('safegsagfaweftiqyo8rho297ryo2qr3', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '呵呵呵', 'asdasdfawefawfefawfewafawefawefw', '2015-09-21 17:55:14', '1', null, '0');

-- ----------------------------
-- Table structure for t_question_type_user_map
-- ----------------------------
DROP TABLE IF EXISTS `t_question_type_user_map`;
CREATE TABLE `t_question_type_user_map` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL COMMENT '用户Id',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0失效 1正常 2已删除',
  `systemIndex` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设置用户处理问题类型的关系';

-- ----------------------------
-- Records of t_question_type_user_map
-- ----------------------------
INSERT INTO `t_question_type_user_map` VALUES ('cb7a185892d2408ca62c188e4d37d343', '9eefc5cf728343d6883eee13167006da', 'e7b4d0b4874e47b6bc04e8a7137776f0', '2015-09-22 16:58:25', '1', '0');

-- ----------------------------
-- Table structure for t_quick_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_quick_reply`;
CREATE TABLE `t_quick_reply` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0 隐藏 1显示 2已删除',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快速回复';

-- ----------------------------
-- Records of t_quick_reply
-- ----------------------------
INSERT INTO `t_quick_reply` VALUES ('207d765425d04fefb0ef39a28189c78b', '0b61eea23a9c406381d3a02cc432773e', '大神大神的', '啊沙发发二娃范围', '2015-09-16 16:37:12', '1');

-- ----------------------------
-- Table structure for t_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `uuid` varchar(32) NOT NULL,
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `userId` varchar(32) DEFAULT NULL COMMENT '回复人员Id',
  `attachmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attachmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `replyContent` varchar(1024) DEFAULT NULL COMMENT '回复内容',
  `status` int(1) DEFAULT NULL COMMENT '0工单处理回复 1客户回复 ',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户和客服人员之间的回复记录';

-- ----------------------------
-- Records of t_reply
-- ----------------------------
INSERT INTO `t_reply` VALUES ('b06b2a09e0324c51862d5366db39916h', '1cfbd24ac5514ce3b6630f4f9aa2531f', 'a980879fc8cd4a2ba6317ecf5daf19e0', null, null, '的房顶上v而未冬天该说的地方成vsdegfvs想法给第三方丝袜的飒飒都是', null, null);
INSERT INTO `t_reply` VALUES ('b06b2a09e0324c51862d5366db3991db', '1cfbd24ac5514ce3b6630f4f9aa2531f', '2d2b7e1d31be4edc9d00f04f8d2b3f5e', null, null, '撒嗲嗲哈哈地还打挥洒好滴', '1', '2015-09-16 09:48:00');
INSERT INTO `t_reply` VALUES ('b076891d28544517ae972292bc6102d6', '1cfbd24ac5514ce3b6630f4f9aa2531f', 'a980879fc8cd4a2ba6317ecf5daf19e0', null, null, 'werwfvret35t3edgr gedx大风歌不过 个人分国别的四五个给我二跟', '1', '2015-09-16 09:46:59');

-- ----------------------------
-- Table structure for t_sys8_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_sys8_knowledge`;
CREATE TABLE `t_sys8_knowledge` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL COMMENT '提交人Id',
  `question` varchar(32) DEFAULT NULL COMMENT '问题',
  `answer` varchar(1024) DEFAULT NULL COMMENT '回答',
  `attatmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attatmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态（1正常，2待审核，3失效, 4删除）',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys8_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys8_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_sys8_reply`;
CREATE TABLE `t_sys8_reply` (
  `uuid` varchar(32) NOT NULL,
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `userId` varchar(32) DEFAULT NULL COMMENT '回复人员Id',
  `attachmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attachmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `replyContent` varchar(1024) DEFAULT NULL COMMENT '回复内容',
  `status` int(1) DEFAULT NULL COMMENT '0工单处理回复 1客户回复',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys8_reply
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys8_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_sys8_ticket`;
CREATE TABLE `t_sys8_ticket` (
  `uuid` varchar(32) NOT NULL,
  `question` varchar(64) DEFAULT NULL COMMENT '问题',
  `content` varchar(1024) DEFAULT NULL COMMENT '问题描述',
  `level` int(1) DEFAULT NULL COMMENT '紧急程度',
  `outerUserId` varchar(32) DEFAULT NULL COMMENT '客户Id',
  `attamentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `attamentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `status` int(1) DEFAULT NULL COMMENT '状态（0 等待处理，1正在处理，2等待用户反馈，3问题关闭,4已删除）',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `ownerId` varchar(32) DEFAULT NULL COMMENT '问题持有人Id',
  `createTime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '紧急原因',
  `closeTime` datetime DEFAULT NULL COMMENT '工单关闭时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys8_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys8_ticket_move
-- ----------------------------
DROP TABLE IF EXISTS `t_sys8_ticket_move`;
CREATE TABLE `t_sys8_ticket_move` (
  `uuid` varchar(32) NOT NULL,
  `toUserId` varchar(32) DEFAULT NULL COMMENT '流转目的人ID',
  `fromUserId` varchar(32) DEFAULT NULL COMMENT '申请流转人ID',
  `audtId` varchar(32) DEFAULT NULL COMMENT '审核人Id',
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `createtime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '流转原因',
  `status` int(1) DEFAULT NULL COMMENT '0待审核 1 通过 2驳回 3已删除',
  `refuseReason` varchar(512) DEFAULT NULL COMMENT '驳回原因',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys8_ticket_move
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys9_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `t_sys9_knowledge`;
CREATE TABLE `t_sys9_knowledge` (
  `uuid` varchar(32) NOT NULL,
  `userId` varchar(32) DEFAULT NULL COMMENT '提交人Id',
  `question` varchar(32) DEFAULT NULL COMMENT '问题',
  `answer` varchar(1024) DEFAULT NULL COMMENT '回答',
  `attatmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attatmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `createTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态（1正常，2待审核，3失效, 4删除）',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys9_knowledge
-- ----------------------------
INSERT INTO `t_sys9_knowledge` VALUES ('176c3d8da4a44e90a1fd66f06917c1df', null, '纳新', '那些年', null, null, '-1', '2015-09-22 15:30:41', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('36fac05171f546d5b735d82db6290433', null, '那西恩', '那些年错过的大雨', null, null, '-1', '2015-09-22 15:32:29', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('38a4206bf2bb4b9fac81fcbdfe7a8585', null, '纳新', '那些年', null, null, '-1', '2015-09-22 15:31:12', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('449099b26bb84bf19c3774bedad8830f', null, 'fool', 'sd ', null, null, '3869c309bc03446388870f67cb428018', '2015-09-22 18:22:19', '2');
INSERT INTO `t_sys9_knowledge` VALUES ('44ee482f8f424ceb8728614b5af0d055', null, '苹果', '烦烦烦', null, null, 'safegsagfaweftiqyo8rho297ryo2qr3', '2015-09-22 18:19:30', '2');
INSERT INTO `t_sys9_knowledge` VALUES ('550a3cb808ad413cb8daf9d5954d58d7', null, '苹果', '烦烦烦', null, null, 'safegsagfaweftiqyo8rho297ryo2qr3', '2015-09-22 18:20:30', '2');
INSERT INTO `t_sys9_knowledge` VALUES ('5feb113bbf4a415d88d39a7db84054a2', null, '纳新', '那些年', null, null, '-1', '2015-09-22 15:32:14', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('aa92f5e9238b460097232bb76339a750', null, '纳新', '那些年', null, null, '-1', '2015-09-22 15:29:44', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('aefaefaefaefawefwaefawefaweajf42', '4d1133b61b9148b68af764fef32bc520', 'sds', '若风', null, null, 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-22 14:36:57', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('aefaefaefaefawefwaefawsddfe3jf4r', '4d1133b61b9148b68af764fef32bc520', ' 阿斯蒂芬', '丰富的福特为丰田认为的国府田仍然天赋却发生鬼玩人共同发起微微风格外', null, null, 'ajefiajwoiefjoaiaweofjoawifejpia', '2015-09-22 14:37:01', '1');
INSERT INTO `t_sys9_knowledge` VALUES ('d3bf12e52a6c44c8b7fc476408414525', null, '苹果人', '烦烦烦', null, null, 'safegsagfaweftiqyo8rho297ryo2qr3', '2015-09-22 18:20:54', '2');

-- ----------------------------
-- Table structure for t_sys9_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_sys9_reply`;
CREATE TABLE `t_sys9_reply` (
  `uuid` varchar(32) NOT NULL,
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `userId` varchar(32) DEFAULT NULL COMMENT '回复人员Id',
  `attachmentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `attachmentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `replyContent` varchar(1024) DEFAULT NULL COMMENT '回复内容',
  `status` int(1) DEFAULT NULL COMMENT '0工单处理回复 1客户回复',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys9_reply
-- ----------------------------
INSERT INTO `t_sys9_reply` VALUES ('', null, null, null, null, null, '0', null);
INSERT INTO `t_sys9_reply` VALUES ('1c2f418778f6447ea6363c631ea1142s', '2013cdd8be7b4f3e9e00b5fd649sdcbf', 'a980879fc8cd4a2ba6317ecf5daf19e0', null, null, '我们这里是百居易电子商务有限公司', '1', '2015-09-21 17:29:44');
INSERT INTO `t_sys9_reply` VALUES ('1c2f418778f6447ea6363c631ea1143s', '2013cdd8be7b4f3e9e00b5fd649sdcbf', 'a980879fc8cd4a2ba6317ecf5daf19e0', null, null, '我们这里有强大的团队', '1', '2015-09-23 17:31:48');
INSERT INTO `t_sys9_reply` VALUES ('2013cdd8be7b4f3e9e00b5fd649sdcb2', '2013cdd8be7b4f3e9e00b5fd649sdcbf', 'a980879fc8cd4a2ba6317ecf5daf19e0', null, null, '你是？', '0', '2015-09-19 17:34:16');

-- ----------------------------
-- Table structure for t_sys9_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_sys9_ticket`;
CREATE TABLE `t_sys9_ticket` (
  `uuid` varchar(32) NOT NULL,
  `question` varchar(64) DEFAULT NULL COMMENT '问题',
  `content` varchar(1024) DEFAULT NULL COMMENT '问题描述',
  `level` int(1) DEFAULT NULL COMMENT '紧急程度',
  `outerUserId` varchar(32) DEFAULT NULL COMMENT '客户Id',
  `attamentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `attamentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `status` int(1) DEFAULT NULL COMMENT '状态（0 等待处理，1正在处理，2等待用户反馈，3问题关闭,4已删除）',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `ownerId` varchar(32) DEFAULT NULL COMMENT '问题持有人Id',
  `createTime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '紧急原因',
  `closeTime` datetime DEFAULT NULL COMMENT '工单关闭时间',
  `email` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys9_ticket
-- ----------------------------
INSERT INTO `t_sys9_ticket` VALUES ('2013cdd8be7b4f3e9e00b5fd649sdcbf', '123', '1231', '1', '5247bd07b0fd42eda5272e47cb65c7b0', null, null, '0', 'ajefiajwoiefjoaiaweofjoawifejpia', '78a3149f9058490fbfc54e228a759542', '2015-09-22 17:08:41', null, null, null, null);

-- ----------------------------
-- Table structure for t_sys9_ticket_move
-- ----------------------------
DROP TABLE IF EXISTS `t_sys9_ticket_move`;
CREATE TABLE `t_sys9_ticket_move` (
  `uuid` varchar(32) NOT NULL,
  `toUserId` varchar(32) DEFAULT NULL COMMENT '流转目的人ID',
  `fromUserId` varchar(32) DEFAULT NULL COMMENT '申请流转人ID',
  `audtId` varchar(32) DEFAULT NULL COMMENT '审核人Id',
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `createtime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '流转原因',
  `status` int(1) DEFAULT NULL COMMENT '0待审核 1 通过 2驳回 3已删除',
  `refuseReason` varchar(512) DEFAULT NULL COMMENT '驳回原因',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys9_ticket_move
-- ----------------------------

-- ----------------------------
-- Table structure for t_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket`;
CREATE TABLE `t_ticket` (
  `uuid` varchar(32) NOT NULL,
  `question` varchar(64) DEFAULT NULL COMMENT '问题',
  `content` varchar(1024) DEFAULT NULL COMMENT '问题描述',
  `level` int(1) DEFAULT NULL COMMENT '紧急程度（0 非常紧急 1 一般 2 不着急)',
  `outerUserId` varchar(32) DEFAULT NULL COMMENT '客户Id',
  `attamentName` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `attamentUrl` varchar(32) DEFAULT NULL COMMENT '附件路径',
  `status` int(1) DEFAULT NULL COMMENT '状态（0 等待处理，1正在处理，2等待用户反馈，3问题关闭 4已删除 5 流转中）',
  `questionTypeId` varchar(32) DEFAULT NULL COMMENT '问题分类Id',
  `ownerId` varchar(32) DEFAULT NULL COMMENT '问题持有人Id',
  `createTime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '紧急原因',
  `closeTime` datetime DEFAULT NULL COMMENT '工单关闭时间',
  `email` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单列表';

-- ----------------------------
-- Records of t_ticket
-- ----------------------------
INSERT INTO `t_ticket` VALUES ('', null, '安庆市', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_ticket` VALUES ('1145600bdf45462ca802801e1e6cb718', null, null, '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-17 09:16:33', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('1cfbd24ac5514ce3b6630f4f9aa2531f', '鹅鹅鹅哇', '山东肥城复古色大幅度', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-15 12:47:44', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('242e0a2fca7d498d9dee54f4f596a9e9', '威武气温', '(NULL辅导费)', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-17 09:18:41', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('36917d8f56564233a6ae8ea85c06f5c1', '认为无色', '是的撒', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-17 09:14:05', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('3a714a0c99f64aa7a9cc9922ac6b517c', '撒的丝袜', '但是', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-16 18:01:54', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('5e6a50f6c9754b4a805cc895d73e8295', '阿桑奇挖的我', '是的撒', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '0', '2015-09-15 12:47:45', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('6b5c4c02af3a4db5b7fb36ed02a15953', null, '是的撒', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-17 09:17:36', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('7288de6213614d4ba3734ce9923793d9', null, '未', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-17 09:17:59', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('adfaefawefawefawefwawefawefawefa', '1+1?', '啊', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '9eefc5cf728343d6883eee13167006da', '2015-09-15 10:47:16', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('d53f44894a0644f89c66fb98f3911c1a', '是的撒', '(NULL)是的撒', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-15 12:47:47', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('d58aa67aebe64eceb56d95a2e32b177d', '阿迪速度速度', '未', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-16 18:02:18', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('d7d606f4eebb4f44b4e77cf221c5b431', '啥', '单位', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-17 09:14:54', '紧急', null, null, null);
INSERT INTO `t_ticket` VALUES ('d9d06dfe1b7e4e10950e8d755077a978', 'ad ', '是', '2', '664eb55058f511e5b3218c89a5e173c7', null, null, '1', 'safegsagfaweftiqyo8rho297ryo2qr3', '664eb55058f511e5b3218c89a5e173c7', '2015-09-17 09:18:18', '紧急', null, null, null);

-- ----------------------------
-- Table structure for t_ticket_move
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket_move`;
CREATE TABLE `t_ticket_move` (
  `uuid` varchar(32) NOT NULL,
  `toUserId` varchar(32) DEFAULT NULL COMMENT '流转目的人ID',
  `fromUserId` varchar(32) DEFAULT NULL COMMENT '申请流转人ID',
  `audtId` varchar(32) DEFAULT NULL COMMENT '审核人Id',
  `ticketId` varchar(32) DEFAULT NULL COMMENT '问题Id',
  `createTime` datetime DEFAULT NULL,
  `reason` varchar(512) DEFAULT NULL COMMENT '流转原因',
  `status` int(1) DEFAULT NULL COMMENT '0待审核 1 通过 2驳回 3已删除',
  `refuseReason` varchar(512) DEFAULT NULL COMMENT '驳回原因',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流转情况';

-- ----------------------------
-- Records of t_ticket_move
-- ----------------------------
INSERT INTO `t_ticket_move` VALUES ('22222222222222222222222222222222', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '3', '');
INSERT INTO `t_ticket_move` VALUES ('adasdfsfffffffffffffffffffffffff', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '1', '');
INSERT INTO `t_ticket_move` VALUES ('assddddddddddddddddddddddddddddd', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '3', '');
INSERT INTO `t_ticket_move` VALUES ('asssssssssssssssssssssssssssssss', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '123');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444277a187ea7768678678', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444277a187ea7fe678526d', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '12312312');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444277a187ea7fe67852sd', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '123123123');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444277a187ea7fe6785xsa', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '12312');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444277a187zzzzzzzz526d', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '');
INSERT INTO `t_ticket_move` VALUES ('d1ba362afb444sdassssssssssssssss', '9eefc5cf728343d6883eee13167006da', 'a980879fc8cd4a2ba6317ecf5daf19e0', '9eefc5cf728343d6883eee13167006da', 'adfaefawefawefawefwawefawefawefa', '2015-09-16 14:01:02', '撒嗲嗲哈哈地还打挥洒好滴', '0', '');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `uuid` varchar(32) NOT NULL,
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `roleId` varchar(32) DEFAULT NULL COMMENT '角色Id',
  `departmentId` varchar(32) DEFAULT NULL COMMENT '部门Id',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机',
  `phone` varchar(16) DEFAULT NULL COMMENT '座机',
  `realName` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(1) DEFAULT NULL COMMENT '性别 0 女 1男',
  `status` int(1) DEFAULT NULL COMMENT '状态（0待审核 1正常 2 审核未通过 3删除 4 冻结）',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注（申请备注等）',
  `createTime` datetime DEFAULT NULL,
  `workerNo` varchar(32) DEFAULT NULL COMMENT '工单号',
  `systemIndex` int(11) DEFAULT NULL COMMENT '系统Id',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1c2f418778f6447ea6363c631ea11520', 'eqwe', 'qwe', 'asoitufaoihfaeaefhaiwfheaiewufha', '43e5f8f1714e4be88c45e2d7d39c20b8', null, null, null, 'weqw', null, '1', null, '2015-09-21 09:40:58', null, '0');
INSERT INTO `t_user` VALUES ('2013cdd8be7b4f3e9e00b5fd6490bcbf', 'qwe', 'qweqw', 'asoitufaoihfaeaefhaiwfheaiewufha', '5b763d3ff8b54d36bd9a3f0922eda701', null, null, null, 'qweqwe', null, '1', null, '2015-09-21 09:42:08', null, '0');
INSERT INTO `t_user` VALUES ('2414ac7436e54d69846bbde9cce25196', '12312', '31', 'asoitufaoihfaeaefhaiwfheaiewufha', '5a0cdf8a913f46479c336dc6379b3595', null, null, null, '123', null, '1', null, '2015-09-19 16:45:02', null, '0');
INSERT INTO `t_user` VALUES ('2d2b7e1d31be4edc9d00f04f8d2b3f5e', '3333333333333333', '3333333333333', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, '333333333333333', null, '1', null, '2015-09-21 16:34:30', null, '0');
INSERT INTO `t_user` VALUES ('34644c9fc18b4953a4b03b8daaa494f6', 'asdfasd', 'adsfasdf', 'asoitufaoihfaeaefhaiwfheaiewufha', '6511e5e4b19a4322a22fb1be0ca554f4', null, null, null, 'fasdfasf', null, '1', null, '2015-09-19 12:45:37', null, '0');
INSERT INTO `t_user` VALUES ('3af22e60e7e04815af7808e016b6795f', 'sadfasd', 'fasdf', 'asoitufaoihfaeaefhaiwfheaiewufha', '874f587f48a149bc938e6084bc55fdda', null, null, null, 'sdfsadf', null, '1', null, '2015-09-21 09:43:32', null, '0');
INSERT INTO `t_user` VALUES ('3feb59302bf84e62ad0becfff1a18ad7', '1231', '23', 'asoitufaoihfaeaefhaiwfheaiewufha', '5199399b92f341ec8b61756a621d4272', null, null, null, '123', null, '1', null, '2015-09-19 11:46:06', null, '0');
INSERT INTO `t_user` VALUES ('41b2b4bc66e44f6da41c0f4fd6f26f0f', '1', '1', 'asoitufaoihfaeaefhaiwfheaiewufha', '6c820ba3f90f4192893b06fdb85b8987', null, null, null, '11', null, '1', null, '2015-09-21 09:21:07', null, '0');
INSERT INTO `t_user` VALUES ('4d1133b61b9148b68af764fef32bc520', '1231231', '12312', 'asoitufaoihfaeaefhaiwfheaiewufha', 'c23fec40cd494d71b97ad770e7220424', null, null, null, '3123', null, '1', null, '2015-09-19 16:46:50', null, '0');
INSERT INTO `t_user` VALUES ('5247bd07b0fd42eda5272e47cb65c7b0', 'ji', '000000q', null, null, 'sj@123.co', null, '15236535695', 's', '0', '1', null, '2015-09-21 16:04:02', null, '0');
INSERT INTO `t_user` VALUES ('664eb55058f511e5b3218c89a5e173c7', '111', '111', '499744105b4a11e5b3218c89a5e173c7', '', '123@163.com', '125522188455', '1235448845', '超级管理员', '1', '1', '二二二二二二二二二二二二让我', '2015-09-15 14:10:16', null, '0');
INSERT INTO `t_user` VALUES ('66f888e9537e4564a456d1e976b72c09', 'asd', 'asd', 'asoitufaoihfaeaefhaiwfheaiewufha', '6447dcb19b654833802ed90fe4cdf7b7', null, null, null, 'dasd', null, '1', null, '2015-09-21 09:47:12', null, '0');
INSERT INTO `t_user` VALUES ('6be61134ba4741edad709fb5b862ca66', 'asdasd', 'asdsa', 'asoitufaoihfaeaefhaiwfheaiewufha', '7d68839a3ffd4ead89831766a5b703a1', null, null, null, 'asdas', null, '1', null, '2015-09-19 11:45:00', null, '0');
INSERT INTO `t_user` VALUES ('6ca7b29ea0bb429189048a565435d645', '111111111111', '111111111111111', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, '1111111111111', null, '3', null, '2015-09-21 16:33:21', null, '0');
INSERT INTO `t_user` VALUES ('74e4e9efcf3f4a6596a55c5523a22b0e', '222', '222', 'f39sfvdv3dea343f43t5df213rdsd214', '', '123@163.com', '125522188455', '1235448845', '系统管理员', '1', '1', '地方', '2015-09-15 16:58:52', null, '0');
INSERT INTO `t_user` VALUES ('75a4fb13338c48f2aaa050c418896977', 'aaa', 'aaa', null, null, 'aaa@1', null, '2332132', null, '1', '1', 'sd', '2015-09-21 13:12:44', null, '0');
INSERT INTO `t_user` VALUES ('788614759fe44c8f8fd74c6bb1cb39f5', '123', '1231', 'asoitufaoihfaeaefhaiwfheaiewufha', 'b8b76f507ba847c3adceb724d7662ff7', null, null, null, '3123', null, '1', null, '2015-09-19 13:27:49', null, '0');
INSERT INTO `t_user` VALUES ('78a3149f9058490fbfc54e228a759542', 'fffffffffffff', 'ffffffffff', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'fffffffff', null, '1', null, '2015-09-21 16:37:18', null, '0');
INSERT INTO `t_user` VALUES ('806654fe3cc7422c8cc19834a2508587', 'sss', 'sss', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'sss', null, '1', null, '2015-09-21 16:29:03', null, '0');
INSERT INTO `t_user` VALUES ('9eefc5cf728343d6883eee13167006da', '333', '333', 'asoitufaoihfaeaefhaiwfheaiewufha', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '123@163.com', '125522188455', '1214561148', '部门管理员', '1', '1', null, '2015-09-17 17:47:37', null, '0');
INSERT INTO `t_user` VALUES ('a2e8d49f57e94383ac1a37da37d672eb', 'xxx', 'xxx', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'xxx', null, '1', null, '2015-09-21 16:31:24', null, '0');
INSERT INTO `t_user` VALUES ('a980879fc8cd4a2ba6317ecf5daf19e0', '444', '444', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', '123@163.com', '125522188455', '1532552522', '职员', '0', '1', '收到的第未', '2015-09-17 17:47:38', null, '9');
INSERT INTO `t_user` VALUES ('c71574644b2143938df73cda6d209eff', '12312', '3123', 'asoitufaoihfaeaefhaiwfheaiewufha', 'c114199137e64473b3036ed3cc1a2cd3', null, null, null, '123', null, '1', null, '2015-09-19 13:28:10', null, '0');
INSERT INTO `t_user` VALUES ('c7e2860ea3d84679b2865dae9c175bb3', 'bbb', '123', null, null, '', null, '', null, null, '1', '', '2015-09-21 13:29:44', null, '0');
INSERT INTO `t_user` VALUES ('c875cdbcc72b45b08314d46ac0140627', 'qwe', 'qweqw', 'asoitufaoihfaeaefhaiwfheaiewufha', 'd0ec07cda0ec4f4b8b104ff626acb031', null, null, null, 'qweqwe', null, '1', null, '2015-09-21 09:42:24', null, '0');
INSERT INTO `t_user` VALUES ('d2a378c6519f41a48f2f8944c3cc1216', 'zzzzzzzzzzzz', 'zzzzzzzzz', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'zzzzzz', null, '1', null, '2015-09-21 16:41:12', null, '0');
INSERT INTO `t_user` VALUES ('d69e573084bb4e8b90082e460c3d1127', 'cccccccccccc', 'ccccccccccccc', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'ccccccccc', null, '1', null, '2015-09-21 16:38:44', null, '0');
INSERT INTO `t_user` VALUES ('db0be67c99354ab2bc7616ed06a38772', 'das', 'dasd', 'asoitufaoihfaeaefhaiwfheaiewufha', '8b07821acb7b42caa296a6cbf8c24449', null, null, null, 'asdas', null, '1', null, '2015-09-21 09:39:04', null, '0');
INSERT INTO `t_user` VALUES ('db8919eab43246e2b15ad0ce2cb05ac9', 'eqwe', 'qwe', 'asoitufaoihfaeaefhaiwfheaiewufha', '04c1b3cf19184e10a02e4aaa78e0b6f2', null, null, null, 'weqw', null, '1', null, '2015-09-21 09:41:25', null, '0');
INSERT INTO `t_user` VALUES ('e36898b5638540d7859e320ed17436ff', 'qwe', 'qweqw', 'asoitufaoihfaeaefhaiwfheaiewufha', 'ce64a6a33fe54ab8a6c29be16129da85', null, null, null, 'qweqwe', null, '1', null, '2015-09-21 09:43:09', null, '0');
INSERT INTO `t_user` VALUES ('e6d9bc257365461c879847bc7a6b8535', 'qqq', 'qqq', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'qqq', null, '1', null, '2015-09-21 16:31:51', null, '0');
INSERT INTO `t_user` VALUES ('e73315ae74da4680bc263b551a5242ff', 'xxxxxxxxxxxxxxx', 'xxxxxxxxxxxxx', 'dfm932bn182jde92njcd29uend29ej28', '43irlp3p4l6k7m35m23b6b4n6md8b2k4', null, null, null, 'xxxxxxxxxxxxxxx', null, '1', null, '2015-09-21 16:33:56', null, '0');
INSERT INTO `t_user` VALUES ('eff151db2d9747bd993f1aaca0a5c91f', '1231231', '12312', 'asoitufaoihfaeaefhaiwfheaiewufha', '8efaaea07342425ea7897e35821ccd50', null, null, null, '3123', null, '1', null, '2015-09-19 16:47:06', null, '0');
INSERT INTO `t_user` VALUES ('f94fe832564a40c398fb81b625bb94a2', '12312', '12312', 'asoitufaoihfaeaefhaiwfheaiewufha', '8078940ffbe942888dac44286dc6c38c', null, null, null, '123', null, '1', null, '2015-09-21 09:33:14', null, '0');
INSERT INTO `t_user` VALUES ('ff39798606ce4084938298db864a4b1d', 'aaaaa', '', null, null, 'aaa@1', null, '2332132', null, '1', '1', 'sd', '2015-09-21 13:13:14', null, '0');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `uuid` varchar(32) NOT NULL,
  `roleName` varchar(32) DEFAULT NULL COMMENT '角色名字',
  `createTime` datetime DEFAULT NULL,
  `level` int(10) DEFAULT NULL COMMENT '级别（0超级管理员，10客户管理员，20开头部门主管，100开头职员）',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `code` varchar(16) DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('499744105b4a11e5b3218c89a5e173c7', '超级管理员', '2015-09-15 09:37:29', '0', null, 'superAdmin');
INSERT INTO `t_user_role` VALUES ('asoitufaoihfaeaefhaiwfheaiewufha', '部门管理员', '2015-09-14 14:58:35', '20', null, 'departmentAdmin');
INSERT INTO `t_user_role` VALUES ('dfm932bn182jde92njcd29uend29ej28', '普通职员', '2015-09-23 09:35:27', '100', null, 'staff');
INSERT INTO `t_user_role` VALUES ('f39sfvdv3dea343f43t5df213rdsd214', '系统管理员', '2015-09-15 09:19:17', '10', null, 'systemAdmin');
