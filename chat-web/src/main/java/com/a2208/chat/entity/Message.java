package com.a2208.chat.entity;

import lombok.Data;

import java.util.Date;

/**
 * 聊天信息表
 */
@Data
public class Message {
    /** id */
    private Long id;
    /** 发送者 */
    private Long fromUserId;
    /** 接收者 */
    private Long toUserId;
    /** 聊天信息 */
    private String content;
    /** 信息发送时间 */
    private Date createTime;
    /** 0未读 1已读 */
    private Integer readFlag;
    /** 图片 */
    private String img;


    public Message() {
    }

    public Message(Long id, Long fromUserId, Long toUserId, String content, Integer readFlag, String img) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.readFlag = readFlag;
        this.img = img;
    }
}