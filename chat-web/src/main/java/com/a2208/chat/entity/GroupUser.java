package com.a2208.chat.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 群组映射表
 */
@Data
public class GroupUser {
    /** id
     */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 群id
     */
    private Long groupId;
    /** 群内昵称 */
    private String name;
    /** 进群时间 */
    private Date createTime;

    private User user;

    private Group group;

    public GroupUser() {
    }

    public GroupUser(Long id, Long userId, Long groupId, String name) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.name = name;
    }

}