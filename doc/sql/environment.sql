CREATE TABLE `environment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `name` varchar(128) NOT NULL COMMENT '环境别名',
  `label` varchar(128) NOT NULL COMMENT '环境名标签',
  `ips` varchar(512) NOT NULL COMMENT 'zookeeper地址',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，0停用，1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8