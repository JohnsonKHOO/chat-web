use chat_db;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `nickname` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sex` int(8) DEFAULT NULL COMMENT '性别 0为女性 1为男性',
  `description` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '个性签名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(16) DEFAULT NULL COMMENT '发送者',
  `to_user_id` bigint(16) DEFAULT NULL COMMENT '接收者',
  `message` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '聊天信息',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '聊天信息',
  `read_flag` varchar(16) DEFAULT NULL COMMENT 'no未读 yes已读',
  `msg_type` int(8) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(8) NOT NULL,
  `content` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `imgs` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名如测试人员等',
  `password` varchar(255) DEFAULT NULL COMMENT '管理员密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COMMENT='后台角色表';