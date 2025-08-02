# FashOJ Backend

## 项目简介

FashOJ（Fashion Online Judge）是一个开源的在线代码评测平台后端系统，采用微服务架构设计，支持多种编程语言的代码提交和自动判题。

## 系统架构

![Project Architecture](/doc/project_Architecture.png "Magic Gardens")

## 技术栈

### 后端技术
- **Spring Boot 3.2.0** - 微服务框架
- **Spring Cloud Gateway** - API网关
- **Spring Security + JWT** - 认证授权
- **Spring Data JPA** - 数据库ORM
- **SpringDoc OpenAPI 3** - API文档生成
- **MapStruct** - 对象映射
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存和消息队列
- **MinIO** - 对象存储（测试数据）
- **RabbitMQ** - 消息队列

### 开发工具
- **Maven** - 项目构建
- **Docker Compose** - 本地开发环境

## 项目结构

```
FashOnlineJudgeBE/
├── pom.xml                          # 父级Maven配置
├── docker-compose.yml               # 本地开发环境
├── testApp.sh                       # 微服务启动/停止脚本
├── common-core/                     # 公共核心模块
│   ├── pom.xml
│   └── src/main/java/com/fashoj/common/core/
│       ├── exception/               # 异常处理
│       ├── response/                # 响应格式
│       └── util/                    # 工具类
├── common-data/                     # 公共数据模块
│   ├── pom.xml
│   └── src/main/java/com/fashoj/common/data/
│       ├── entity/                  # 基础实体
│       └── config/                  # 数据库配置
├── common-security/                 # 公共安全模块
│   ├── pom.xml
│   └── src/main/java/com/fashoj/common/security/
│       ├── config/                  # 安全配置
│       └── util/                    # JWT工具类
├── gateway/                         # API Gateway服务
│   ├── pom.xml
│   └── src/main/java/com/fashoj/gateway/
├── user-service/                    # 用户服务
│   ├── pom.xml
│   └── src/main/java/com/fashoj/user/
├── problem-service/                 # 题目服务
│   ├── pom.xml
│   └── src/main/java/com/fashoj/problem/
├── submission-service/              # 提交服务
│   ├── pom.xml
│   └── src/main/java/com/fashoj/submission/
└── judge-service/                   # 判题调度服务
    ├── pom.xml
    └── src/main/java/com/fashoj/judge/
```

## 服务说明

### API Gateway (端口: 8080)
- 统一入口，路由转发
- 跨域处理
- 负载均衡

### 用户服务 (端口: 8081)
- 用户注册、登录、认证
- 用户信息管理
- JWT Token生成和验证

### 题目服务 (端口: 8082)
- 题目CRUD操作
- 测试数据管理（MinIO存储）
- 题目分类和标签

### 提交服务 (端口: 8083)
- 代码提交记录
- 提交状态管理
- Redis缓存优化

### 判题服务 (端口: 8084)
- 判题任务调度
- 沙箱集群管理
- 判题结果处理

![service](/doc/service.png "Magic Gardens")

## 快速开始

### 环境要求
- JDK 21
- Maven 3.6+
- Docker & Docker Compose

### 启动开发环境

1. **启动基础服务**
```bash
docker-compose up -d
```

2. **编译项目**
```bash
mvn clean compile
```

3. **启动各个服务**

**方式一：使用启动脚本（推荐）**
```bash
# 启动所有服务
./testApp.sh start

# 查看服务状态
./testApp.sh status

# 停止所有服务
./testApp.sh stop

# 重启所有服务
./testApp.sh restart

# 查看帮助
./testApp.sh help
```

**方式二：手动启动各个服务**
```bash
# 启动Gateway
cd gateway && mvn spring-boot:run

# 启动用户服务
cd user-service && mvn spring-boot:run

# 启动题目服务
cd problem-service && mvn spring-boot:run

# 启动提交服务
cd submission-service && mvn spring-boot:run

# 启动判题服务
cd judge-service && mvn spring-boot:run
```

### 访问地址

- **API Gateway**: http://localhost:8080
- **用户服务API文档**: http://localhost:8081/swagger-ui/index.html
- **题目服务API文档**: http://localhost:8082/swagger-ui/index.html
- **提交服务API文档**: http://localhost:8083/swagger-ui/index.html
- **判题服务API文档**: http://localhost:8084/swagger-ui/index.html
- **MySQL**: localhost:3306 (用户名: fashoj, 密码: fashoj123)
- **Redis**: localhost:6379
- **MinIO控制台**: http://localhost:9001 (用户名: fashoj, 密码: fashoj123)
- **RabbitMQ管理界面**: http://localhost:15672 (用户名: fashoj, 密码: fashoj123)

## 开发指南

### 代码规范
- 遵循阿里巴巴Java开发手册
- 使用MapStruct进行对象映射
- 统一异常处理和响应格式
- API文档使用SpringDoc注解

### 数据库设计
- 使用JPA实体类自动建表
- 继承BaseEntity获得公共字段
- 软删除设计

### 安全设计
- JWT无状态认证
- 密码BCrypt加密
- 接口权限控制

### 项目架构
- 微服务架构，模块化设计
- 公共模块拆分：core（核心工具）、data（数据层）、security（安全）
- 统一网关入口，服务间解耦

### 日志管理
- 统一日志路径配置（Maven属性过滤）
- 按服务分离日志文件
- 支持日志滚动和压缩
- 日志文件位置：`logs/` 目录

## 许可证

MIT License - 详见 [LICENSE](LICENSE) 文件