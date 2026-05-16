# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

多 JVM 语言 Spring Boot 4.0 示例项目，演示 Java、Kotlin、Scala、Groovy 在同一 Maven 构建中共存。使用 Jetty（非 Tomcat）和 Log4j2（非 Logback）。

## 构建与运行命令

```bash
# 构建（输出到 libs/ 目录）
mvn package

# 运行全部测试
mvn test

# 运行单个测试类
mvn test -Dtest=SysUserServiceTest
mvn test -Dtest=BookServiceTest

# 构建时跳过测试
mvn package -DskipTests

# 清理（同时删除 libs/ 目录）
mvn clean

# 本地启动
mvn spring-boot:run
```

## 数据库初始化

应用启动和测试执行前，需确保 MySQL 与 PostgreSQL 均已在本地运行。

```bash
# Windows（在项目根目录执行）
mysql -u root -p12345678 test < scripts\01_mysql_init.sql
psql -U postgres -d test -f scripts\02_postgres_init.sql
psql -U postgres -d test -f scripts\03_postgres_person_info_init.sql
psql -U postgres -d test -f scripts\04_postgres_address_init.sql
mysql -u root -p12345678 test < scripts\03_mysql_book_init.sql
```

数据库凭据写在 `application.yaml` 中：MySQL root/12345678，PostgreSQL postgres/12345678。

## 架构说明

### 语言与领域映射

每种 JVM 语言独立负责一个领域，贯穿 entity → mapper → service → controller 全链路：

| 语言   | 领域            | 数据源              |
|--------|-----------------|---------------------|
| Java   | SysUser、Order  | MySQL 主库、PostgreSQL 从库 |
| Kotlin | Book            | MySQL 主库          |
| Scala  | PersonInfo      | PostgreSQL 从库     |
| Groovy | Address         | PostgreSQL 从库     |

各语言源码根目录通过 `build-helper-maven-plugin` 注册到 Maven：`src/main/java`、`src/main/scala`、`src/main/groovy`、`src/main/kotlin`，测试目录与之对应。

### 双数据源路由

- **master** → MySQL，用于写操作（SysUser、Book）
- **slave** → PostgreSQL，用于读操作（PersonInfo、Order、Address）
- 通过 `dynamic-datasource-spring-boot3-starter` 的 `@DS("master")` / `@DS("slave")` 注解在方法级切换数据源
- MyBatis-Plus `@MapperScan("com.zjw.mapper")` 扫描所有子包（Java Mapper 按数据源分在 `mapper/master/` 下）
- XML Mapper 文件位于 `src/main/resources/mapper/master/` 和 `mapper/slave/`

### 打包说明

Maven 配置将所有 JAR 及依赖输出到 `libs/`（而非默认的 `target/`）。Docker 入口命令为：`java -cp /app/libs/* com.zjw.HelloJvmLanguageApplication`。

### API 文档

启动后访问：Swagger UI `/swagger-ui.html`，OpenAPI JSON `/api-docs`。

### 关键配置文件

- `src/main/resources/application.yaml` — 数据源、MyBatis-Plus、XXL-Job、SpringDoc 配置
- `src/test/resources/application.yaml` — 测试环境覆盖（关闭严格数据源模式、缩减连接池）
- `src/main/resources/log4j2.xml` — 日志配置
