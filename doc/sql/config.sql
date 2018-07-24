CREATE TABLE `config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `key` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `desc` varchar(128) NOT NULL,
  `type` int(10) NOT NULL,
  `projectId` int(11) NOT NULL,
  `private` int(10) NOT NULL,
  `creatorId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key_project_id` (`key`,`projectId`),
  KEY `idx_project_id` (`projectId`),
  KEY `idx_creator_id` (`creatorId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8