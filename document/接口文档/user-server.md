## 用户服务接口文档
请求 BaseUrl：http://127.0.0.1/


#### 用户登录

```
请求地址：/user/login
请求方法：POST
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



#### 用户注册

```
请求地址：/user/register
请求方法：POST
```

**入参字段**

| 字段      | 类型    | 含义                                 | 必填 |
| --------- | ------- | ------------------------------------ | ---- |
| idencode  | String  | 验证码                               | 是   |
| username  | String  | 用户账号                             | 是   |
| password  | String  | 用户密码                             | 是   |
| email     | String  | 用户邮箱                             | 是   |
| nickname  | String  | 用户名称                             | 是   |
| introduce | String  | 个人介绍                             | 否   |
| sex       | Integer | 性别 0 匿 \| 1 boy \| 2 girl，默认 0 | 否   |
| phone     | String  | 电话                                 | 否   |

**出参**

```json
{
    "code": 200,
    "message": "注册成功",
    "data": null,
    "status": true
}
```



#### 获取用户信息

```
请求地址：/user/detail
请求方法：GET
```

**入参字段**

| 字段   | 类型    | 含义   | 必填 |
| ------ | ------- | ------ | ---- |
| userId | Integer | 用户id | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```

#### 获取用户相关统计数据

如：用户一共有几条评论等，可以在用户主页使用
```
请求地址：/user/general
请求方法：GET
```

**入参字段**

以下入参字段至少一个必填，当查询他人统计时，传递 otherUserId 即可，查询自己的统计数据时有 token就行


| 字段   | 类型    | 含义   | 必填 |
| ------ | ------- | ------ | ---- |
| token | Integer | 请求头添加 | 否   |
| otherUserId | Integer | 要查询的用户id | 否   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```


#### 修改用户头像

```
请求地址：/user/person/update/avatar
请求方法：POST
```

**入参字段**

| 字段  | 类型   | 含义                                 | 必填 |
| ----- | ------ | ------------------------------------ | ---- |
| token | String | 请求头加入                           | 是   |
| file  | file   | 新头像文件（限jpg、jpeg、png、webp） | 是   |

**出参**

```json
{
    "code": 200,
    "message": "上传成功",
    "data": "xxx.jpg",
    "status": true
}
```

出参数据：新头像地址



#### 更新用户信息

传递需要更新的字段即可

```
请求地址：/user/person/update/userInfo
请求方法：POST
```

**入参字段**

| 字段      | 类型    | 含义                               | 必填 |
| --------- | ------- | ---------------------------------- | ---- |
| token     | String  | 请求头加入                         | 是   |
| nickname  | String  | 用户名称                           | 否   |
| introduce | String  | 个性介绍                           | 否   |
| sex       | Integer | 性别，合法值：0 匿 \| 1 男 \| 2 女 | 否   |
| phone     | String  | 电话号码                           | 否   |

**出参**

```json
{
    "code": 200,
    "message": "更新成功",
    "data": null,
    "status": true
}
```

#### 邮箱验证登录

```
请求地址：/user/login/email
请求方法：POST
```

**入参字段**

| 字段     | 类型   | 含义     | 必填 |
| -------- | ------ | -------- | ---- |
| idencode | String | 验证码   | 是   |
| email    | String | 用户邮箱 | 是   |

**出参**

- **登录失败**

```json
{
    "code": 200,
    "message": "验证码错误",
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

| 字段       | 解释      |
| ---------- | --------- |
| username   | 用户账号  |
| nickname   | 用户名称  |
| avatarUrl  | 头像地址  |
| createTime | 注册时间  |
| token      | 用户token |

#### 发送邮箱验证码

```
请求地址：/user/send/code
请求方法：POST
```

**入参字段**

| 字段  | 类型   | 含义     | 必填 |
| ----- | ------ | -------- | ---- |
| email | String | 用户邮箱 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "发送成功",
    "data": null,
    "status": true
}
```















