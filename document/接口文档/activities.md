## 活动相关接口文档

请求 BaseUrl：http://127.0.0.1/



#### 抢送流量券

在每周日的 13：00  -- 14：00，进行放送，一共限制 10 张，每个用户只能抢 1张。

流量券将帮助帖子提升热度，上热榜

```
请求地址：/user/activity/traffic/voucher
请求方法：POST
```

**入参字段**

| 字段  | 类型   | 含义       | 必填 |
| ----- | ------ | ---------- | ---- |
| token | String | 请求头添加 | 是   |

**出参**

成功

```json
{
    "code": 200,
    "message": "成功抢到",
    "data": null,
    "status": true
}
```

失败

```json
{
    "code": 200,
    "message": "已经抢完或已经抢过",
    "data": null,
    "status": true
}
```



