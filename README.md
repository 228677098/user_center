# 用户中心项目

## 项目概述

用户中心是一个基于 Spring Boot 3 构建的用户管理系统，提供完整的用户注册、登录、注销功能，以及用户查询和管理能力。项目采用 RESTful API 设计风格，支持多环境配置（开发、测试、生产），具备完善的权限控制和数据脱敏机制。


### 多环境和部署文档
    https://www.yuque.com/shayushitiande/hcq960/waku9u
### 核心功能
- ✅ 用户注册与登录（含密码加密）
- ✅ 用户会话管理（Session）
- ✅ 用户信息查询与搜索
- ✅ 管理员权限控制
- ✅ 用户数据脱敏保护
- ✅ 逻辑删除支持
- ✅ 统一异常处理与响应封装
- ✅ 多环境配置支持

### 📖 API 接口文档
详细的接口文档请查看：[doc/接口文档.md](doc/接口文档.md)

---

## 技术栈

### 后端框架
- **Spring Boot 3.5.14** - 核心框架
- **Java 17** - 编程语言
- **MyBatis-Plus 3.5.15** - ORM 框架
- **MySQL** - 关系型数据库

### 工具库
- **Lombok** - 简化 Java 代码
- **Apache Commons Lang3** - 字符串处理工具
- **Spring Boot DevTools** - 开发热部署

### 构建工具
- **Maven** - 项目依赖管理与构建

---

## 项目结构

```
user_center/
├── src/main/java/com/zyc/user_center/
│   ├── common/                 # 通用类
│   │   ├── BaseResponse.java   # 统一响应封装
│   │   ├── ErrorCode.java      # 错误码枚举
│   │   └── ResultUtil.java     # 结果工具类
│   ├── constant/               # 常量定义
│   │   └── UserConstant.java   # 用户相关常量
│   ├── controller/             # 控制器层
│   │   └── UserController.java # 用户接口
│   ├── exception/              # 异常处理
│   │   ├── BusinessException.java      # 业务异常
│   │   └── GlobalExceptionHandle.java  # 全局异常处理器
│   ├── mapper/                 # 数据访问层
│   │   ├── UserMapper.java     # 用户 Mapper 接口
│   │   └── UserMapper.xml      # MyBatis XML 映射
│   ├── model/domain/           # 领域模型
│   │   ├── User.java                   # 用户实体
│   │   └── request/                    # 请求 DTO
│   │       ├── UserLoginRequest.java   # 登录请求
│   │       └── UserRegisterRequest.java # 注册请求
│   ├── service/                # 服务层
│   │   ├── UserService.java            # 用户服务接口
│   │   └── impl/UserServiceImpl.java   # 用户服务实现
│   └── UserCenterApplication.java      # 启动类
├── src/main/resources/
│   ├── application.yml         # 主配置文件
│   ├── application-dev.yml     # 开发环境配置
│   ├── application-prod.yml    # 生产环境配置
│   └── mapper/                 # MyBatis XML 目录
├── sql/
│   └── create_table.sql        # 数据库建表脚本
└── pom.xml                     # Maven 配置文件
```

---

## 数据库设计

### 用户表 (user)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键，自增 |
| username | varchar(256) | 用户昵称 |
| userAccount | varchar(256) | 账号 |
| avatarUrl | varchar(1024) | 用户头像 URL |
| gender | tinyint | 性别 |
| userPassword | varchar(512) | 密码（加密存储） |
| phone | varchar(128) | 电话 |
| email | varchar(512) | 邮箱 |
| userStatus | int | 状态（0-正常） |
| createTime | datetime | 创建时间 |
| updateTime | datetime | 更新时间 |
| isDelete | tinyint | 逻辑删除标识（0-未删除，1-已删除） |
| userRole | int | 用户角色（0-普通用户，1-管理员，2-VIP） |
| planetCode | varchar(512) | 星球编号 |

---

## 快速开始

### 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 5.7+ 或 8.0+

### 安装步骤

#### 1. 克隆项目
```bash
git clone <repository-url>
cd user_center
```

#### 2. 创建数据库
```bash
mysql -u root -p
source sql/create_table.sql
```

#### 3. 配置数据库连接

修改 `src/main/resources/application-dev.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/user_manage
    username: your_username
    password: your_password
```

#### 4. 编译打包

**开发环境**:
```bash
mvn clean package -P dev
```

**测试环境**:
```bash
mvn clean package -P test
```

**生产环境**:
```bash
mvn clean package -P prod
```

#### 5. 运行项目
```bash
java -jar target/user_center-0.0.1-SNAPSHOT.jar
```

或者使用 Maven 直接运行：
```bash
mvn spring-boot:run -P dev
```

#### 6. 访问接口

项目启动后，访问 `http://localhost:8080/api` 即可调用接口。

---

## 多环境配置

项目支持三种环境配置，通过 Maven Profile 在打包时指定：

| 环境 | Profile ID | 配置文件 | 说明 |
|------|-----------|---------|------|
| 开发环境 | dev | application-dev.yml | 默认环境 |
| 测试环境 | test | application-test.yml | 测试服务器 |
| 生产环境 | prod | application-prod.yml | 生产服务器 |

### 切换环境

打包时指定环境：
```bash
mvn clean package -P <环境ID>
```

运行时覆盖环境（优先级更高）：
```bash
java -jar user_center.jar --spring.profiles.active=dev
```

---

## 安全特性

### 1. 密码加密
用户密码经过加密后存储，确保数据安全。

### 2. 数据脱敏
通过 `getSafetyUser()` 方法对用户敏感信息（如密码）进行脱敏处理，避免泄露。

### 3. 权限控制
- 用户搜索和删除接口需要管理员权限
- 通过 Session 验证用户身份和角色

### 4. Session 管理
- 登录后将用户信息存入 Session
- 注销时清除 Session
- 可配置 Session 超时时间

### 5. 统一异常处理
全局异常处理器捕获并统一处理各类异常，返回标准化的错误响应。

---

## 快速开始

### 代码规范
- 使用 Lombok 简化 Getter/Setter
- 遵循 RESTful API 设计规范
- 统一使用 `BaseResponse` 封装响应
- 业务异常使用 `BusinessException`

### 提交规范
- feat: 新功能
- fix: 修复 bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构
- test: 测试相关
- chore: 构建过程或辅助工具的变动

---

## 常见问题

### 1. 编译报错找不到包
确保使用 JDK 17+，并执行 `mvn clean install` 重新构建。

### 2. 数据库连接失败
检查 `application-dev.yml` 中的数据库配置是否正确，确保 MySQL 服务已启动。

### 3. Session 失效
检查 Session 超时时间配置，默认为 81264ms。

### 4. 权限不足
确保当前登录用户具有管理员角色（userRole = 1）。

---

## 许可证

本项目仅供学习参考使用。

---

## 联系方式

如有问题或建议，欢迎联系。
