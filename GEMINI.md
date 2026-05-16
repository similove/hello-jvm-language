# GEMINI.md - hello-jvm-language 项目指南

本文件为 Gemini CLI 提供项目上下文和开发指令。

## 项目概览

`hello-jvm-language` 是一个展示多种 JVM 语言在单个 Spring Boot 4.0 项目中协同工作的示例项目。

### 核心技术栈
- **运行时**: JDK 25 (Spring Boot 4 要求 Java 21+)
- **框架**: Spring Boot 4.0.6 (Spring 7.0)
- **JVM 语言**:
  - **Java 25**: 主力开发语言及入口
  - **Kotlin 2.2.20**: 现代化 JVM 语言支持
  - **Scala 3.8.0**: 函数式/面向对象混合语言
  - **Groovy 5.0.6**: 动态语言支持
- **数据层**:
  - **MyBatis-Plus 3.5.16**: ORM 框架
  - **Druid 1.2.28**: 数据库连接池
  - **dynamic-datasource 4.5.0**: 多数据源管理 (MySQL 主库, PostgreSQL 从库)
- **其他**:
  - **XXL-JOB 3.4.0**: 分布式任务调度
  - **SpringDoc 2.8.0**: OpenAPI 3.0 (Swagger) 文档

## 编译与运行

项目使用 Maven 构建。**注意：编译顺序非常重要**，因为它决定了跨语言调用的可见性。

### 编译顺序
1. **Scala** (`src/main/scala`): 最先编译，不能引用其他语言的类。
2. **Groovy** (`src/main/groovy`)
3. **Kotlin** (`src/main/kotlin`)
4. **Java** (`src/main/java`): 最后编译，可以引用上述所有语言的类。

### 核心命令
- **全量构建**: `mvn clean package` (输出至 `libs/`)
- **跳过测试构建**: `mvn clean package -DskipTests`
- **本地运行**: `mvn spring-boot:run` (确保 JDK 25 环境)
- **清理**: `mvn clean` (同时清理 `target/` 和 `libs/`)

## 开发规范

### 代码风格与实践
1. **日志记录**: 所有 Controller 方法必须包含详细的入参和返回日志，并计算执行耗时。示例：
   ```java
   log.info("methodName 入参: {}", param);
   long startTime = System.currentTimeMillis();
   // 业务逻辑
   long costTime = System.currentTimeMillis() - startTime;
   log.info("methodName 返回: result={}, 耗时: {}ms", result, costTime);
   ```
2. **多数据源使用**: 
   - 默认使用 `master` (MySQL)。
   - 使用 `@DS("slave")` 切换至 PostgreSQL (从库)。
3. **包结构**: 
   - `com.zjw.config`: 配置类
   - `com.zjw.controller`: REST 接口
   - `com.zjw.service`: 业务接口与实现
   - `com.zjw.mapper`: MyBatis Mapper (按数据源分为 `master` 和 `slave` 目录)
   - `com.zjw.entity`: 实体类

### 跨语言调用限制
由于编译顺序限制，编写新功能时请遵循以下原则：
- 如果需要在 Scala 中调用，该代码也必须是 Scala。
- Java 作为最后编译的语言，是集成所有模块的最佳位置。

## 安全与隐私
- **禁止读取配置文件**: 严禁将 `application.yaml`, `application.properties` 或任何包含敏感凭据的文件上传、读取或发送到模型。
- **配置参考**: 数据库和外部服务配置应在本地环境中通过环境变量或外部配置文件进行。
