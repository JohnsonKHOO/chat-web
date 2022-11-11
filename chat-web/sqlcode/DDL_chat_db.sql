/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = friend   */
/******************************************/
CREATE TABLE `friend` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `friend_id` bigint NOT NULL COMMENT '映射好友的id',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '默认好友' COMMENT '设置好友称呼',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='好友映射表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = group   */
/******************************************/
CREATE TABLE `group` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '群名字',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `owner_id` bigint NOT NULL COMMENT '群主',
  `avatar` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '群头像',
  `notice` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '公告',
  `signature` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '群简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群组表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = group_message   */
/******************************************/
CREATE TABLE `group_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `from_user_id` bigint NOT NULL COMMENT '群内发送者',
  `to_group_id` int NOT NULL COMMENT '发送至群号',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '聊天内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群聊信息表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = group_user   */
/******************************************/
CREATE TABLE `group_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id\r\n',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `group_id` int NOT NULL COMMENT '群id\r\n',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '群内昵称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '进群时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='群组映射表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = message   */
/******************************************/
CREATE TABLE `message` (
  `id` bigint NOT NULL COMMENT 'id',
  `from_user_id` bigint NOT NULL COMMENT '发送者',
  `to_user_id` bigint NOT NULL COMMENT '接收者',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '聊天信息',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '信息发送时间',
  `read_flag` int DEFAULT NULL COMMENT '0未读 1已读',
  `img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='聊天信息表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = user   */
/******************************************/
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `nickname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` int DEFAULT NULL COMMENT '年龄',
  `sex` int DEFAULT NULL COMMENT '性别',
  `signature` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `state_id` int DEFAULT '0' COMMENT '用户在线状态',
  `avatar` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `role_id` int DEFAULT NULL COMMENT '扮演角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表'
;

/******************************************/
/*   DatabaseName = chat_db   */
/*   TableName = user_state   */
/******************************************/
CREATE TABLE `user_state` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `state_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '在线、离线等状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户状态'
;
