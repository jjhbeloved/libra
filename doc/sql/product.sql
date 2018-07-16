-- 产品表(一个产品负责人下面有N个项目)
CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `teamId` int(10) NOT NULL,
  `productLeaderId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_team_id_name` (`teamId`,`name`),
  KEY `idx_name` (`name`),
  KEY `idx_leader_id` (`productLeaderId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8