# JMU-Campus
i集大校园软件服务端

<p align="center">
<a target="_blank" href="https://github.com/Durancer/JMU-Campus">
  <img src="https://img.shields.io/hexpm/l/plug.svg" alt=""/>
  <img src="https://img.shields.io/github/stars/durancer/JMU-campus" alt=""/>
  <img src="https://img.shields.io/github/forks/durancer/JMU-campus" alt=""/>
<br>
  <img src="https://img.shields.io/badge/SpringBoot-2.6.6-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1.0-green" alt=""/>
 <br>
  <img src="https://img.shields.io/badge/Vue-3.2-green" alt=""/>
  <img src="https://img.shields.io/badge/pinia-2.0.32-green" alt=""/>
  <img src="https://img.shields.io/badge/vite-4.1.4-green" alt=""/>
</a></p>

### 项目交流

- 项目后端已经自动化部署在 服务器
- 前端正在开发阶段，基本功能可用后将会部署上线
- 项目地址：http://www.jmucampus.top
- QQ交流群：294590170 
- 欢迎加入参与贡献！🌹

<img src="https://user-images.githubusercontent.com/102197880/261830919-5d54c286-6ac5-418a-895e-8d2c23b1c7e3.jpg" alt="" align="center" style="width: 300px"/>

### 项目模块
本项目基于 **SpringCloud Alibaba** 实现微服务架构项目。
服务包含：**网关服务**、**资源服务**、**聊天服务**、**帖子服务**、**评论服务**、**用户服务** 等将再继续进行拓展

<p align="center">
<img src="https://user-images.githubusercontent.com/102197880/261855760-9e080e89-6fc7-4030-8372-9e3f8c4422d7.png" alt="" align="middle" style="width: 80%"/>
</p>


### 项目技术架构图
<p align="center">
<img src="https://github.com/Durancer/JMU-Campus/assets/102197880/90f4cdbb-86b8-4493-86d0-0f753e99aeb6" style="width: 80%"/>
</p>

### 技术选型
#### 前端技术选型
计划实现 web端
vue3.2 + vue-router + pinia + axios + typescript + vite

#### 后端及部署技术选型
|      技术      |       说明       |                       官网                        |
| :------------: | :--------------: | :-----------------------------------------------: |
|   SpringBoot   |     MVC框架      |      https://spring.io/projects/spring-boot       |
|  SpringCloud   |    微服务框架    |     https://spring.io/projects/spring-cloud/      |
|    Gateway     |   网关服务框架   |  https://spring.io/projects/spring-cloud-gateway  |
|   OpenFegin    |     RPC框架      | https://spring.io/projects/spring-cloud-openfeign |
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
├─post-service：帖子服务
├─post-search-service：帖子搜索服务
└─campus-web：项目前端

通用部分
├─ -server 服务后端
├─ -sdk 服务软件开发包
├─ -client 服务客户端
```

项目各内容将首发再 **github** , gitee会同步代码，但不会同步 **issue** 和 **pr**

**github**地址：https://github.com/Durancer/JMU-Campus <br>
**gitee**地址：https://gitee.com/Durancer/JMU-Campus

对项目感兴趣欢迎 star 项目🌹
