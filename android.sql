/*
MySQL Backup
Database: android
Backup Time: 2020-12-20 19:49:57
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `android`.`account`;
DROP TABLE IF EXISTS `android`.`course`;
DROP TABLE IF EXISTS `android`.`hole`;
DROP TABLE IF EXISTS `android`.`test`;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `backgroundUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `isNew` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
CREATE TABLE `course` (
  `id` int NOT NULL,
  `day` int DEFAULT NULL,
  `startTime` int DEFAULT NULL,
  `endTime` int DEFAULT NULL,
  `courseName` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `hole` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
CREATE TABLE `test` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
BEGIN;
LOCK TABLES `android`.`account` WRITE;
DELETE FROM `android`.`account`;
INSERT INTO `android`.`account` (`id`,`nickname`,`email`,`password`,`avatarUrl`,`backgroundUrl`,`isNew`) VALUES (1, 'HIHIA', '283304489@qq.com', '$2a$10$ECUuhG1sTsHn9u3Qyra1derRYNJZYfi9BB1DZtKrGv.OUzFYH3hsS', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/a55dfa2dc6fc4bc0bbb4a1c756534c151608022698640.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/07114d6880614c2fb34ea070cade9389HupuBBS_201118130554-1242822577.png', 0),(2, 'ythihia', '18221221@bjtu.edu.cn', '$2a$10$MyAoIqqXppRIKIVJOyC.YOrI6Q5VzzAj/yOIZFJcJ2qrrpgceC0um', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/12/ee0b152635274c61af3df17fae23b12cavatar.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 1),(3, 'ljc', '18301043@bjtu.edu.cn', '$2a$10$xqKOUhOFlu1LB/oFbkv7g.64QzBTBwtBnyeijCQLFygZy2gamTKYy', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/12/ee0b152635274c61af3df17fae23b12cavatar.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 1),(4, 'dengliang', '2685138609@qq.com', '$2a$10$vVkNBQDmZxPtaveBcZidw.Gk6U9lfqJabIe/pBK49Pl8PZnF1wixy', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/0bae86921f0345c3ba8ca12cdc50614fmmexport1594101714927.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(5, 'ljzzz', '18301044@bjtu.edu.cn', '$2a$10$BRVB0ySTW7fkrL2/ugBY/.8Ww.YFZOsfy0ZpiVikXvea4r4Gm5zAG', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/14/0169fa91a709480aa57921bd23d3c877mmexport1594101717686.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/14/4638e15d49d442958c3952dcbcc1a5bePopi_2020_12_04_00_12_27.jpg', 0),(6, 'Caroline', '1425790430@qq.com', '$2a$10$fw/wC320Ua6tPvet.QZ8ROljn4RMILc34xuFaW03aqcb6kEC5.2va', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/0ac615ab14404d1492543f65d62f76cc69bc36831fb63ef.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/14/faff1ee253dd438e8486089b1d8c6fa2mmexport1601078585142.jpg', 0),(7, 'xzc', '18301054@bjtu.edu.cn', '$2a$10$0vPakcsQ7m9zyliOlL0dGuIKt6D0ue0/HdF9C2yj4sLqmtLZhzUBS', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/16/1f0a15750f0f489d9cf2795f3e9dd9baIMG_20201213_053716~2.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(8, 'Murphy', '18735694547@163.com', '$2a$10$.RKUMx.JcWfk3WK/z0qo4OYXnU5sv.RWuvlcW8.YzVuu6SlXeQFDu', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/12/ee0b152635274c61af3df17fae23b12cavatar.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 1),(9, 'zhang', '916822436@qq.com', '$2a$10$Hi.HrRmWgzo1WGvt3P7Da.hybCpnwNsb2yRzuSQBvu7D2YX0y3ZVG', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/12/ee0b152635274c61af3df17fae23b12cavatar.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 1),(10, 'xuzihan125', 'xuzihan125@163.com', '$2a$10$8WcQfF1/8vaPVzLsLcsXDOuFIgCphcN.iVrBnmihHs5mGzz5nOnGC', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/14/48043b909a00474eaa252709f3aa9816Screenshot_2020-12-14-19-43-34-414_com.bjtu.campus_information_platform.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(11, '7k7k', '1030967699@qq.com', '$2a$10$aT5Ix6YZdctD5a/A1yqZV.XsktVFxZJfN5d5Z5Dy0lkwZk/ceQf.O', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/12/ee0b152635274c61af3df17fae23b12cavatar.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 1),(12, 'chosen1', '18301060@bjtu.edu.cn', '$2a$10$2r45A26auPP/OK0rzUVqlu5jK8RQvj2p64DfXD7mM4Tj4hjKd1L.G', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/55ddadd2e6484b89a419acaa80c7406dmmexport1594101717686.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/14/e388e4e0bf4e474a94d03b6ca39db242null-dd3267c225f425f.jpg', 0),(13, 'xrsbsbsb', '844567437@qq.com', '$2a$10$C3GBEcQbSaINfs/11N/5De6i2QlWUief3faercBUXMCctHGAegoJK', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(14, 'paul', '3114938951@qq.com', '$2a$10$JBc60VzO15sXO8PbLcNJy.LxdYDSCQlle.ekYQSn1x3a0YBVwp3FG', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/cf54e9eda10c4240bb077d7528377975Screenshot_20201114_170057_com.tencent.mm.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/588167c83e2c4535bfaa61f51084fffcScreenshot_20201114_182729_com.tencent.mm.jpg', 0),(15, 'qwert', '18301042@bjtu.edu.cn', '$2a$10$H/RhdUPQhPOwSO8Ze0LTNut1o94xMNVsDHU1thxidlu7rYUqhI.5G', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/40c6ba098c354b63abf89b7020eae83e505E26E7EED034C6ED1EFA09B9218E94.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/cd5c77988ab44cb082d41b87ad58ce2236030EC15572317AD3E31F95CA4440C3.png', 0),(16, 'xuzihan', '18301055@bjtu.edu.cn', '$2a$10$Z7AEjxS.nBmBExtIJiKpIu0L9k4OvQ0AYTcKEO3r4cPS88itUO876', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(17, '2432014058', '2432014058@qq.com', '$2a$10$Woae83yh6Vfzwe9FSCI6Lu8XvKf8TFoumd5kZTmOf8oHi25cD0iAm', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/cf3f6da04a2f4ae696711acf146351233259393eb13533fa3501947ebfd3fd1f40345ba8.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/14bc5b3685ef4e5b99aee503f9e4e3adScreenshot_20201114_214743_tv.danmaku.bili.jpg', 0),(18, 'xuzihan111', '779740778@qq.com', '$2a$10$D.SEj47De36ABabkRMfV6uQ0vINIo.zvHB73LJdJqKqYfYXVVs6wG', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(19, 'ARO', '1394486562@qq.com', '$2a$10$nkxe6akDLu.36Wbq7kbbUurcGfXGwcagkh69./is4QYBWStQODSaq', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(20, 'yyyy', '18301084@bjtu.edu.cn', '$2a$10$hCGs5qUACajFxcrtAwKh1ekAiwMecQNqelzET2dfb3fbLSb2ImoOW', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/aae5642f61d748978c3f55418dad93b3Screenshot_20201213_003807_tv.danmaku.bili_edit_164973595293055.jpg', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/15/b718361afb614b37b26d501e531b6152Screenshot_20201110_215936.jpg', 0),(21, 'lucas', 'itslucas@itslucas.me', '$2a$10$AMBcDDdJQeOPX12WBSkgie9ghIxzxIaoiiiqIfMnFMczLGWJJUOBa', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(22, 'kellywells', '18301016@bjtu.edu.cn', '$2a$10$ybGf7i46j7wxKKeQia0YfuUWYdPWPJChGOX4FvhDEv.PvUAbPg2gm', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0),(23, 'hhhhh', '18301006@bjtu.edu.cn', '$2a$10$uEm4IAhM52xku0M9jbNOZe0pHk/oM1k29kMGkSDKCZE9/9Ciav/dO', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/4839513ad2b54517900059ca8cc503cbWechatIMG1001.png', 'https://hihia.oss-cn-beijing.aliyuncs.com/2020/12/13/8a08025711fe4bbc88fa822c5d523772WechatIMG655.jpeg', 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `android`.`course` WRITE;
DELETE FROM `android`.`course`;
INSERT INTO `android`.`course` (`id`,`day`,`startTime`,`endTime`,`courseName`,`room`) VALUES (10, 2, 2, 2, '计算机网络', 'yf615'),(10, 4, 2, 2, '计算机网络', 'yf505'),(10, 3, 3, 3, '数据库实践', 'yf305'),(10, 2, 4, 4, '嵌入式开发', 'yf206'),(10, 1, 3, 3, '计算机啊', 'yf123'),(12, 2, 3, 3, 'dlddw', 'dlddw'),(1, 3, 3, 3, '安卓开发', '逸夫'),(5, 2, 5, 5, 'android', 'yf205'),(14, 2, 5, 5, 'test', 'yt11'),(14, 1, 4, 4, 'hhgg', 'ggfff'),(4, 1, 2, 2, '芜湖', 'sx101'),(4, 1, 6, 6, '起飞', 'sx222'),(17, 1, 4, 4, '？^O^', 'YF208'),(15, 1, 1, 1, '黑胡椒', '哇'),(7, 1, 3, 3, 'Java', 'YF301'),(7, 3, 1, 1, 'C++', 'SX204'),(7, 5, 4, 4, 'Python', 'SY101'),(7, 3, 4, 4, 'C++', 'YF505'),(19, 1, 7, 7, 'Ry', 'Yf503'),(7, 4, 3, 3, 'PHP', 'SD107'),(18, 1, 6, 6, 'c++', 'yf303'),(1, 5, 3, 3, '哈哈哈', 'gg'),(20, 1, 3, 3, '干饭', '学活'),(20, 2, 6, 6, '干饭', '明湖'),(20, 6, 7, 7, '绿茶必备课', '酒吧'),(7, 7, 2, 2, 'OS', 'SY105');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `android`.`hole` WRITE;
DELETE FROM `android`.`hole`;
INSERT INTO `android`.`hole` (`id`,`content`,`time`) VALUES (1, '湖人总冠军', '2020/12/11 09:22:00'),(2, '加油大三人', '2020/12/11 09:22:30'),(3, '大三人大三魂', '2020/12/11 09:23:00'),(4, '测试4', '2020/12/11 09:23:01'),(5, '测试5', '2020/12/11 09:23:02'),(6, '测试6', '2020/12/11 09:23:03'),(7, '测试7', '2020/12/11 09:23:04'),(8, '测试8', '2020/12/11 09:23:05'),(9, '测试9', '2020/12/11 09:23:06'),(10, '测试10', '2020/12/11 09:23:07'),(11, '测试11', '2020/12/11 09:23:08'),(12, '测试12', '2020/12/11 09:23:09'),(13, '测试13', '2020/12/11 09:23:10'),(14, '测试14', '2020/12/11 09:23:11'),(15, '测试15', '2020/12/11 09:23:12'),(16, '测试16', '2020/12/11 09:23:13'),(17, '测试17', '2020/12/11 09:23:14'),(18, '周子涵真帅', '2020-12-11-22:30:47'),(19, '哈哈哈！', '2020-12-11-22:31:36'),(20, '周子涵是黑煤球', '2020-12-11-23:18:42'),(21, '周子涵大笨蛋', '2020-12-11-23:21:20'),(22, '六级必过！', '2020-12-12-10:12:27'),(23, '奥力给！', '2020-12-12-13:17:40'),(24, '周子涵大笨蛋啊大笨蛋', '2020-12-12-18:14:36'),(25, 'hello～', '2020-12-12-22:45:07'),(26, '15周加油！', '2020-12-13-00:02:11'),(27, 'zptql', '2020-12-13-17:47:46'),(28, 'Sssssjy', '2020-12-13-13:46:55'),(29, '掌上通通\n地表最强！', '2020-12-13-21:55:50'),(30, 'Fighting!', '2020-12-14-08:58:39'),(31, 'dltql\n', '2020-12-14-19:49:34'),(32, '徐紫程yydsb', '2020-12-14-21:40:07'),(33, 'xr yyds', '2020-12-14-22:02:11'),(34, '徐紫程我宝贝', '2020-12-14-22:02:37'),(35, 'test', '2020-12-15-00:23:07'),(36, '邓梁tql！！！', '2020-12-15-02:00:47'),(37, '啊啊啊', '2020-12-15-02:01:14'),(38, 'tql', '2020-12-15-02:01:31'),(39, '狗子', '2020-12-15-02:04:25'),(40, 'Hello world', '2020-12-14-18:21:44'),(41, 'test111', '2020-12-15-02:30:38'),(42, '张璞是个胖子', '2020-12-15-13:33:24'),(43, '树洞多少斤了', '2020-12-15-14:49:36'),(44, 'zzhzz', '2020-12-15-14:53:59'),(45, 'zzhzz', '2020-12-15-14:54:06'),(46, 'zzhdzz', '2020-12-15-14:54:17'),(47, '何运晨好帅！', '2020-12-15-14:54:43'),(48, '冰冰好美！！！舔屏', '2020-12-15-14:54:59'),(49, '周子涵学姐永远的女神！', '2020-12-15-14:55:36'),(50, '想认识子涵学姐', '2020-12-15-14:55:58'),(51, '子涵学姐也太美了吧', '2020-12-15-14:56:06'),(52, 'dltql', '2020-12-15-14:58:01'),(53, '邓梁太强了', '2020-12-15-14:58:10'),(54, 'dlddw', '2020-12-15-14:58:38'),(55, 'ljz我女神！！！！', '2020-12-15-17:12:18'),(56, '啦啦啦啦啦啦', '2020-12-15-17:23:28'),(57, '子涵学姐八千年第一美女！', '2020-12-15-17:45:57'),(58, '', '2020-12-17-14:57:31'),(59, 'xzcxzc', '2020-12-17-15:07:19'),(60, '', '2020-12-18-18:42:41');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `android`.`test` WRITE;
DELETE FROM `android`.`test`;
INSERT INTO `android`.`test` (`id`,`username`,`password`) VALUES (1, 'admin', '123456'),(2, 'test1', '123456'),(3, 'test2', '56789');
UNLOCK TABLES;
COMMIT;
