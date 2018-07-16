-- 操作日指标
CREATE TABLE `op_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `opType` int(10) DEFAULT NULL COMMENT '操作类型',
  `opUserId` int(11) unsigned NOT NULL COMMENT '操作人ID',
  `opUserIp` varchar(128) DEFAULT NULL COMMENT '操作人IP',
  `envId` int(11) unsigned DEFAULT 0 COMMENT '环境ID',
  `opTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `projectId` int(11) unsigned DEFAULT 0 COMMENT '项目ID',
  `content` text COMMENT '操作的内容',
  `key1` mediumtext,
  `key2` mediumtext,
  `key3` mediumtext,
  `key4` mediumtext,
  `key5` mediumtext,
  `key6` mediumtext,
  PRIMARY KEY (`id`),
  KEY `idx_projectId` (`projectId`),
  KEY `idx_opUserId` (`opUserId`),
  KEY `idx_opTime` (`opTime`),
  KEY `idx_envId` (`envId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

