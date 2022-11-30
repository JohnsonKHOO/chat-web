package com.a2208.chat.entity;

import lombok.Data;

import java.util.Date;

/**
 * 群组表
 */
@Data
public class Group {
    /** id */
    private Long id;
    /** 群名字 */
    private String name;
    /** 创建时间 */
    private Date createTime;
    /** 群主 */
    private Long ownerId;
    /** 群头像 */
    private String avatar;
    /** 公告 */
    private String notice;
    /** 群简介 */
    private String signature;

    private User user;

    public Group() {
    }

    public Group(Long id, String name, Long ownerId, String avatar, String signature) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.avatar = avatar;
        this.signature = signature;
    }

}