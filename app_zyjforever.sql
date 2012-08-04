-- phpMyAdmin SQL Dump
-- version 3.3.8.1
-- http://www.phpmyadmin.net
--
-- 主机: w.rdc.sae.sina.com.cn:3307
-- 生成日期: 2012 年 08 月 04 日 09:52
-- 服务器版本: 5.1.47
-- PHP 版本: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `app_zyjforever`
--

-- --------------------------------------------------------

--
-- 表的结构 `activity`
--

CREATE TABLE IF NOT EXISTS `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `context` text NOT NULL,
  `userId` bigint(20) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `picName` varchar(32) DEFAULT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  `peopleLimit` smallint(4) NOT NULL,
  `city` varchar(20) NOT NULL,
  `time` datetime NOT NULL,
  `type` smallint(4) unsigned zerofill NOT NULL DEFAULT '0000',
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- 转存表中的数据 `activity`
--

INSERT INTO `activity` (`id`, `name`, `context`, `userId`, `startTime`, `endTime`, `location`, `latitude`, `longitude`, `picName`, `picUrl`, `peopleLimit`, `city`, `time`, `type`, `attribute`) VALUES
(1, '三人足球赛', '踢足球', 1, '2012-05-20 01:59:00', '2012-05-20 01:59:00', '广工体育场', '23.047405', '113.406445', '3a6f4815e5d1261a0a6c4f097ba0ef7d', 'http://zyjforever-activitypic.stor.sinaapp.com/3a6f4815e5d1261a0a6c4f097ba0ef7d', 3, '广州市', '2012-05-20 15:34:34', 0000, NULL),
(2, '五人足球赛', '无聊啊', 1, '2012-05-20 02:44:00', '2012-05-20 02:44:00', '广工', '23.047105', '113.404445', '58de0eb31977863ee13825387faf1223', 'http://zyjforever-activitypic.stor.sinaapp.com/58de0eb31977863ee13825387faf1223', 2, '广州市', '2012-05-20 15:34:34', 0000, NULL),
(3, '五人足球赛', '无聊啊', 1, '2012-05-20 02:44:00', '2012-05-20 02:44:00', '广工', '23.054979', '113.413208', '58de0eb31977863ee13825387faf1223', 'http://zyjforever-activitypic.stor.sinaapp.com/58de0eb31977863ee13825387faf1223', 2, '广州市', '2012-05-20 15:34:34', 0000, NULL),
(4, '毕业答辩', '毕业答辩了啊啊啊啊啊啊啊', 1, '2012-05-20 10:13:00', '2012-05-20 10:13:00', '广工工学一号馆', '23.041436', '113.404351', '7ed5c9cb0ef0fa37a2a7cc012d82d03b', 'http://zyjforever-activitypic.stor.sinaapp.com/7ed5c9cb0ef0fa37a2a7cc012d82d03b', 2, '广州市', '2012-05-20 10:14:00', 0000, NULL),
(5, '没事做', '聊聊天吧', 1, '2012-05-20 11:05:00', '2012-05-20 11:05:00', '广工正门', '23.044654', '113.403668', '40f2bac47212504898993ca462ffd1a2', 'http://zyjforever-activitypic.stor.sinaapp.com/40f2bac47212504898993ca462ffd1a2', 3, '广州市', '2012-05-20 11:06:00', 0000, NULL),
(6, '生气了', '嗯嗯懂弄弄记录他', 1, '2012-05-20 23:13:00', '2012-05-20 23:13:00', '广工西区', '23.043623', '113.400569', '06c89f7a743fbb1554b8a0020fc56008', 'http://zyjforever-activitypic.stor.sinaapp.com/06c89f7a743fbb1554b8a0020fc56008', 2, '广州市', '2012-05-20 23:14:00', 0000, NULL),
(7, '睡觉', '睡觉睡觉睡觉', 10, '2012-05-21 09:23:00', '2012-05-21 09:23:00', '广工西区', '23.050049', '113.397802', '8ab1a68acaedfd6479a46da82bcf1750', 'http://zyjforever-activitypic.stor.sinaapp.com/8ab1a68acaedfd6479a46da82bcf1750', 1, '广州市', '2012-05-21 09:24:00', 0000, NULL),
(18, '三人足球赛', '三人足球赛，每队至少三人参加，采用单循环机制进行，前三名将获得丰厚奖励，喜欢足球的你怎么可以错过？\n\n详情留意校内宣传点', 1, '2012-05-30 09:00:00', '2012-06-20 06:00:00', '大学城广工无跑道足球场', '23.045701', '113.398934', '3a6f4815e5d1261a0a6c4f097ba0ef7d', 'http://zyjforever-activitypic.stor.sinaapp.com/3a6f4815e5d1261a0a6c4f097ba0ef7d', 3, '广州市', '2012-05-29 23:12:00', 0000, NULL),
(16, '看电影', 'ume 复仇者联盟', 14, '2012-05-29 18:51:00', '2012-05-29 21:51:00', 'ume', '30.286799', '120.102431', 'c6b20866c9b845dc93e50393929c094d', 'http://zyjforever-activitypic.stor.sinaapp.com/c6b20866c9b845dc93e50393929c094d', 2, '杭州市', '2012-05-28 23:53:00', 0000, NULL),
(10, '悲剧', '发生神马事', 1, '2012-05-24 00:00:00', '2012-05-24 00:00:00', '广工', '23.047913', '113.394065', '23777d1af461f6da52273037c959ade5', 'http://zyjforever-activitypic.stor.sinaapp.com/23777d1af461f6da52273037c959ade5', 1, '广州市', '2012-05-24 00:01:00', 0000, NULL),
(14, '随便聊聊', '聊聊天吧', 1, '2012-05-26 22:38:00', '2012-05-26 22:38:00', '随便啊啊', '23.055062', '113.39897', 'f7ccbab112d637935e1f0ab09d4036d3', 'http://zyjforever-activitypic.stor.sinaapp.com/f7ccbab112d637935e1f0ab09d4036d3', 2, '广州市', '2012-05-28 22:39:00', 0000, NULL),
(17, '随便聊聊天', '比较无聊，我们找个地方一起聊聊天吧？线上也行，线下也可以。', 1, '2012-05-30 00:00:00', '2012-05-30 21:00:00', '线上线下都可以', '23.05197', '113.398458', '5f303f4273713772c0af89d5da8c146d', 'http://zyjforever-activitypic.stor.sinaapp.com/5f303f4273713772c0af89d5da8c146d', 2, '广州市', '2012-05-30 01:23:00', 0000, NULL),
(19, '三人足球赛', '三人足球赛，每队至少三人参加，采用单循环机制进行，前三名将获得丰厚奖励，喜欢足球的你怎么可以错过？\n\n详情留意校内宣传点', 1, '2012-05-30 09:00:00', '2012-06-20 06:00:00', '大学城广工无跑道足球场', '23.045701', '113.398934', '3a6f4815e5d1261a0a6c4f097ba0ef7d', 'http://zyjforever-activitypic.stor.sinaapp.com/3a6f4815e5d1261a0a6c4f097ba0ef7d', 3, '广州市', '2012-05-30 23:12:00', 0000, NULL),
(20, '打羽毛球', '有没有羽毛球爱好者？一起来打打球吧。', 1, '2012-05-31 05:00:00', '2012-05-31 06:30:00', '大学城广工体育馆羽毛球场', '23.046317', '113.396158', '03f18dbc949a6e8772047d05dfeb4f25', 'http://zyjforever-activitypic.stor.sinaapp.com/03f18dbc949a6e8772047d05dfeb4f25', 1, '广州市', '2012-05-30 01:00:00', 0000, NULL),
(21, '小聚会', '没事打打牌什么的', 1, '2012-05-31 10:00:00', '2012-05-31 12:00:00', '大学城中心湖', '23.057423', '113.399689', '8024189b2ba03224b73ca480cc34f119', 'http://zyjforever-activitypic.stor.sinaapp.com/8024189b2ba03224b73ca480cc34f119', 0, '广州市', '2012-05-30 01:08:00', 0000, NULL),
(22, '毕业答辩', '毕业答辩，最重要的一次。', 1, '2012-05-30 09:00:00', '2012-05-30 18:30:00', '大学城广工工学一号馆', '23.041569', '113.405806', '7ed5c9cb0ef0fa37a2a7cc012d82d03b', 'http://zyjforever-activitypic.stor.sinaapp.com/7ed5c9cb0ef0fa37a2a7cc012d82d03b', 0, '广州市', '2012-05-30 09:40:00', 0000, NULL),
(23, '毕业聚餐', '毕业的同学，我们一起去吃顿饭吧', 1, '2012-05-30 11:00:00', '2012-05-30 13:00:00', '大学城南亭', '23.043914', '113.394236', '2c0991549078a0d4fa9e442c33c156e0', 'http://zyjforever-activitypic.stor.sinaapp.com/2c0991549078a0d4fa9e442c33c156e0', 1, '广州市', '2012-05-30 10:01:00', 0000, NULL),
(24, '篮球赛', '三人篮球赛，每队至少三名同学参加，小组淘汰赛，喜欢篮球的你，怎么可以错过？', 1, '2012-05-31 12:00:00', '2012-06-20 12:00:00', '大学城广工篮球场', '23.045627', '113.401503', '4678328d6f29386a9957c226c31d30e7', 'http://zyjforever-activitypic.stor.sinaapp.com/4678328d6f29386a9957c226c31d30e7', 3, '广州市', '2012-05-30 12:01:00', 0000, NULL),
(25, '多P派对', '几个男，几个女，然后做点令人羞羞的事情…不交换联系方式，咱都只有一面感情，怎么样', 15, '2012-06-02 11:52:00', '2012-06-03 11:52:00', '揭东', '23.575294', '116.414096', 'c1e12f2a0f6b1b3bd2b7a3a970f2a898', 'http://zyjforever-activitypic.stor.sinaapp.com/c1e12f2a0f6b1b3bd2b7a3a970f2a898', 1, '广州市', '2012-06-01 11:53:00', 0000, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `activitycomment`
--

CREATE TABLE IF NOT EXISTS `activitycomment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `time` datetime NOT NULL,
  `context` text NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=41 ;

--
-- 转存表中的数据 `activitycomment`
--

INSERT INTO `activitycomment` (`id`, `activityId`, `userId`, `time`, `context`, `attribute`) VALUES
(35, 20, 10, '2012-05-30 09:27:00', '不太喜欢打的路过', ''),
(34, 17, 10, '2012-05-30 09:26:00', '好呀，聊点什么', ''),
(33, 19, 10, '2012-05-30 09:26:00', '每场球大概多少人参加呢？', ''),
(32, 17, 1, '2012-05-29 22:13:00', '本机', ''),
(31, 17, 1, '2012-05-29 20:56:00', '可以聊天的', ''),
(30, 14, 12, '2012-05-28 21:35:00', '我也说几句', ''),
(29, 10, 1, '2012-05-28 20:49:00', '恩，说多句', ''),
(25, 14, 1, '2012-05-27 14:29:00', '试试功能', ''),
(24, 14, 10, '2012-05-26 00:00:00', '悲剧的日子', ''),
(26, 14, 1, '2012-05-27 14:43:00', '不就试试嘛', ''),
(23, 14, 1, '2012-05-26 00:00:00', '"不就是评论一下咩"', ''),
(27, 14, 1, '2012-05-28 16:24:00', '快点来参加啊', ''),
(28, 10, 1, '2012-05-28 20:46:00', '没人说话，我来说句', ''),
(36, 19, 12, '2012-05-30 09:33:00', '我只有一个人，求组队', ''),
(38, 19, 1, '2012-05-30 09:41:00', '每场球20分钟。', ''),
(39, 19, 1, '2012-05-30 09:58:00', '大家快点报名吧', ''),
(40, 19, 1, '2012-05-30 11:48:00', '来更多人参加吧', '');

-- --------------------------------------------------------

--
-- 表的结构 `activityjoin`
--

CREATE TABLE IF NOT EXISTS `activityjoin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activityId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=66 ;

--
-- 转存表中的数据 `activityjoin`
--

INSERT INTO `activityjoin` (`id`, `activityId`, `userId`, `attribute`) VALUES
(65, 19, 1, NULL),
(63, 19, 12, NULL),
(60, 17, 10, NULL),
(59, 19, 10, NULL),
(38, 7, 1, NULL),
(58, 19, 15, NULL),
(51, 14, 1, NULL),
(57, 18, 1, NULL),
(56, 17, 1, NULL),
(55, 17, 15, NULL),
(54, 14, 12, NULL),
(53, 10, 1, NULL),
(52, 14, 10, NULL),
(44, 14, 11, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `contacts`
--

CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `number` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- 转存表中的数据 `contacts`
--

INSERT INTO `contacts` (`id`, `name`, `number`) VALUES
(6, '小治', '138000000005'),
(2, '小莉', '138000000000'),
(3, '小王', '138000000001'),
(4, '小李', '138000000002'),
(5, '小治', '138000000005');

-- --------------------------------------------------------

--
-- 表的结构 `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `senderId` bigint(20) NOT NULL,
  `context` text NOT NULL,
  `time` datetime NOT NULL,
  `type` smallint(6) NOT NULL,
  `isRead` smallint(6) NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  `isDelete` smallint(6) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `message`
--


-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick` varchar(20) NOT NULL,
  `birthday` date DEFAULT NULL,
  `sex` smallint(4) NOT NULL DEFAULT '-1',
  `password` varchar(32) NOT NULL,
  `provice` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  `picName` varchar(32) DEFAULT NULL,
  `type` smallint(4) DEFAULT NULL,
  `blacklist` smallint(4) NOT NULL DEFAULT '0',
  `attributes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `nick`, `birthday`, `sex`, `password`, `provice`, `city`, `signature`, `email`, `picUrl`, `picName`, `type`, `blacklist`, `attributes`) VALUES
(1, '小枫', '1989-05-19', 1, '0ed5493495036d7a2342c0ba6e96cfc0', NULL, '韶关市', '我喜欢计算机，程序员一枚', 'zyj520@yeah.net', 'http://zyjforever-userpic.stor.sinaapp.com/9b0c1fdbb3a1b4843035a8e95040de87', '9b0c1fdbb3a1b4843035a8e95040de87', NULL, 0, NULL),
(10, '水哥一号', '1990-02-21', 1, '0ed5493495036d7a2342c0ba6e96cfc0', NULL, '香港', '没有呢', 'zyj@yeah.net', 'http://zyjforever-userpic.stor.sinaapp.com/5ccd4774f6387706c61585148f73a967', '5ccd4774f6387706c61585148f73a967', NULL, 0, NULL),
(11, 'hyben', NULL, -1, 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 'hyben123@gmail.com', NULL, NULL, NULL, 0, NULL),
(12, '小明', '1991-05-30', 1, '0ed5493495036d7a2342c0ba6e96cfc0', NULL, '广州市', '一个普通的人', '329480985@qq.com', 'http://zyjforever-userpic.stor.sinaapp.com/4cf350692a4a3bb54d13daacfe8c683b', '4cf350692a4a3bb54d13daacfe8c683b', NULL, 0, NULL),
(13, 'feifei', NULL, -1, '0b4e7a0e5fe84ad35fb5f95b9ceeac79', NULL, NULL, NULL, 'jessfay@163.com', NULL, NULL, NULL, 0, NULL),
(14, 'jessfay', NULL, -1, '3dbe00a167653a1aaee01d93e77e730e', NULL, NULL, NULL, 'feifei527@163.com', NULL, NULL, NULL, 0, NULL),
(15, 'airsen', NULL, -1, 'bd9c0dd8620f6a49ebd7f2176552d141', NULL, NULL, NULL, 'jason61719@gmail.com', NULL, NULL, NULL, 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `userattention`
--

CREATE TABLE IF NOT EXISTS `userattention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `attentionId` bigint(20) NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `userattention`
--

INSERT INTO `userattention` (`id`, `userId`, `attentionId`, `attribute`) VALUES
(4, 1, 10, NULL),
(2, 1, 11, NULL),
(5, 12, 10, NULL),
(6, 12, 1, NULL),
(7, 1, 12, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `userblacklist`
--

CREATE TABLE IF NOT EXISTS `userblacklist` (
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `blackId` bigint(20) NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `userblacklist`
--


-- --------------------------------------------------------

--
-- 表的结构 `__meta_code`
--

CREATE TABLE IF NOT EXISTS `__meta_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table` varchar(32) NOT NULL,
  `action` varchar(32) NOT NULL,
  `code` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `table` (`table`,`action`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=69 ;

--
-- 转存表中的数据 `__meta_code`
--

INSERT INTO `__meta_code` (`id`, `table`, `action`, `code`) VALUES
(27, 'user', 'updatepassword', '$password=v(''password'');\nif(!preg_match(''^[a-z0-9]{32}^'', $password)){\n  return $this->send_error(''10050'',''INVALID PASSWORD'');\n}\n\n$newpassword=v(''newpassword'');\nif(!preg_match(''^[a-z0-9]{32}^'', $newpassword)){\n  return $this->send_error(''10051'',''INVALID New PASSWORD'');\n}\n\n$email=v(''email'');\nif(empty($email)){\n  return $this->send_error(''10052'',''EMAIL CAN NOT BE NULL'');\n}\n\n$sql="update user set password={$newpassword} where password={$password} and email={$email}";\n\nrun_sql($sql);\n\nif( mysql_errno() != 0 ) $this->send_error( LR_API_DB_ERROR , ''DATABASE ERROR '' . mysql_error() );\nelse $this->send_result("");'),
(65, 'user', 'queryById', '$userId=v(''id'');\n\nif(empty($userId)){\n  return $this->send_error(''10030'',''ID CAN NOT BE NULL'');\n}\n\n$result=mysql_query("select nick,sex,provice,city,signature,picUrl,picName,birthday,type from user where id={$userId}");\n$data=mysql_fetch_object($result);\n\n\nif(empty($data)){\n  return $this->send_error(''10031'',''USER DOES NOT EXIST'');\n}\nelse{\n return $this->send_result($data);\n}'),
(18, 'user', 'querybyemailandpassword', '$email=v(''email'');\n$password=v(''password'');\n\nif(empty($email)){\n  return $this->send_error(''10040'',''EMAIL CAN NOT BE NULL'');\n}\n\nif(empty($password)){\n   return $this->send_error(''10041'',''PASSWORD CAN NOT BE NULL'');\n}\n$password=md5($password);\n\n$result=mysql_query("select id,nick,sex,provice,city,signature,picUrl,type from user where email like ''{$email}'' and password like ''{$password}''");\n$data=mysql_fetch_object($result);\n\n\nif(empty($data)){\n  return $this->send_error(''10042'',''USER DOES NOT EXIST'');\n}\nelse{\n return $this->send_result($data);\n}'),
(50, 'activity', 'queryBycityAndtime', '$city=v(''city'');\n$startTime=v(''startTime'');\n$endTime=v(''endTime'');\n\nif(empty($city)){\n    return $this->send_error(''10006'',''CITY CAN NOT BE NULL'');\n}\n\nif(empty($startTime)){\n    return $this->send_error(''10006'',''TIME CAN NOT BE NULL'');\n}\n\nif(empty($endTime)){\n    return $this->send_error(''10006'',''TIME CAN NOT BE NULL'');\n}\n\n$sql="select id,name,city,time,context,userId,startTime,endTime,location,latitude,longitude,picName,picUrl,peopleLimit,type\n,(select nick from user u where u.id=userId) as userName, (select picUrl from user u where u.id=userId) as userPicUrl\n,(select picName from user u where u.id=userId) as userPicName\n,(select count(*) from activityjoin aj where aj.activityId=id) as jcount\n,(select count(*) from activitycomment ac where ac.activityId=id) as ccount\nfrom activity \nwhere city like ''${city}'' and time>=''${startTime}'' and time<''${endTime}'' order by time desc";\n\n$result=mysql_query($sql); \n$i=0;\nwhile($data=mysql_fetch_object($result)){\n	$data_array[$i++]=$data;\n}\n\n\n return $this->send_result($data_array);'),
(53, 'activityjoin', 'IsJoin', '$activityId=v(''activityId'');\n$userId=v(''userId'');\n\nif(empty($activityId)){\n    return $this->send_error(''10006'',''ACTIVITY ID CAN NOT BE NULL'');\n}\n\nif(empty($userId)){\n    return $this->send_error(''10006'',''USER ID CAN NOT BE NULL'');\n}\n\n$sql="select id,activityId,userId,attribute\nfrom activityjoin \nwhere activityId = ${activityId} and userId = ${userId}";\n\n$result=mysql_query($sql);\n\n$i=0;\nwhile($data=mysql_fetch_object($result)){\n    $i=1;\n}\nreturn $this->send_result($i);'),
(68, 'userattention', 'querybyuserid', '$userId=v(''userId'');\n\nif(empty($userId)){\n return $this->send_error("10070" , "USERID CAN NOT BE NULL" );\n}\n\n$sql="select attentionId,\nattentionId as userId\n,(select nick from user u where u.id=attentionId) as userName\n,(select picUrl from user u where u.id=attentionId) as userPicUrl\n,(select picName from user u where u.id=attentionId) as userPicName\n,(select signature from user u where u.id=attentionId) as userSignature\n,(select sex from user u where u.id=attentionId) as userSex\n,(select birthday from user u where u.id=attentionId) as userBirthday\nfrom userattention \nwhere userId=$userId order by id desc";\n\n$result=mysql_query($sql); \n$i=0;\nwhile($data=mysql_fetch_object($result)){\n    $data_array[$i++]=$data;\n}\nreturn $this->send_result($data_array);'),
(62, 'activitycomment', 'querybyactivityId', '$activityId=v(''activityId'');\n\nif(empty($activityId)){\n    return $this->send_error(''10006'',''ACTIVITYID CAN NOT BE NULL'');\n}\n\n$sql="select id,userId,time,context,attribute,\n(select u.nick from user u where u.id=userId) as userName,\n(select u.picName from user u where u.id=userId) as userPicName,\n(select u.picUrl from user u where u.id=userId) as userPicUrl \nfrom activitycomment where activityId=${activityId} order by time desc";\n\n$result=mysql_query($sql); \nif(!empty($result)){\n$i=0;\nwhile($data=mysql_fetch_object($result)){\n    $data_array[$i++]=$data;\n}\n}\nreturn $this->send_result($data_array);'),
(64, 'activityjoin', 'querybyactivityId', '$activityId=v(''activityId'');\n\nif(empty($activityId)){\n    return $this->send_error(''10006'',''ACTIVITY ID CAN NOT BE NULL'');\n}\n\n$sql="select id,activityId,userId,attribute\n,(select nick from user u where u.id=userId) as userName\n,(select picUrl from user u where u.id=userId) as userPicUrl\n,(select picName from user u where u.id=userId) as userPicName\n,(select signature from user u where u.id=userId) as userSignature\n,(select sex from user u where u.id=userId) as userSex\n,(select birthday from user u where u.id=userId) as userBirthday\nfrom activityjoin \nwhere activityId=$activityId order by id desc";\n\n$result=mysql_query($sql); \n$i=0;\nwhile($data=mysql_fetch_object($result)){\n    $data_array[$i++]=$data;\n}\nreturn $this->send_result($data_array);');

-- --------------------------------------------------------

--
-- 表的结构 `__meta_user`
--

CREATE TABLE IF NOT EXISTS `__meta_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- 转存表中的数据 `__meta_user`
--

INSERT INTO `__meta_user` (`id`, `email`, `password`) VALUES
(1, 'admin@admin.com', '21232f297a57a5a743894a0e4a801fc3'),
(2, 'zyj520@yeah.net', 'b8f6c39b0acb67abfe50e5b6f16f10c1'),
(3, 'hyben123@gmail.com', '60674c5c00c79dcf888efb112f3c2bd5');
