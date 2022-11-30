package com.a2208.chat.entity;


import lombok.Data;

import java.util.List;

/**
 * 好友映射表
 */
@Data
public class Friend {
    /** id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 映射好友的id */
    private Long friendId;
    /** 设置好友称呼 */
    private String name;
    /** 关联用户 */
    private User user;

    public Friend() {
    }

    public Friend(Long id, Long userId, Long friendId, String name) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.name = name;
    }


}