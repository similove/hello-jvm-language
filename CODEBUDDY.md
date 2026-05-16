# CODEBUDDY.md

本文件为 CodeBuddy Code 在本仓库中工作时提供指引。

## 构建命令

本项目使用 Apache Maven 构建。项目中没有 Maven Wrapper 脚本（`mvnw`/`mvnw.cmd`），需确保 `mvn` 已在 PATH 中。

```bash
mvn clean package              # 完整构建：编译所有语言、运行测试、输出 JAR 及依赖到 libs/
mvn clean package -DskipTests  # 构建但跳过测试
mvn compile                    # 仅编译（四种 JVM 语言）
mvn test                       # 运行所有测试
mvn test -Dtest=类名           # 运行单个测试类
mvn clean                      # 清理构建产物（同时删除 target/ 和 libs/）
```

构建输出到 `libs/` 目录（非默认的 `target/`），通过 pom.xml 中的 `${build.libs.path}` 配置。

## 项目架构

**多 JVM 语言 Spring Boot 4.0 应用**（Java 21）。构建系统从各自的源码目录编译四种 JVM 语言：

| 语言 | 源码目录 | 编译插件 | 编译阶段 |
|------|---------|---------|---------|
| Scala | `src/main/scala/` | `scala-maven-plugin` 4.9.10 | `process-resources`（最先编译） |
| Groovy | `src/main/groovy/` | `gmavenplus-plugin` 1.7.1 | 默认阶段 |
| Kotlin | `src/main/kotlin/` | `kotlin-maven-plugin` 2.2.20 | `compile` |
| Java | `src/main/java/` | `maven-compiler-plugin` 3.1 | `compile`（最后编译） |

**编译顺序很重要**：Scala 最先编译（`process-resources` 阶段），然后依次是 Groovy、Kotlin、Java。这意味着 Scala 代码不能引用 Java/Kotlin/Groovy 的类，而 Java 可以引用所有其他语言的类。

`build-helper-maven-plugin` 将所有四个 `src/main/*` 目录注册为源码根目录。

### 基础包名

`com.zjw` — Spring 组件扫描覆盖此包及其子包。

### 主要依赖

- **Spring Boot** 4.0.6（基于 Spring Framework 7.0）
- **Spring Boot Starter Web** — 内嵌 Tomcat 11
- **Log4j 2**（通过 `spring-boot-starter-log4j2` 引入）— 日志实现，已排除 Spring Boot 默认的 Logback
- **Scala 3** 3.8.0（`scala3-library_3`）— 注意 Scala 3 不再需要 `scala-reflect`
- **Kotlin** 2.2.20（`kotlin-stdlib`）
- **Groovy** 5.0.6（`groovy-all`）
- **Druid** 1.2.28（核心包，不使用 starter 以避免 Spring Boot 4 兼容问题）
- **dynamic-datasource** 4.5.0（`dynamic-datasource-spring-boot3-starter`）— 多数据源切换，使用 `@DS` 注解
- **MyBatis-Plus** 3.5.16（`mybatis-plus-spring-boot3-starter` + `mybatis-plus-jsqlparser`）— ORM 框架
- **MySQL** 驱动 9.7.0（`com.mysql:mysql-connector-j`）
- **PostgreSQL** 驱动 42.7.11
- **xxl-job** 3.4.0（`xxl-job-core`）— 分布式任务调度客户端
- **Lettuce** — Redis 客户端
- **Kafka Clients** 3.9.0 — Kafka 生产者/消费者
- **Nacos Client** 2.2.0（纯净版 SDK，classifier 为 `pure`）— 服务发现/配置管理
- **Jackson** — 由 Spring Boot 4 管理（3.x 版本）
- **Lombok** 1.18.38（provided 作用域）
- **Guava** 31.1-jre、**Commons Lang 3** 3.8

### 多数据源架构

使用 `dynamic-datasource-spring-boot3-starter` 管理，配置两个数据源：

| 数据源名 | 数据库 | 用途 |
|---------|--------|------|
| `master`（默认） | MySQL | 主库 |
| `slave` | PostgreSQL | 从库 |

- `@DS("master")` / `@DS("slave")` 在 Mapper 或 Service 层切换数据源
- `primary: master` 表示未指定 `@DS` 时默认使用 master 数据源

### 代码结构

```
com.zjw/
├── config/           — 配置类（MybatisPlusConfig、XxlJobConfig）
├── controller/       — REST 控制器
├── entity/           — MyBatis-Plus 实体类
├── mapper/
│   ├── master/       — 主库 Mapper 接口（@DS("master")）
│   └── slave/        — 从库 Mapper 接口（@DS("slave")）
├── service/
│   └── impl/         — Service 实现
└── job/              — xxl-job 任务处理器（@XxlJob）
```

Mapper XML 文件位于 `src/main/resources/mapper/{master,slave}/`，由 `mybatis-plus.mapper-locations` 配置扫描。

### Spring Boot 4 兼容性注意事项

- Druid 使用核心包（`druid`）而非 `druid-spring-boot-3-starter`，因为 starter 的自动配置类引用了 Spring Boot 3 的 `DataSourceProperties`，在 Spring Boot 4 中路径已变更
- `log4j-api-scala` 已移除（基于 Scala 2.13，不兼容 Scala 3）
- `jackson-module-scala` 已移除（基于 Scala 2.13，不兼容 Scala 3）
- dynamic-datasource 和 mybatis-plus 的自动配置会产生 WARN（`DataSourceAutoConfiguration not present`），不影响运行

### 运行环境要求

- **JDK 25**：项目 `java.version=25`，Spring Boot 4 要求 Java 21+。本机 JDK 25 路径：`D:/Program Files/jdk/jdk-25.0.1`
- 运行命令：`JAVA_HOME="D:/Program Files/jdk/jdk-25.0.1" mvn spring-boot:run`
- 需要 MySQL 和 PostgreSQL 数据库实例运行，数据库连接信息在 `application.yaml` 中配置
- xxl-job 需要调度中心（admin）运行，地址在 `application.yaml` 中配置

### 测试配置

- 框架：Spring Boot Test（通过 `spring-boot-starter-test` 引入 JUnit 5）
- Surefire 包含模式：`**/Test*.java`、`**/*Test.java`、`**/*TestCase.java`、`**/*Suite.java`
- 目前仅配置了 Java 测试模式；Scala/Groovy/Kotlin 的测试目录尚未创建
