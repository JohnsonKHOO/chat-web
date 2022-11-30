package com.a2208.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 用户表
 */
@Data
public class User {
    /** id */
    private Long id;
    /** 账号 */
    private String account;
    /** 昵称 */
    private String nickname;
    /**
     * 密码
     * 前端不显示用户密码
     * */
    @JsonIgnore
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

    public User() {
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


}