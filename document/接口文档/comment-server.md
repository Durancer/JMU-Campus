## 评论服务接口文档
请求 BaseUrl：http://127.0.0.1/


#### 添加评论

```
请求地址：/comment/add
请求方法：POST
```

**入参字段**

| 字段     | 类型    | 含义                                             | 必填 |
| -------- | ------- | ------------------------------------------------ | ---- |
| postId   | Integer | 帖子id                                           | 是   |
| content  | String  | 评论内容                                         | 是   |
| type     | String  | 评论类型（是否为根评论）合法值：root  \|  answer | 是   |
| rootId   | Integer | 根评论id  （回复时需要）                         | 否   |
| toUserId | Integer | 回复用户id  （回复时需要）                       | 否   |
| token    | String  | 请求头添加                                       | 是   |

**出参**

```json
{
    "code": 200,
    "message": "添加成功",
    "data": null,
    "status": true
}
```



#### 删除评论
当删除的评论为根评论时，会将所有子评论一同删除
```
请求地址：/comment/delete
请求方法：POST
```

**入参字段**

| 字段      | 类型    | 含义       | 必填 |
| --------- | ------- | ---------- | ---- |
| commentId | Integer | 帖子id     | 是   |
| token     | String  | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "删除成功",
    "data": null,
    "status": true
}
```

#### 更新评论

```
请求地址：/comment/delete
请求方法：POST
```

**入参字段**

| 字段      | 类型    | 含义       | 必填 |
| --------- | ------- | ---------- | ---- |
| commentId | Integer | 帖子id     | 是   |
| content | String | 更新内容     | 是   |
| token     | String  | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "更新成功",
    "data": null,
    "status": true
}
```




#### 获取用户的评论


```
请求地址：/comment/user/list
请求方法：GET
```

**入参字段**

以下入参字段至少一个必填，当查询他人评论时，传递 otherUserId 即可，查询自己的评论时有 token就行

| 字段  | 类型   | 含义       | 必填 |
| ----- | ------ | ---------- | ---- |
| token | String | 请求头添加 | 否   |
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



#### 获取回复我的评论

不包含自己回复自己的评论

```
请求地址：/comment/user/answer
请求方法：GET
```

**入参字段**

| 字段  | 类型   | 含义       | 必填 |
| ----- | ------ | ---------- | ---- |
| token | String | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 点赞或取消点赞

```
请求地址：/comment/like
请求方法：POST
```

**入参字段**

| 字段      | 类型   | 含义       | 必填 |
| --------- | ------ | ---------- | ---- |
| token     | String | 请求头添加 | 是   |
| userId    | String | 请求头添加 | 是   |
| commentId | String | 评论id     | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 判断评论是否被点赞

```
请求地址：/comment/like/exist
请求方法：GET
```

**入参字段**

| 字段      | 类型   | 含义       | 必填 |
| --------- | ------ | ---------- | ---- |
| token     | String | 请求头添加 | 是   |
| userId    | String | 请求头添加 | 是   |
| commentId | String | 评论id     | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 查询用户点赞所有评论

```
请求地址：/comment/like/common
请求方法：GET
```

**入参字段**

| 字段   | 类型   | 含义       | 必填 |
| ------ | ------ | ---------- | ---- |
| token  | String | 请求头添加 | 是   |
| userId | String | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 查询用户被点赞所有评论

```
请求地址：/comment/like/user/common
请求方法：GET
```

**入参字段**

| 字段   | 类型   | 含义       | 必填 |
| ------ | ------ | ---------- | ---- |
| token  | String | 请求头添加 | 是   |
| userId | String | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



