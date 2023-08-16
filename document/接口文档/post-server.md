## 帖子服务接口文档
请求 BaseUrl：http://127.0.0.1/


#### 分页获取所有帖子

获取的为审核通过的帖子

```
请求地址：/post/list/all
请求方法：GET
```

**入参字段**

| 字段    | 类型    | 含义                           | 必填 |
| ------- | ------- | ------------------------------ | ---- |
| current | Integer | 分页需要获取的当前页，默认为 1 | 否   |
| size    | Integer | 每页帖子的数量，默认为 10      | 否   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 分页获取我的帖子

```
请求地址：/post/list/user/self
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



#### 分页获取用户帖子

获取的为审核通过的帖子

```
请求地址：/post/list/user
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



#### 获取帖子详细信息

```
请求地址：/post/detail
请求方法：GET
```

**入参字段**

| 字段   | 类型    | 含义   | 必填 |
| ------ | ------- | ------ | ---- |
| postId | Integer | 帖子id | 是   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```



#### 添加帖子
```
请求地址：/post/add
请求方法：POST
```

投票相关内容，当该帖子添加投票功能时为必填，否则可不填

**入参字段**

| 字段    | 类型    | 含义                                                       | 必填 |
| ------- | ------- | ---------------------------------------------------------- | ---- |
| title   | String  | 帖子标题                                                   | 是   |
| content | String  | 帖子内容                                                   | 是   |
| files   | file    | 帖子附件图片（限jpg、jpeg、png、webp格式，最大 9 张）      | 否   |
| token   | String  | 请求头添加                                                 | 是   |
| topic   | String  | 投票内容                                                   | 是   |
| type    | String  | 投票类型  radio 单选 \ multiple 多选                      | 是   |
| cycle   | String  | 投票周期  day 一天 \ week 一周 \ month 一月 \ year 一年 | 是   |
| options | Integer | 投票选项                                                   |  是   |

**出参**

```json
{
    "code": 200,
    "message": "发布成功",
    "data": null,
    "status": true
}
```



#### 删除帖子

```
请求地址：/post/delete
请求方法：POST
```

**入参字段**

| 字段   | 类型    | 含义       | 必填 |
| ------ | ------- | ---------- | ---- |
| postId | Integer | 帖子id     | 是   |
| token  | String  | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "删除成功",
    "data": null,
    "status": true
}
```



#### 审核用户帖子

```
请求地址：/post/check
请求方法：POST
```

**入参字段**

| 字段     | 类型    | 含义                                | 必填 |
| -------- | ------- | ----------------------------------- | ---- |
| postId   | Integer | 帖子id                              | 是   |
| decision | Integer | 审核结果， 1 审核通过 \| 2 审核通过 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "提交成功",
    "data": null,
    "status": true
}
```



#### 点赞 | 取消点赞用户帖子

如未点赞则进行点赞操作，否则反之

```
请求地址：/post/operate/like
请求方法：POST
```

**入参字段**

| 字段   | 类型    | 含义       | 必填 |
| ------ | ------- | ---------- | ---- |
| postId | Integer | 帖子id     | 是   |
| token  | String  | 请求头添加 | 是   |

**出参**

```json
{
    "code": 200,
    "message": "点赞成功 | 取消点赞",
    "data": null,
    "status": true
}
```



#### 发起投票

```
请求地址：/post/vote/launch
请求方法：POST
```

**入参字段**

| 字段    | 类型    | 含义                       | 必填 |
| ------- | ------- | -------------------------- | ---- |
| postId  | Integer | 帖子id                     | 是   |
| token   | String  | 请求头添加                 | 是   |
| topic   | String  | 投票标题                   | 是   |
| type    | String  | 投票类型 multiple \| redio | 是   |
| cycle   | String  | 投票周期                   | 是   |
| options | String  | 投票选项1                  | 是   |
| options | String  | 投票选项2                  | 否   |

**出参**

```json
{
    "code": 200,
    "message": "投票成功 | 投票失败",
    "data": null,
    "status": true
}
```



#### 删除投票

```
请求地址：/post/vote/delete
请求方法：POST
```

**入参字段**

| 字段   | 类型    | 含义       | 必填 |
| ------ | ------- | ---------- | ---- |
| token  | String  | 请求头添加 | 是   |
| voteId | Integer | 投票id     | 是   |

**出参**

```json
{
    "code": 200,
    "message": "删除成功",
    "data": null,
    "status": true
}
```



#### 查询投票信息

```
请求地址：/post/vote/getone
请求方法：GET
```

**入参字段**

| 字段   | 类型    | 含义       | 必填 |
| ------ | ------- | ---------- | ---- |
| token  | String  | 请求头添加 | 是   |
| postId | Integer | 投票id     | 是   |

**出参**

```json
{
    "code": 200,
    "message": data,
    "data": null,
    "status": true
}
```



#### 用户投票

```
请求地址：/post/vote/record/add
请求方法：POST
```

**入参字段**

| 字段      | 类型    | 含义       | 必填 |
| --------- | ------- | ---------- | ---- |
| token     | String  | 请求头添加 | 是   |
| postId    | Integer | 投票id     | 是   |
| userId    | Integer | 请求头添加 | 是   |
| voteId    | Integer | 投票id     | 是   |
| optionIds | Integer | 投票选项id | 是   |

**出参**

```json
{
    "code": 200,
    "message": data,
    "data": null,
    "status": true
}
```



#### 分页搜索帖子

```
请求地址：/post/search/page
请求方法：POST
```

**入参字段**

| 字段        | 类型    | 含义     | 必填 |
| ----------- | ------- | -------- | ---- |
| pageNum     | Integer | 页数     | 是   |
| pageSize    | Integer | 每页条数 | 否   |
| searchWords | String  | 搜索词   | 是   |

**出参**

```json
{
    "code": 200,
    "message": data,
    "data": null,
    "status": true
}
```



#### 分页获取审核帖子

获取的为审核通过的帖子

```
请求地址：/post/status/list
请求方法：GET
```

**入参字段**

| 字段    | 类型    | 含义                           | 必填 |
| ------- | ------- | ------------------------------ | ---- |
| current | Integer | 分页需要获取的当前页，默认为 1 | 否   |
| size    | Integer | 每页帖子的数量，默认为 10      | 否   |

**出参**

```json
{
    "code": 200,
    "message": "success",
    "data": data,
    "status": true
}
```

