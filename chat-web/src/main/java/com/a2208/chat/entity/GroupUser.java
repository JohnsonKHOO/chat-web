package com.a2208.chat.entity;

import java.util.Date;

/**
 * 群组映射表
 */
public class GroupUser {
    /** id
     */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 群id
     */
    private Integer groupId;
    /** 群内昵称 */
    private String name;
    /** 进群时间 */
    private Date createTime;

    public GroupUser() {
    }

    public GroupUser(Long id, Long userId, Integer groupId, String name) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
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

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        GroupUser that = (GroupUser) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "id=" + id +
                ",userId='" + userId + "'" +
                ",groupId='" + groupId + "'" +
                ",name='" + name + "'" +
                ",createTime='" + createTime + "'" +
                '}';
    }

}