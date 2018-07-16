-- 用户信息
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(50) DEFAULT NULL COMMENT '修改人',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本号',
  `loginName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(50) NOT NULL COMMENT '使用明文存储',
  `name` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL ,
  `admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是管理员 0不是 1是',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否被锁定 0未被锁定 1锁定',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0启用 1停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_login_name` (`loginName`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8