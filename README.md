# JMU-Campus
i集大校园软件服务端

<p align="center">
<a target="_blank" href="https://github.com/stick-i/scblogs">
  <img src="https://img.shields.io/hexpm/l/plug.svg" alt=""/>
  <img src="https://img.shields.io/github/stars/durancer/JMU-campus" alt=""/>
  <img src="https://img.shields.io/github/forks/durancer/JMU-campus" alt=""/>
<br>
  <img src="https://img.shields.io/badge/SpringBoot-2.6.6-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1.0-green" alt=""/><br>
</a></p>

### 项目模块
本项目基于 **SpringCloud Alibaba** 实现微服务架构项目。
预估服务包含：**网关服务**、**资源服务**、**聊天服务**、**贴子服务**、**留言服务**、**支付服务**等再继续进行拓展
![image](https://user-images.githubusercontent.com/102197880/214082236-c481fa90-1ef7-4aca-82a1-a0816684b36e.png)

### 技术选型
#### 前端技术选型
预计实现微信小程序和 app 两端
所以前端使用 **微信原生** 或 **uniapp** 实现

#### 后端及部署技术选型
|      技术      |       说明       |                       官网                        |
| :------------: | :--------------: | :-----------------------------------------------: |
|   SpringBoot   |     MVC框架      |      https://spring.io/projects/spring-boot       |
|  SpringCloud   |    微服务框架    |     https://spring.io/projects/spring-cloud/      |
|    Gateway     |   网关服务框架   |  https://spring.io/projects/spring-cloud-gateway  |
|   OpenFegin      |     RPC框架      | https://spring.io/projects/spring-cloud-openfeign |
|     Nacos      |  注册、配置中心  |              https://nacos.io/zh-cn/              |
|  MyBatis-Plus  |    数据库框架    |             https://mp.baomidou.com/              |
|     MySQL      |      数据库      |              https://www.mysql.com/               |
|     Redis      |    分布式缓存    |                 https://redis.io/                 |
|    RabbitMQ    |     消息队列     |             https://www.rabbitmq.com/             |
| Elasticsearch  |     搜索引擎     |      https://github.com/elastic/elasticsear       |
|     Docker     |    容器化部署    |              https://www.docker.com/              |
|    Jenkins     |  自动化部署服务  |              https://www.jenkins.io/              |
|     Druid      |   数据库连接池   |         https://github.com/alibaba/druid          |
|     SLF4J      |     日志框架     |               http://www.slf4j.org/               |
|     Lombok     | 简化对象封装工具 |      https://github.com/rzwitserloot/lombok       |
|     Nginx      |    web服务器     |                 http://nginx.org/                 |
|     Minio      | 本地对象存储服务 |                  https://min.io/                  |
| Docker Compose |  Docker容器编排  |         https://docs.docker.com/compose/          |


## 项目结构
暂未全部创建
```
├─document：项目文档及配置
├─common：公用模块
│  ├─common-amqp：公用AMQP模块
│  ├─common-core：核心模块
│  ├─common-data：公用数据模块
│  └─common-web：公用web模块
├─gateway-service：网关服务
├─resource-service：资源服务
├─user-service：用户服务
├─chat-service：聊天服务
├─blink-service：动态服务
├─pay-service：支付服务
└─uniapp：前端uniapp(将另建仓库)
```
目前项目完成公共模块、网关、资源服务基础编写，还有待完善。进行功能模块编写

项目各内容将首发再 **github** , gitee会同步代码，但不会同步 **issue** 和 **pr**

**github**地址：https://github.com/Durancer/JMU-Campus
**gitee**地址：https://gitee.com/Durancer/JMU-Campus

对项目感兴趣欢迎 star 项目👌
