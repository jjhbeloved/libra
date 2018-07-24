-- 公司组织结构按照 团队来分, 一个团队下面有N个product
CREATE TABLE `team` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

INSERT INTO `team`(gmt_created, gmt_modified, creator, modifier, version, name)
VALUES (NOW(), NOW(), 'david', 'david', 0, 'TA');