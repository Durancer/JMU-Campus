## 用户服务接口文档



#### 登录

```
请求地址：/user/login
请求方法：GET
```

**入参字段**

| 字段     | 类型   | 含义     | 必填 |
| -------- | ------ | -------- | ---- |
| username | String | 用户账号 | 是   |
| password | String | 用户密码 | 是   |

**出参**

- **登录失败**

```json
{
    "code": 200,
    "message": "账号或密码错误",
    "data": null,
    "status": false
}
```

- **登录成功**

```json
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "userInfo": {
            "id": 1,
            "openid": "",
            "username": "lucy",
            "nickname": "lucy",
            "avatarUrl": "http://xxx/default.jpg",
            "introduce": "ta还没有个人介绍哦！",
            "sex": 0,
            "phone": null,
            "createTime": "2023-02-03T08:41:57.000+00:00"
        },
        "token": "xxxxx"
    },
    "status": true
}
```

**出参字段解释**

| 字段       | 解释                  |
| ---------- | --------------------- |
| username   | 用户账号              |
| nickname   | 用户名称              |
| avatarUrl  | 头像地址              |
| sex        | 性别：0未知、1男、2女 |
| createTime | 注册时间              |
| phone      | 电话                  |
| token      | 用户token             |



#### 注册





















