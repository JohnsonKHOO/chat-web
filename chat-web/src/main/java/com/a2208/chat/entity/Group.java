package com.a2208.chat.entity;

import java.util.Date;

/**
 * 群组表
 */
public class Group {
    /** id */
    private Integer id;
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

    public Group() {
    }

    public Group(Integer id, String name, Long ownerId, String avatar, String signature) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.avatar = avatar;
        this.signature = signature;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
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

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNotice() {
        return this.notice;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return this.signature;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Group that = (Group) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ",name='" + name + "'" +
                ",createTime='" + createTime + "'" +
                ",ownerId='" + ownerId + "'" +
                ",avatar='" + avatar + "'" +
                ",notice='" + notice + "'" +
                ",signature='" + signature + "'" +
                '}';
    }

}