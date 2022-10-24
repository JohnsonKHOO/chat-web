package com.a2208.chat.entity;


/**
 * 好友映射表
 */
public class Friend {
    /** id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 映射好友的id */
    private Long friendId;
    /** 设置好友称呼 */
    private String name;

    public Friend() {
    }

    public Friend(Long id, Long userId, Long friendId, String name) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getFriendId() {
        return this.friendId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Friend that = (Friend) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ",userId='" + userId + "'" +
                ",friendId='" + friendId + "'" +
                ",name='" + name + "'" +
                '}';
    }

}