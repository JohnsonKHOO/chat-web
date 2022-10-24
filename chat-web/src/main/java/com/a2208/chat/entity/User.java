package com.a2208.chat.entity;

import java.time.LocalDate;
import java.util.Date;

/**
 * 用户表
 */
public class User {
    /** id */
    private Long id;
    /** 账号 */
    private String account;
    /** 昵称 */
    private String nickname;
    /** 密码 */
    private String password;
    /** 生日 */
    private Date birthday;
    /** 年龄 */
    private Integer age;
    /** 性别 */
    private Integer sex;
    /** 简介 */
    private String signature;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
    /** 用户在线状态 */
    private Integer stateId;
    /** 头像 */
    private String avatar;
    /** 扮演角色id */
    private Integer roleId;

    public User(String account, String nickname, String password, Date birthday, Integer sex, String avatar, Integer roleId) {
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.age = LocalDate.now().getYear() - birthday.getYear() - 1900;
        this.sex = sex;
        this.avatar = avatar;
        this.roleId = roleId;
    }

    public User(Long id, String account, String nickname, String password, Date birthday, Integer sex, String avatar, Integer roleId) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.birthday = birthday;
        this.age = LocalDate.now().getYear() - birthday.getYear() - 1900;
        this.sex = sex;
        this.avatar = avatar;
        this.roleId = roleId;
    }

    public User() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getStateId() {
        return this.stateId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        User that = (User) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ",account='" + account + "'" +
                ",nickname='" + nickname + "'" +
                ",password='" + password + "'" +
                ",birthday='" + birthday + "'" +
                ",age='" + age + "'" +
                ",sex='" + sex + "'" +
                ",signature='" + signature + "'" +
                ",createTime='" + createTime + "'" +
                ",updateTime='" + updateTime + "'" +
                ",stateId='" + stateId + "'" +
                ",avatar='" + avatar + "'" +
                ",roleId='" + roleId + "'" +
                '}';
    }

}