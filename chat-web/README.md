# 数据库

#### sql语句

```sql
create table friend
(
    id        bigint auto_increment comment 'id'
        primary key,
    user_id   bigint                     not null comment '用户id',
    friend_id bigint                     not null comment '映射好友的id',
    name      varchar(45) default '默认好友' null comment '设置好友称呼'
)
    comment '好友映射表';

create table `group`
(
    id          int auto_increment comment 'id'
        primary key,
    name        varchar(45)                         not null comment '群名字',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    owner_id    bigint                              not null comment '群主',
    avatar      varchar(225)                        null comment '群头像',
    notice      varchar(512)                        null comment '公告',
    signature   varchar(512)                        null comment '群简介'
)
    comment '群组表';

create table group_message
(
    id           bigint auto_increment comment 'id'
        primary key,
    from_user_id bigint                              not null comment '群内发送者',
    to_group_id  int                                 not null comment '发送至群号',
    content      text                                null comment '聊天内容',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '发送时间',
    img          varchar(255)                        null comment '图片'
)
    comment '群聊信息表';

create table group_user
(
    id          bigint auto_increment comment 'id
'
        primary key,
    user_id     bigint                              not null comment '用户id',
    group_id    int                                 not null comment '群id
',
    name        varchar(45)                         null comment '群内昵称',
    create_time timestamp default CURRENT_TIMESTAMP null comment '进群时间'
)
    comment '群组映射表';

create table message
(
    id           bigint                              not null comment 'id'
        primary key,
    from_user_id bigint                              not null comment '发送者',
    to_user_id   bigint                              not null comment '接收者',
    content      text charset utf8mb4                null comment '聊天信息',
    create_time  timestamp default CURRENT_TIMESTAMP null comment '信息发送时间',
    read_flag    int                                 null comment '0未读 1已读',
    img          varchar(255)                        null comment '图片'
)
    comment '聊天信息表' charset = utf8mb3;

create table user
(
    id          bigint auto_increment comment 'id'
        primary key,
    account     varchar(255)                        not null comment '账号',
    nickname    varchar(45)                         null comment '昵称',
    password    varchar(255)                        not null comment '密码',
    birthday    date                                null comment '生日',
    age         int                                 null comment '年龄',
    sex         int                                 null comment '性别',
    signature   varchar(512)                        null comment '简介',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp                           null on update CURRENT_TIMESTAMP comment '修改时间',
    state_id    int                                 null comment '用户在线状态',
    avatar      varchar(225)                        null comment '头像',
    role_id     int                                 null comment '扮演角色id'
)
    comment '用户表';


```



---



# 聊天系统接口

> v1.0.0

Base URLs:

# Default

## POST 登录

POST /login

### Params

| Name     | Location | Type   | Required | Description |
| -------- | -------- | ------ | -------- | ----------- |
| account  | query    | string | yes      | none        |
| password | query    | string | yes      | none        |

```json
{
  "account": "ovvbhofinl",
  "password": "bborgovwbd",
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 4,
    "account": "ovvbhofinl",
    "nickName": "曹勇",
    "password": "bborgovwbd",
    "bitrhday": "1990-04-07",
    "age": 32,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-03 20:58:26",
    "updateTime": "2022-10-19 23:18:49",
    "stateId": 0,
    "avatar": "data:image/png;base64,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",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | Success     | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

## POST 注册

POST /register

### Params

| Name     | Location | Type    | Required | Description |
| -------- | -------- | ------- | -------- | ----------- |
| account  | query    | string  | yes      | none        |
| nickname | query    | string  | yes      | none        |
| password | query    | string  | yes      | none        |
| birthday | query    | string  | yes      | none        |
| sex      | query    | integer | yes      | none        |
| avatar   | query    | string  | no       | none        |

```json
{
  "account": "xdvckskda",
  "nickName": "文平",
  "password": "acyhzvqp",
  "bitrhday": "2006-05-03",
  "sex": 1,
  "avatar": "",

}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 4,
    "account": "xdvckskda",
    "nickName": "文平",
    "password": "acyhzvqp",
    "bitrhday": "2006-05-03",
    "age": 16,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-19 20:58:26",
    "updateTime": "",
    "stateId": 0,
    "avatar": "",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

# 用户

## GET 用户列表

GET /user/list

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 3,
    "account": "xdvckskda",
    "nickName": "文平",
    "password": "acyhzvqp",
    "bitrhday": "2006-05-03",
    "age": 16,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-19 20:58:26",
    "updateTime": "",
    "stateId": 0,
    "avatar": "",
    "roleId": 1
  },
  {
    "id": 4,
    "account": "ovvbhofinl",
    "nickName": "曹勇",
    "password": "bborgovwbd",
    "bitrhday": "1990-04-07",
    "age": 32,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-03 20:58:26",
    "updateTime": "2022-10-19 23:18:49",
    "stateId": 0,
    "avatar": "data:image/png;base64,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",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

## PUT 更新用户信息

PUT /user/update

### Params

| Name      | Location | Type    | Required | Description |
| --------- | -------- | ------- | -------- | ----------- |
| nickname  | query    | string  | no       | none        |
| signature | query    | string  | no       | none        |
| account   | query    | string  | no       | none        |
| password  | query    | string  | no       | none        |
| sex       | query    | integer | no       | none        |
| birthday  | query    | string  | no       | none        |

```json
{
  "account": "ovvbhofinl",
  "nickName": "曹勇",
  "password": "bborgovwbd",
  "bitrhday": "1990-04-07",
  "sex": 1,
  "signature": "",
  "avatar": "data:image/png;base64,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",

}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 4,
    "account": "ovvbhofinl",
    "nickName": "曹勇",
    "password": "bborgovwbd",
    "bitrhday": "1990-04-07",
    "age": 32,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-03 20:58:26",
    "updateTime": "2022-10-19 23:18:49",
    "stateId": 0,
    "avatar": "data:image/png;base64,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",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

## DELETE 删除用户

DELETE /user/delete

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| id   | query    | string | no       | none        |

```json
{
  "id": 10
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": null
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name   | Type    | Required | Restrictions | Title | description          |
| ------ | ------- | -------- | ------------ | ----- | -------------------- |
| » code | integer | true     | none         |       | 200：成功，400：失败 |
| » msg  | string  | true     | none         |       | 返回信息             |
| » data | null    | true     | none         |       | 返回数据             |

## GET 搜索用户

GET /user/search

### Params

| Name     | Location | Type    | Required | Description |
| -------- | -------- | ------- | -------- | ----------- |
| id       | query    | integer | no       | none        |
| account  | query    | string  | no       | none        |
| nickname | query    | string  | no       | none        |

```json
{
  "id": ,
  "account": "",
  "nickName": "曹勇",
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 4,
    "account": "ovvbhofinl",
    "nickName": "曹勇",
    "password": "bborgovwbd",
    "bitrhday": "1990-04-07",
    "age": 32,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-03 20:58:26",
    "updateTime": "2022-10-19 23:18:49",
    "stateId": 0,
    "avatar": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAADwCAYAAADLh6BnAAAFzUlEQVR4Xu2YvW4UVxxH/2MTsGRw+BBIQENBRST6tIHXAArSIaoUKA1INIgHoIMCkHiFNAHxEqSBBokoEojP2MQQ8KC74zuemZ1dL4uRlsOhA++u/Dtnzp1ZirXHUYZ/sAQKBWPdDoYpmO1XwXC/ClYwnQB8n/dgBcMJwOdZsILhBODzLFjBcALweRasYDgB+DwLVjCcAHyeBSsYTgA+z4IVDCcAn2fBCoYTgM+zYAXDCcDnWbCC4QTg8yxYwXAC8HkWrGA4Afg8C1YwnAB8ngUrGE4APs+CFQwnAJ9nwQqGE4DPs2AFwwnA51mwguEE4PMsWMFwAvB5FqxgOAH4PAtWMJwAfJ4FKxhOAD7PghUMJwCfZ8EKhhOAz7NgBcMJwOdZsILhBODzLFjBcALweRasYDgB+DwLVjCcAHyeBSsYTgA+z4IVDCcAn2fBCoYTgM+zYAXDCcDnWbCC4QTg8yxYwXAC8HkWrGA4Afg8C1YwnAB8ngUrGE4APs+CFQwnAJ9nwQqGE4DPs2AFwwnA51mwguEE4PMsWMFwAvB5FqxgOAH4PAtWMJwAfJ4FKxhOAD7PghUMJwCfZ8EKhhOAz7NgBcMJwOdZsILhBODzLFjBcALweRasYDgB+DwLVjCcAHyeBSsYTgA+b+YKLg78GbFwImL5RpQvfq3xFz9ejli6EFFsr/5t9W6UT09u/Dy/L/3L2r9RvjwXsXJ7Yn3FwQcRPxyrXj/q/YunothzLaLYEfHmapSvLw5eXuy9HrHzbPXe8n3rZxP/Al/phbMjeOGXKPbdipg/XE1tCB6Sm2Gsv6a+KJqQPv4d5fPTEav3NkXXkptf3fP++nUNiS25+b1TXGCb/pJTvmA2BOcy5nZFrD2PmNvXFpzrXK+2Fvr/X1G+PF9dGHP7q3Le3W/9PeYPVXVl6Km4ZoUfHlZ/T/Gl6rs/36TSWnq+2PJJ0DmBpvTzxW+bHcG7r0T56vcoFs8MHdEDiNuO1kdfXU0SvnKzElS+qYvtHvO1hNW7EfMHq6M4Xyz56P/wKMp/forIJ0mxVB/z9Qny8Ul1PHcvpuZr83HduYV8sakpP2A2BDd++VH34PolWcA65MG/p3vz2rNhwRly84RIrx9zfNcXTzodusJfX4pi6bcNwd36V25v3I/z+6cUs1Vv+7YEN+/T+UjMBY4TnI7dfMyPeQiq5TYugNYF9/ZO+3ag4M+/DkcW3CN38Om5zjFH9OC+2nxK7jk+++TWn52eDfr+/PdHxPbjER7Rk4vuFTxKbvrYzpHdfchKX2WGnnQ7T7m9cpsXzyjByzcidvxc3dN9yJpMcp/g3q9B6ePW73NjvyalevPXryRk25HqIS7fI7v35/xr9h3l3Yup7+JJ7/dr0mjZQ4K734+bb208yLQkNwC3vlKlh6am0CQ8/cn/SdH87AkFD45//6Njsnp91dYTmLmn6K2f+H1/ooLh/hWsYDgB+DwLVjCcAHyeBSsYTgA+z4IVDCcAn2fBCoYTgM+zYAXDCcDnWbCC4QTg8yxYwXAC8HkWrGA4Afg8C1YwnAB8ngUrGE4APs+CFQwnAJ9nwQqGE4DPs2AFwwnA51mwguEE4PMsWMFwAvB5FqxgOAH4PAtWMJwAfJ4FKxhOAD7PghUMJwCfZ8EKhhOAz7NgBcMJwOdZsILhBODzLFjBcALweRasYDgB+DwLVjCcAHyeBSsYTgA+z4IVDCcAn2fBCoYTgM+zYAXDCcDnWbCC4QTg8yxYwXAC8HkWrGA4Afg8C1YwnAB8ngUrGE4APs+CFQwnAJ9nwQqGE4DPs2AFwwnA51mwguEE4PMsWMFwAvB5FqxgOAH4PAtWMJwAfJ4FKxhOAD7PghUMJwCfZ8EKhhOAz7NgBcMJwOdZsILhBODzLFjBcALweRasYDgB+DwLVjCcAHyeBSsYTgA+z4IVDCcAn2fBCoYTgM+zYAXDCcDnWbCC4QTg8yxYwXAC8HkWDBf8CYZhj02SRVXOAAAAAElFTkSuQmCC",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

## GET 显示单个用户信息

GET /user/getbyid

### Params

| Name | Location | Type   | Required | Description |
| ---- | -------- | ------ | -------- | ----------- |
| id   | query    | string | no       | none        |

```json
{
  "id": 4,
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 4,
    "account": "ovvbhofinl",
    "nickName": "曹勇",
    "password": "bborgovwbd",
    "bitrhday": "1990-04-07",
    "age": 32,
    "sex": 1,
    "signature": "",
    "createTime": "2022-10-03 20:58:26",
    "updateTime": "2022-10-19 23:18:49",
    "stateId": 0,
    "avatar": "data:image/png;base64,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",
    "roleId": 1
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                | Required | Restrictions | Title | description          |
| ------------- | ------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer             | true     | none         |       | 200：成功，400：失败 |
| » msg         | string              | true     | none         |       | 返回信息             |
| » data        | [user](#schemauser) | true     | none         |       | 返回数据             |
| »» id         | integer             | true     | none         |       | none                 |
| »» account    | string              | true     | none         |       | none                 |
| »» nickName   | string              | false    | none         |       | none                 |
| »» password   | string              | true     | none         |       | none                 |
| »» bitrhday   | string              | false    | none         |       | none                 |
| »» age        | integer             | false    | none         |       | none                 |
| »» sex        | integer             | false    | none         |       | none                 |
| »» signature  | string              | false    | none         |       | none                 |
| »» createTime | string              | false    | none         |       | none                 |
| »» updateTime | string              | false    | none         |       | none                 |
| »» stateId    | integer             | false    | none         |       | none                 |
| »» avatar     | string              | false    | none         |       | none                 |
| »» roleId     | integer             | true     | none         |       | none                 |

# 好友

## GET 聊天记录

GET /chat/messagelist

显示用户与某个好友的历史聊天记录

### Params

| Name     | Location | Type    | Required | Description              |
| -------- | -------- | ------- | -------- | ------------------------ |
| id       | query    | integer | no       | 用户自己的id             |
| friendId | query    | integer | no       | 想要查看聊天记录的好友id |

```json
{
  "id": 3,
  "friendId": 4,
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "fromUserId": 3,
    "toUserId": 4,
    "content": "hi!",
    "createTime": "2019-10-08 13:55:31",
    "readFlag": 1,
    "img": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                      | Required | Restrictions | Title | description          |
| ------------- | ------------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer                   | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                    | true     | none         |       | 返回信息             |
| » data        | [message](#schemamessage) | true     | none         |       | 返回数据             |
| »» id         | integer                   | true     | none         |       | id                   |
| »» fromUserId | integer                   | true     | none         |       | 从用户id             |
| »» toUserId   | integer                   | true     | none         |       | 发给用户id           |
| »» content    | string                    | true     | none         |       | 消息内容             |
| »» createTime | string                    | false    | none         |       | none                 |
| »» readFlag   | integer                   | true     | none         |       | 已读标记             |
| »» img        | string                    | false    | none         |       | 图片（如果有的话）   |

## GET 搜索好友

GET /friend/search

通过好友账号或id搜索好友

### Params

| Name    | Location | Type    | Required | Description |
| ------- | -------- | ------- | -------- | ----------- |
| id      | query    | integer | no       | none        |
| account | query    | string  | no       | none        |

```json
{
    "id": 4,
    "account": ovvbhofinl,
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "userId": 3,
    "friendId": 4,
    "name": "班长"
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type                    | Required | Restrictions | Title | description          |
| ----------- | ----------------------- | -------- | ------------ | ----- | -------------------- |
| » code      | integer                 | true     | none         |       | 200：成功，400：失败 |
| » msg       | string                  | true     | none         |       | 返回信息             |
| » data      | [friend](#schemafriend) | true     | none         |       | 返回数据             |
| »» id       | integer                 | true     | none         |       | id                   |
| »» userId   | integer                 | true     | none         |       | 用户自身id           |
| »» friendId | integer                 | true     | none         |       | 用户好友id           |
| »» name     | string                  | false    | none         |       | 好友备注             |

## POST 添加好友

POST /friend/add

### Params

| Name     | Location | Type    | Required | Description |
| -------- | -------- | ------- | -------- | ----------- |
| id       | query    | integer | yes      | none        |
| friendId | query    | integer | yes      | none        |
| name     | query    | string  | no       | none        |

```json
{
  "userId": 3,
  "friendId": 6,
  "name": ""
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 1,
    "userId": 3,
    "friendId": 6,
    "name": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type                    | Required | Restrictions | Title | description |
| ----------- | ----------------------- | -------- | ------------ | ----- | ----------- |
| » code      | string                  | true     | none         |       | none        |
| » msg       | string                  | true     | none         |       | none        |
| » data      | [friend](#schemafriend) | true     | none         |       | 返回数据    |
| »» id       | integer                 | true     | none         |       | id          |
| »» userId   | integer                 | true     | none         |       | 用户自身id  |
| »» friendId | integer                 | true     | none         |       | 用户好友id  |
| »» name     | string                  | false    | none         |       | 好友备注    |

## GET 好友列表

GET /friend/list

### Params

| Name   | Location | Type   | Required | Description  |
| ------ | -------- | ------ | -------- | ------------ |
| userId | query    | string | yes      | 用户自己的id |

```json
{
  "userId": 3
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "userId": 3,
    "friendId": 4,
    "name": "班长"
  },
  {
    "id": 1,
    "userId": 3,
    "friendId": 6,
    "name": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type                    | Required | Restrictions | Title | description          |
| ----------- | ----------------------- | -------- | ------------ | ----- | -------------------- |
| » code      | integer                 | true     | none         |       | 200：成功，400：失败 |
| » msg       | string                  | true     | none         |       | 返回信息             |
| » data      | [friend](#schemafriend) | true     | none         |       | 返回数据             |
| »» id       | integer                 | true     | none         |       | id                   |
| »» userId   | integer                 | true     | none         |       | 用户自身id           |
| »» friendId | integer                 | true     | none         |       | 用户好友id           |
| »» name     | string                  | false    | none         |       | 好友备注             |

## POST 向指定好友发送信息

POST /chat/send

### Params

| Name       | Location | Type    | Required | Description |
| ---------- | -------- | ------- | -------- | ----------- |
| fromUserId | query    | integer | yes      | none        |
| toUserId   | query    | integer | yes      | none        |
| content    | query    | string  | yes      | none        |
| img        | query    | string  | no       | none        |

```json
{
  "fromUserId": 3,
  "toUserId": 4,
  "content": "晚上去哪里吃？",
  "img": ""
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "fromUserId": 3,
    "toUserId": 4,
    "content": "晚上去哪里吃？",
    "createTime": "1987-10-18 14:38:57",
    "readFlag": 0,
    "img": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                      | Required | Restrictions | Title | description          |
| ------------- | ------------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer                   | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                    | true     | none         |       | 返回信息             |
| » data        | [message](#schemamessage) | true     | none         |       | 返回数据             |
| »» id         | integer                   | true     | none         |       | id                   |
| »» fromUserId | integer                   | true     | none         |       | 从用户id             |
| »» toUserId   | integer                   | true     | none         |       | 发给用户id           |
| »» content    | string                    | true     | none         |       | 消息内容             |
| »» createTime | string                    | false    | none         |       | none                 |
| »» readFlag   | integer                   | true     | none         |       | 已读标记             |
| »» img        | string                    | false    | none         |       | 图片（如果有的话）   |

## DELETE 删除好友

DELETE /friend/delete

### Params

| Name     | Location | Type    | Required | Description |
| -------- | -------- | ------- | -------- | ----------- |
| userId   | query    | integer | yes      | none        |
| friendId | query    | integer | yes      | none        |

```json
{
  "userId": 3,
  "friendId": 4,
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": null
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name   | Type    | Required | Restrictions | Title | description          |
| ------ | ------- | -------- | ------------ | ----- | -------------------- |
| » code | integer | true     | none         |       | 200：成功，400：失败 |
| » msg  | string  | true     | none         |       | 返回信息             |
| » data | null    | true     | none         |       | 返回数据             |

## GET 用户收取信息

GET /chat/receive

### Params

| Name       | Location | Type    | Required | Description |
| ---------- | -------- | ------- | -------- | ----------- |
| fromUserId | query    | integer | yes      | none        |
| toUserId   | query    | integer | yes      | none        |

```json
{
  "fromUserId": 3,
  "toUserId": 4,
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "fromUserId": 3,
    "toUserId": 4,
    "content": "晚上去哪里吃？",
    "createTime": "2022-10-18 14:38:57",
    "readFlag": 1,
    "img": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                      | Required | Restrictions | Title | description          |
| ------------- | ------------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer                   | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                    | true     | none         |       | 返回信息             |
| » data        | [message](#schemamessage) | true     | none         |       | 返回数据             |
| »» id         | integer                   | true     | none         |       | id                   |
| »» fromUserId | integer                   | true     | none         |       | 从用户id             |
| »» toUserId   | integer                   | true     | none         |       | 发给用户id           |
| »» content    | string                    | true     | none         |       | 消息内容             |
| »» createTime | string                    | false    | none         |       | none                 |
| »» readFlag   | integer                   | true     | none         |       | 已读标记             |
| »» img        | string                    | false    | none         |       | 图片（如果有的话）   |

## PUT 设置好友备注

PUT /friend/updatename

### Params

| Name     | Location | Type    | Required | Description |
| -------- | -------- | ------- | -------- | ----------- |
| id       | query    | integer | yes      | none        |
| friendId | query    | integer | yes      | none        |
| name     | query    | string  | no       | none        |

```json
{
  "userId": 3,
  "friendId": 4,
  "name": "同学"
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "userId": 3,
    "friendId": 4,
    "name": "同学"
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name        | Type                    | Required | Restrictions | Title | description          |
| ----------- | ----------------------- | -------- | ------------ | ----- | -------------------- |
| » code      | integer                 | true     | none         |       | 200：成功，400：失败 |
| » msg       | string                  | true     | none         |       | 返回信息             |
| » data      | [friend](#schemafriend) | true     | none         |       | 返回数据             |
| »» id       | integer                 | true     | none         |       | id                   |
| »» userId   | integer                 | true     | none         |       | 用户自身id           |
| »» friendId | integer                 | true     | none         |       | 用户好友id           |
| »» name     | string                  | false    | none         |       | 好友备注             |

# 群组

## GET 群组列表

GET /group/list

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "name": "大软课群",
    "createTime": "2022-10-19 15:20:17",
    "ownerId": 3,
    "avatar": "",
    "notice": "",
    "signature": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                  | Required | Restrictions | Title | description          |
| ------------- | --------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer               | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                | true     | none         |       | 返回信息             |
| » data        | [group](#schemagroup) | true     | none         |       | 返回数据             |
| »» id         | integer               | true     | none         |       | id                   |
| »» name       | string                | true     | none         |       | 群名字               |
| »» createTime | string                | false    | none         |       | none                 |
| »» ownerId    | integer               | true     | none         |       | 群主id               |
| »» avatar     | string                | false    | none         |       | 群头像               |
| »» notice     | string                | false    | none         |       | 公告                 |
| »» signature  | string                | false    | none         |       | 简介                 |

## GET 群组消息记录

GET /groupmessage/list

### Params

| Name    | Location | Type    | Required | Description |
| ------- | -------- | ------- | -------- | ----------- |
| userId  | query    | integer | no       | none        |
| groupId | query    | integer | no       | none        |

```json
{
  "userId": ,
  "groupId": 0,
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "UserId": 3,
    "groupId": 0,
    "content": "欢迎进群",
    "createTime": "2022-10-19 18:28:50",
    "img": ""
  },
  {
    "id": 1,
    "UserId": 4,
    "groupId": 0,
    "content": "谢谢",
    "createTime": "2022-10-19 18:29:03",
    "img": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                                  | Required | Restrictions | Title | description          |
| ------------- | ------------------------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer                               | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                                | true     | none         |       | 返回信息             |
| » data        | [group_message](#schemagroup_message) | true     | none         |       | 返回数据             |
| »» id         | integer                               | true     | none         |       | none                 |
| »» UserId     | integer                               | true     | none         |       | none                 |
| »» groupId    | integer                               | true     | none         |       | none                 |
| »» content    | string                                | true     | none         |       | none                 |
| »» createTime | string                                | false    | none         |       | none                 |
| »» img        | string                                | false    | none         |       | none                 |

## GET 群组搜索

GET /group/search

### Params

| Name | Location | Type    | Required | Description |
| ---- | -------- | ------- | -------- | ----------- |
| id   | query    | integer | no       | none        |
| name | query    | string  | no       | none        |

```json
{
  "id": 0,
  "name": "大软课群",
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "name": "大软课群",
    "createTime": "2022-10-19 15:20:17",
    "ownerId": 3,
    "avatar": "",
    "notice": "",
    "signature": ""
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                  | Required | Restrictions | Title | description          |
| ------------- | --------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer               | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                | true     | none         |       | 返回信息             |
| » data        | [group](#schemagroup) | true     | none         |       | 返回数据             |
| »» id         | integer               | true     | none         |       | id                   |
| »» name       | string                | true     | none         |       | 群名字               |
| »» createTime | string                | false    | none         |       | none                 |
| »» ownerId    | integer               | true     | none         |       | 群主id               |
| »» avatar     | string                | false    | none         |       | 群头像               |
| »» notice     | string                | false    | none         |       | 公告                 |
| »» signature  | string                | false    | none         |       | 简介                 |

## POST 加入群组

POST /groupuser/add

### Params

| Name    | Location | Type    | Required | Description |
| ------- | -------- | ------- | -------- | ----------- |
| userId  | query    | integer | yes      | none        |
| groupId | query    | integer | yes      | none        |
| name    | query    | string  | no       | 群内昵称    |

```json
{
  "userId": 4,
  "groupId": 0,
  "name": "",
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "userId": 4,
    "groupId": 0,
    "name": "",
    "createTime": "2022-10-19 18:27:35"
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                            | Required | Restrictions | Title | description                |
| ------------- | ------------------------------- | -------- | ------------ | ----- | -------------------------- |
| » code        | integer                         | true     | none         |       | 200：成功，400：失败       |
| » msg         | string                          | true     | none         |       | 返回信息                   |
| » data        | [group_user](#schemagroup_user) | true     | none         |       | 返回数据                   |
| »» id         | integer                         | true     | none         |       | id                         |
| »» userId     | integer                         | true     | none         |       | 用户id                     |
| »» groupId    | integer                         | true     | none         |       | 群组id                     |
| »» name       | string                          | false    | none         |       | 群内昵称（默认为用户昵称） |
| »» createTime | string                          | false    | none         |       | none                       |

## DELETE 用户退出群组

DELETE /groupuser/delete

### Params

| Name    | Location | Type    | Required | Description |
| ------- | -------- | ------- | -------- | ----------- |
| userId  | query    | integer | no       | none        |
| groupId | query    | integer | no       | none        |

```json
{
  userId: 5,
  groupId: 0,
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": null
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name   | Type    | Required | Restrictions | Title | description          |
| ------ | ------- | -------- | ------------ | ----- | -------------------- |
| » code | integer | true     | none         |       | 200：成功，400：失败 |
| » msg  | string  | true     | none         |       | 返回信息             |
| » data | null    | true     | none         |       | 返回数据             |

## PUT 设置群内昵称

PUT /group/updatename

### Params

| Name    | Location | Type    | Required | Description |
| ------- | -------- | ------- | -------- | ----------- |
| id      | query    | integer | yes      | none        |
| groupId | query    | integer | yes      | none        |
| name    | query    | string  | no       | none        |

```json
{
   "id": 0,
   "userId": 3,
   "groupId": 0,
   "name": "~群主~",
}
```



```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "userId": 3,
    "groupId": 0,
    "name": "~群主~",
    "createTime": "2022-10-19 18:48:19"
  }
}
```

### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                            | Required | Restrictions | Title | description                |
| ------------- | ------------------------------- | -------- | ------------ | ----- | -------------------------- |
| » code        | integer                         | true     | none         |       | 200：成功，400：失败       |
| » msg         | string                          | true     | none         |       | 返回信息                   |
| » data        | [group_user](#schemagroup_user) | true     | none         |       | 返回数据                   |
| »» id         | integer                         | true     | none         |       | id                         |
| »» userId     | integer                         | true     | none         |       | 用户id                     |
| »» groupId    | integer                         | true     | none         |       | 群组id                     |
| »» name       | string                          | false    | none         |       | 群内昵称（默认为用户昵称） |
| »» createTime | string                          | false    | none         |       | none                       |

## POST 创建群组

POST /group/register

### Params

| Name      | Location | Type    | Required | Description |
| --------- | -------- | ------- | -------- | ----------- |
| name      | query    | string  | yes      | none        |
| ownerId   | query    | integer | yes      | 群主用户id  |
| avatar    | query    | string  | no       | none        |
| notice    | query    | string  | no       | none        |
| signature | query    | string  | no       | none        |

```json
{
  "id": 0,
  "name": "大软课群",
  "ownerId": 3,
  "avatar": "",
  "notice": "",
  "signature": ""
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "name": "大软课群",
    "createTime": "2022-10-19 15:20:17",
    "ownerId": 3,
    "avatar": "",
    "notice": "",
    "signature": ""
  }
}
```



### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                  | Required | Restrictions | Title | description          |
| ------------- | --------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer               | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                | true     | none         |       | 返回信息             |
| » data        | [group](#schemagroup) | true     | none         |       | 返回数据             |
| »» id         | integer               | true     | none         |       | id                   |
| »» name       | string                | true     | none         |       | 群名字               |
| »» createTime | string                | false    | none         |       | none                 |
| »» ownerId    | integer               | true     | none         |       | 群主id               |
| »» avatar     | string                | false    | none         |       | 群头像               |
| »» notice     | string                | false    | none         |       | 公告                 |
| »» signature  | string                | false    | none         |       | 简介                 |



## PUT 更新群信息通知/简介）

PUT /group/update

### Params

| Name      | Location | Type    | Required | Description |
| --------- | -------- | ------- | -------- | ----------- |
| ownerId   | query    | integer | yes      | none        |
| groupId   | query    | integer | yes      | none        |
| name      | query    | string  | no       | none        |
| avatar    | query    | string  | no       | none        |
| notice    | query    | string  | no       | none        |
| signature | query    | string  | no       | none        |

```json
{
  "id": 0,
  "name": "大软课群",
  "ownerId": 3,
  "avatar": "data:image/png;base64,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",
  "notice": "明天记得交作业",
  "signature": "武大课群"
}
```

```json
{
  "code": 200,
  "msg": "成功",
  "data": {
    "id": 0,
    "name": "大软课群",
    "createTime": "2022-10-19 15:20:17",
    "ownerId": 3,
    "avatar": "",
    "notice": "",
    "signature": ""
  }
}
```



### Responses

| HTTP Status Code | Meaning                                                 | Description | Data schema |
| ---------------- | ------------------------------------------------------- | ----------- | ----------- |
| 200              | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | OK          | Inline      |

### Responses Data Schema

HTTP Status Code **200**

| Name          | Type                  | Required | Restrictions | Title | description          |
| ------------- | --------------------- | -------- | ------------ | ----- | -------------------- |
| » code        | integer               | true     | none         |       | 200：成功，400：失败 |
| » msg         | string                | true     | none         |       | 返回信息             |
| » data        | [group](#schemagroup) | true     | none         |       | 返回数据             |
| »» id         | integer               | true     | none         |       | id                   |
| »» name       | string                | true     | none         |       | 群名字               |
| »» createTime | string                | false    | none         |       | none                 |
| »» ownerId    | integer               | true     | none         |       | 群主id               |
| »» avatar     | string                | false    | none         |       | 群头像               |
| »» notice     | string                | false    | none         |       | 公告                 |
| »» signature  | string                | false    | none         |       | 简介                 |



### 

# Data Schema

<h2 id="tocS_group_message">group_message</h2>

<a id="schemagroup_message"></a>
<a id="schema_group_message"></a>
<a id="tocSgroup_message"></a>
<a id="tocsgroup_message"></a>

```json
{
  "id": 0,
  "UserId": 0,
  "groupId": 0,
  "content": "string",
  "createTime": "string",
  "img": "string"
}

```

### Attribute

| Name       | Type    | Required | Restrictions | Title | Description |
| ---------- | ------- | -------- | ------------ | ----- | ----------- |
| id         | integer | true     | none         |       | none        |
| UserId     | integer | true     | none         |       | none        |
| groupId    | integer | true     | none         |       | none        |
| content    | string  | true     | none         |       | none        |
| createTime | string  | false    | none         |       | none        |
| img        | string  | false    | none         |       | none        |

<h2 id="tocS_group_user">group_user</h2>

<a id="schemagroup_user"></a>
<a id="schema_group_user"></a>
<a id="tocSgroup_user"></a>
<a id="tocsgroup_user"></a>

```json
{
  "id": 0,
  "userId": 0,
  "groupId": 0,
  "name": "string",
  "createTime": "string"
}

```

### Attribute

| Name       | Type    | Required | Restrictions | Title | Description                |
| ---------- | ------- | -------- | ------------ | ----- | -------------------------- |
| id         | integer | true     | none         |       | id                         |
| userId     | integer | true     | none         |       | 用户id                     |
| groupId    | integer | true     | none         |       | 群组id                     |
| name       | string  | false    | none         |       | 群内昵称（默认为用户昵称） |
| createTime | string  | false    | none         |       | none                       |

<h2 id="tocS_message">message</h2>

<a id="schemamessage"></a>
<a id="schema_message"></a>
<a id="tocSmessage"></a>
<a id="tocsmessage"></a>

```json
{
  "id": 0,
  "fromUserId": 0,
  "toUserId": 0,
  "content": "string",
  "createTime": "string",
  "readFlag": 0,
  "img": "string"
}

```

### Attribute

| Name       | Type    | Required | Restrictions | Title | Description        |
| ---------- | ------- | -------- | ------------ | ----- | ------------------ |
| id         | integer | true     | none         |       | id                 |
| fromUserId | integer | true     | none         |       | 从用户id           |
| toUserId   | integer | true     | none         |       | 发给用户id         |
| content    | string  | true     | none         |       | 消息内容           |
| createTime | string  | false    | none         |       | none               |
| readFlag   | integer | true     | none         |       | 已读标记           |
| img        | string  | false    | none         |       | 图片（如果有的话） |

<h2 id="tocS_group">group</h2>

<a id="schemagroup"></a>
<a id="schema_group"></a>
<a id="tocSgroup"></a>
<a id="tocsgroup"></a>

```json
{
  "id": 0,
  "name": "string",
  "createTime": "string",
  "ownerId": 0,
  "avatar": "string",
  "notice": "string",
  "signature": "string"
}

```

### Attribute

| Name       | Type    | Required | Restrictions | Title | Description |
| ---------- | ------- | -------- | ------------ | ----- | ----------- |
| id         | integer | true     | none         |       | id          |
| name       | string  | true     | none         |       | 群名字      |
| createTime | string  | false    | none         |       | none        |
| ownerId    | integer | true     | none         |       | 群主id      |
| avatar     | string  | false    | none         |       | 群头像      |
| notice     | string  | false    | none         |       | 公告        |
| signature  | string  | false    | none         |       | 简介        |

<h2 id="tocS_friend">friend</h2>

<a id="schemafriend"></a>
<a id="schema_friend"></a>
<a id="tocSfriend"></a>
<a id="tocsfriend"></a>

```json
{
  "id": 0,
  "userId": 0,
  "friendId": 0,
  "name": "string"
}

```

### Attribute

| Name     | Type    | Required | Restrictions | Title | Description |
| -------- | ------- | -------- | ------------ | ----- | ----------- |
| id       | integer | true     | none         |       | id          |
| userId   | integer | true     | none         |       | 用户自身id  |
| friendId | integer | true     | none         |       | 用户好友id  |
| name     | string  | false    | none         |       | 好友备注    |

<h2 id="tocS_user">user</h2>

<a id="schemauser"></a>
<a id="schema_user"></a>
<a id="tocSuser"></a>
<a id="tocsuser"></a>

```json
{
  "id": 0,
  "account": "string",
  "nickName": "string",
  "password": "string",
  "bitrhday": "string",
  "age": 0,
  "sex": 0,
  "signature": "string",
  "createTime": "string",
  "updateTime": "string",
  "stateId": 0,
  "avatar": "string",
  "roleId": 0
}

```

### Attribute

| Name       | Type    | Required | Restrictions | Title | Description |
| ---------- | ------- | -------- | ------------ | ----- | ----------- |
| id         | integer | true     | none         |       | none        |
| account    | string  | true     | none         |       | none        |
| nickName   | string  | false    | none         |       | none        |
| password   | string  | true     | none         |       | none        |
| bitrhday   | string  | false    | none         |       | none        |
| age        | integer | false    | none         |       | none        |
| sex        | integer | false    | none         |       | none        |
| signature  | string  | false    | none         |       | none        |
| createTime | string  | false    | none         |       | none        |
| updateTime | string  | false    | none         |       | none        |
| stateId    | integer | false    | none         |       | none        |
| avatar     | string  | false    | none         |       | none        |
| roleId     | integer | true     | none         |       | none        |

<h2 id="tocS_BaseResponse">BaseResponse</h2>

<a id="schemabaseresponse"></a>
<a id="schema_BaseResponse"></a>
<a id="tocSbaseresponse"></a>
<a id="tocsbaseresponse"></a>

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}

```

### Attribute

| Name | Type    | Required | Restrictions | Title | Description          |
| ---- | ------- | -------- | ------------ | ----- | -------------------- |
| code | integer | true     | none         |       | 200：成功，400：失败 |
| msg  | string  | true     | none         |       | 返回信息             |
| data | object  | true     | none         |       | 返回数据             |

