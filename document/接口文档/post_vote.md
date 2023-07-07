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

## /查看帖子下的评论

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8003/comment/post/list

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 5 | String | 否 | - |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| postId | 11 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /发起投票

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/vote/launch

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| postId | 14 | String | 是 | - |
| topic | 投票测试3 | String | 是 | - |
| type | multiple | String | 是 | - |
| cycle | month | String | 是 | - |
| options | 投票选项测试1 | String | 是 | - |
| options | 投票选项测试2 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /删除投票

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/vote/delete

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| voteId | 4 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /查询投票信息

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/vote/getone?postId=21

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Query参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| postId | 21 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /用户投票

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/vote/record/add

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | - |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| voteId | 10 | String | 是 | - |
| optionIds | 15 | String | 是 | - |
| optionIds | 16 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /发布帖子

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/add

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | - |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| title | 测试 | String | 是 | - |
| content | 111 | String | 是 | - |
| topic | test | String | 是 | - |
| type | multiple | String | 是 | - |
| cycle | week | String | 是 | - |
| options | 选项3 | String | 是 | - |
| options | 选项4 | String | 是 | - |
| options | 选项5 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /删除帖子

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/delete

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | - | String | 是 | - |

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| postId | 19 | String | 是 | - |
| userId | 1 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /查看帖子详情

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/detail?postId=20

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | - |

#### 请求Query参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| postId | 20 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /分页查询帖子集合

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8002/post/list/all

#### 请求方式

> GET

#### Content-Type

> form-data

#### 请求Header参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| userId | 1 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```
