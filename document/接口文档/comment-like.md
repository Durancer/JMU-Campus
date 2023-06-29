## /添加或取消点赞

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8003/comment/like

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 5 | String | 是 | 用户id |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| commentId | 20 | String | 是 | 评论id |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /判断评论是否被点赞

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8003/comment/like/exist

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 10 | String | 是 | 用户id |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| commentId | 20 | String | 是 | 评论id |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /查询用户点赞的所有评论

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8003/comment/like/common

#### 请求方式

> GET

#### Content-Type

> none

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | 用户id |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /查询用户被点赞评论的数据

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8003/comment/like/user/common

#### 请求方式

> GET

#### Content-Type

> none

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | 用户id |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```
