package com.a2208.chat.entity;

import java.util.Date;

/**
 * 群聊信息表
 */
public class GroupMessage {
    /** id */
    private Long id;
    /** 群内发送者 */
    private Long fromUserId;
    /** 发送至群号 */
    private Integer toGroupId;
    /** 聊天内容 */
    private String content;
    /** 发送时间 */
    private Date createTime;
    /** 图片 */
    private String img;

    public GroupMessage() {
    }

    public GroupMessage(Long id, Long fromUserId, Integer toGroupId, String content, String img) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toGroupId = toGroupId;
        this.content = content;
        this.img = img;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getFromUserId() {
        return this.fromUserId;
    }

    public void setToGroupId(Integer toGroupId) {
        this.toGroupId = toGroupId;
    }

    public Integer getToGroupId() {
        return this.toGroupId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        GroupMessage that = (GroupMessage) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GroupMessage{" +
                "id=" + id +
                ",fromUserId='" + fromUserId + "'" +
                ",toGroupId='" + toGroupId + "'" +
                ",content='" + content + "'" +
                ",createTime='" + createTime + "'" +
                ",img='" + img + "'" +
                '}';
    }

}