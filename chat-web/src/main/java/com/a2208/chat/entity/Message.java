package com.a2208.chat.entity;

import java.util.Date;

/**
 * 聊天信息表
 */
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

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getToUserId() {
        return this.toUserId;
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

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getReadFlag() {
        return this.readFlag;
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
        Message that = (Message) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ",fromUserId='" + fromUserId + "'" +
                ",toUserId='" + toUserId + "'" +
                ",content='" + content + "'" +
                ",createTime='" + createTime + "'" +
                ",readFlag='" + readFlag + "'" +
                ",img='" + img + "'" +
                '}';
    }

}