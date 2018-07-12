CREATE TABLE `system_setting` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `key` varchar(128)  NOT NULL,
  `value` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- 初始化缓存数据
insert into system_setting(creator, modifier, `key`, `value`)
values
('david','david','cache_enabled', 'true');