CREATE TABLE `config_instance` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `configId` int(11) NOT NULL,
  `envId` int(11) NOT NULL,
  `desc` varchar(128) DEFAULT NULL,
  `value` mediumtext,
  `context` text,
  `contextmd5` varchar(128) NOT NULL,
  `creatorId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_configId_envId` (`configId`,`envId`),
  KEY `idx_envId` (`envId`),
  KEY `idx_creator_id` (`creatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;