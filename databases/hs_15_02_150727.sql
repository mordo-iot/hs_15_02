/*
 Navicat MySQL Data Transfer

 Source Server         : gongyanyuan
 Source Server Version : 50514
 Source Host           : 10.200.200.60
 Source Database       : hs_15_02

 Target Server Version : 50514
 File Encoding         : utf-8

 Date: 07/27/2015 10:37:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mordo_alarm_current`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_alarm_current`;
CREATE TABLE `mordo_alarm_current` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `CODE` varchar(10) NOT NULL DEFAULT '' COMMENT '报警代码',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `CONTENT` varchar(512) NOT NULL DEFAULT '' COMMENT '报警内容',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  `HANDLESTATE` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '处理状态 0-未处理 1-已处理 2-手动取消',
  `HANDLEDESC` varchar(512) NOT NULL DEFAULT '' COMMENT '处理描述',
  `HANDLEDATE` char(14) NOT NULL DEFAULT '' COMMENT '处理日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='当前报警信息表';

-- ----------------------------
--  Table structure for `mordo_alarm_history`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_alarm_history`;
CREATE TABLE `mordo_alarm_history` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `CODE` varchar(10) NOT NULL DEFAULT '' COMMENT '报警代码',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `CONTENT` varchar(512) NOT NULL DEFAULT '' COMMENT '报警内容',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  `HANDLESTATE` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '处理状态 0-未处理 1-已处理 2-手动取消',
  `HANDLEDESC` varchar(512) NOT NULL DEFAULT '' COMMENT '处理描述',
  `HANDLEDATE` char(14) NOT NULL DEFAULT '' COMMENT '处理日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='历史报警信息表';

-- ----------------------------
--  Table structure for `mordo_dev_info`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_dev_info`;
CREATE TABLE `mordo_dev_info` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `NAME` varchar(50) NOT NULL DEFAULT '' COMMENT '设备名称',
  `TYPE` varchar(20) NOT NULL DEFAULT '' COMMENT '设备种类',
  `CODE` varchar(50) NOT NULL DEFAULT '' COMMENT '设备条码',
  `ALARMCONTENT` varchar(20) NOT NULL DEFAULT '' COMMENT '报警内容',
  `ALARMDEVID` varchar(500) NOT NULL DEFAULT '' COMMENT '报警设备编号，以,分割',
  `LIGHTNO` int(10) NOT NULL DEFAULT '0' COMMENT '电子看板报警编号',
  `LIGHTDEVID` varchar(500) NOT NULL DEFAULT '' COMMENT '报警电子看板编号',
  `ALARMPHONE` varchar(500) NOT NULL DEFAULT '' COMMENT '报警短信号码，以,分割',
  `EMITID` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '信号发射器编号',
  `PARENTID` int(10) NOT NULL DEFAULT '0' COMMENT '父节点',
  `ATTRIBUTE` varchar(500) NOT NULL DEFAULT '' COMMENT '设备属性，json格式',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  `UPDATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '更新日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
--  Table structure for `mordo_map_dev_position`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_map_dev_position`;
CREATE TABLE `mordo_map_dev_position` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `POSITIONID` int(10) NOT NULL DEFAULT '0' COMMENT '位置编号',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='设备安装位置映射表';

-- ----------------------------
--  Table structure for `mordo_position_info`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_position_info`;
CREATE TABLE `mordo_position_info` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `NAME` varchar(200) NOT NULL DEFAULT '' COMMENT '位置名称',
  `PHOTO` varchar(200) NOT NULL DEFAULT '' COMMENT '照片相对路径',
  `PARENTID` int(10) NOT NULL DEFAULT '0' COMMENT '父节点',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  `UPDATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '更新日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='位置信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_bed`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_bed`;
CREATE TABLE `mordo_state_current_bed` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `HAVINGBODY` char(1) NOT NULL DEFAULT 'Y' COMMENT '床垫状态 Y-有人 N-无人',
  `LEVEL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报警等级',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='床垫当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_door`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_door`;
CREATE TABLE `mordo_state_current_door` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `OPENCLOSE` char(1) NOT NULL DEFAULT 'Y' COMMENT '门状态 Y-关 N-开',
  `LEVEL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报警等级',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='门磁当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_gateway`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_gateway`;
CREATE TABLE `mordo_state_current_gateway` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `LEVEL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报警等级',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='室内网关当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_keyalarm`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_keyalarm`;
CREATE TABLE `mordo_state_current_keyalarm` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='一键报警当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_location`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_location`;
CREATE TABLE `mordo_state_current_location` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `CURRPOSITIONID` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前所在位置编号',
  `CURRLOG` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '当前所在经度',
  `CURRLAT` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '当前所在纬度',
  `LEAVED` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-越界',
  `LEAVEDUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '位置记录更新时间',
  `BODYSTATE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-摔倒',
  `MANUALALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-手动报警',
  `BODYUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '身体状态更新时间',
  `MOVING` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-人卡分离',
  `MOVINGUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '人卡分离记录更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定位器当前状态表';

-- ----------------------------
--  Table structure for `mordo_state_current_urine`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_urine`;
CREATE TABLE `mordo_state_current_urine` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='尿湿当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_current_wandai`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_current_wandai`;
CREATE TABLE `mordo_state_current_wandai` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  `NORMAL` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-故障',
  `ONLINE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-在线 N-离线',
  `POWER` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常 N-低电压',
  `DEVUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '设备状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='腕带当前信息表';

-- ----------------------------
--  Table structure for `mordo_state_history_bed`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_bed`;
CREATE TABLE `mordo_state_history_bed` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `HAVINGBODY` char(1) NOT NULL DEFAULT 'Y' COMMENT '床垫状态 Y-有人 N-无人',
  `LEVEL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报警等级',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='床垫历史信息表';

-- ----------------------------
--  Table structure for `mordo_state_history_door`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_door`;
CREATE TABLE `mordo_state_history_door` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `OPENCLOSE` char(1) NOT NULL DEFAULT 'Y' COMMENT '门状态 Y-关 N-开',
  `LEVEL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '报警等级',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='门磁历史信息表';

-- ----------------------------
--  Table structure for `mordo_state_history_keyalarm`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_keyalarm`;
CREATE TABLE `mordo_state_history_keyalarm` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='一键报警历史信息表';

-- ----------------------------
--  Table structure for `mordo_state_history_location_body`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_location_body`;
CREATE TABLE `mordo_state_history_location_body` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `BODYSTATE` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-摔倒',
  `BODYUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '身体状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定位器身体历史状态表';

-- ----------------------------
--  Table structure for `mordo_state_history_location_manual`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_location_manual`;
CREATE TABLE `mordo_state_history_location_manual` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `MANUALALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-手动报警',
  `BODYUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '身体状态更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定位器手动报警历史状态表';

-- ----------------------------
--  Table structure for `mordo_state_history_location_move`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_location_move`;
CREATE TABLE `mordo_state_history_location_move` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `MOVING` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-人卡分离',
  `MOVINGUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '人卡分离记录更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定位器人卡分离历史状态表';

-- ----------------------------
--  Table structure for `mordo_state_history_location_pos`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_location_pos`;
CREATE TABLE `mordo_state_history_location_pos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `CURRPOSITIONID` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前所在位置编号',
  `CURRLOG` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '当前所在经度',
  `CURRLAT` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '当前所在纬度',
  `LEAVED` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y-正常，N-越界',
  `LEAVEDUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '位置记录更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定位器位置历史状态表';

-- ----------------------------
--  Table structure for `mordo_state_history_urine`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_urine`;
CREATE TABLE `mordo_state_history_urine` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='尿湿历史信息表';

-- ----------------------------
--  Table structure for `mordo_state_history_wandai`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_state_history_wandai`;
CREATE TABLE `mordo_state_history_wandai` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `DEVID` int(10) NOT NULL DEFAULT '0' COMMENT '设备编号',
  `ALARM` char(1) NOT NULL DEFAULT 'Y' COMMENT '报警状态 Y-报警 N-无报警',
  `ALARMUPDATETIME` char(14) NOT NULL DEFAULT '' COMMENT '报警更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='腕带历史信息表';

-- ----------------------------
--  Table structure for `mordo_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_sys_user`;
CREATE TABLE `mordo_sys_user` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(32) NOT NULL DEFAULT '' COMMENT '密码md5',
  `PERMISSIONS` tinyint(3) NOT NULL DEFAULT '2' COMMENT '权限：1-管理员，2-普通用户',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  `LSTLOGINDATE` char(14) NOT NULL DEFAULT '' COMMENT '上次登陆时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
--  Table structure for `mordo_user_log`
-- ----------------------------
DROP TABLE IF EXISTS `mordo_user_log`;
CREATE TABLE `mordo_user_log` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `USERID` int(10) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `LOG` varchar(1024) NOT NULL DEFAULT '' COMMENT '日志描述',
  `LEVEL` tinyint(3) NOT NULL DEFAULT '0' COMMENT '等级 0-通知 1-警告 2-错误',
  `CREATEDATE` char(14) NOT NULL DEFAULT '' COMMENT '创建日期',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户日志表';

SET FOREIGN_KEY_CHECKS = 1;
