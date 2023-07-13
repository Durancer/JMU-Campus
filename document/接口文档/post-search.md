## /post-search

```text
暂无描述
```

#### 公共Header参数

| 参数名 | 示例值 | 参数描述 |
| --- | --- | ---- |
| 暂无参数 |

#### 公共Query参数

| 参数名 | 示例值 | 参数描述 |
| --- | --- | ---- |
| 暂无参数 |

#### 公共Body参数

| 参数名 | 示例值 | 参数描述 |
| --- | --- | ---- |
| 暂无参数 |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /post-search/es分词查看

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> 127.0.0.1:9200/jmu_post_info/_analyze

#### 请求方式

> GET

#### Content-Type

> json

#### 请求Body参数

```javascript
{
  "analyzer" : "ik_max_word",
  "text" : "SpringBoot数据库redisjava"
}
```

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /post-search/es添加索引和映射

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> 127.0.0.1:9200/jmu_post_info

#### 请求方式

> PUT

#### Content-Type

> json

#### 请求Body参数

```javascript
{
    "mappings":{
        "properties":{
            "id":{
                "type":"long"
            },
            "title":{
                "type":"text",
                "analyzer":"ik_smart"
            },
            "userId": {
                "type": "long"
            },
            "nickname": {
                "type": "text"
            },
            "content":{
                "type":"text",
                "analyzer":"ik_smart"
            },
            "createTime":{
                "type":"date"
            }
        }
    }
}
```

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```

## /post-search/es分页查询

```text
暂无描述
```

#### 接口状态

> 开发中

#### 接口URL

> localhost:8005/post/search/page

#### 请求方式

> POST

#### Content-Type

> form-data

#### 请求Body参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| pageNum | 0 | String | 是 | - |
| pageSize | 5 | String | 是 | - |
| searchWords | 福州市 | String | 是 | - |

#### 预执行脚本

```javascript
暂无预执行脚本
```

#### 后执行脚本

```javascript
暂无后执行脚本
```
