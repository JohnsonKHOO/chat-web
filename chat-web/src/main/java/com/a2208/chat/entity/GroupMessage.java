package com.a2208.chat.entity;

import lombok.Data;

import java.util.Date;

/**
 * 群聊信息表
 */
@Data
public class GroupMessage {
    /** id */
    private Long id;
    /** 群内发送者 */
    private Long fromUserId;
    /** 发送至群号 */
    private Long toGroupId;
    /** 聊天内容 */
    private String content;
    /** 发送时间 */
    private Date createTime;
    /** 图片 */
    private String img;

    public GroupMessage() {
    }

    public GroupMessage(Long id, Long fromUserId, Long toGroupId, String content, String img) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toGroupId = toGroupId;
        this.content = content;
        this.img = img;
    }


}