# GitHub Copilot 开发指南

本文档为 Copilot 提供 hello-jvm-language 项目的架构、构建系统和关键约定的指导。

## 构建、测试和代码检查

### 核心命令
```bash
# 完整构建（输出到 libs/）
mvn package

# 构建时跳过测试
mvn package -DskipTests

# 运行所有测试
mvn test

# 运行指定测试类
mvn test -Dtest=SysUserServiceTest
mvn test -Dtest=BookServiceTest
mvn test -Dtest=PersonInfoServiceTest
mvn test -Dtest=AddressServiceTest

# 检查所有四种语言的代码规范（在 validate 阶段运行）
mvn validate

# 本地启动应用
mvn spring-boot:run

# 清理构建产物和 libs/
mvn clean
```

### 各语言的代码检查工具
四种语言各有独立的检查工具，在 `mvn validate` 阶段自动执行：

| 语言   | 检查工具   | 配置文件            | 跳过选项             |
|--------|-----------|------------------|----------------------|
| Java   | Checkstyle | `checkstyle.xml`       | `-Dcheckstyle.skip=true` |
| Kotlin   | ktlint     | （内置规则）       | `-Dktlint.skip=true`     |
| Groovy   | CodeNarc   | `codenarc.xml`         |                          |
| Scala    | Scalastyle | `scalastyle-config.xml` |                          |

- Checkstyle 支持行内抑制：`// checkstyle.off: RuleName` … `// checkstyle.on: RuleName`
- 跳过检查进行构建：`mvn package -Dcheckstyle.skip=true -Dktlint.skip=true`（仅用于调试，不推荐）

## 架构概览

### 多语言策略
这是一个**单模块 Maven 项目**，Java、Kotlin、Scala 和 Groovy 共存，每种语言负责一个特定领域。每种语言拥有完整的垂直切片（entity → mapper → service → controller）：

| 语言   | 领域        | 数据源             | 主要实体 | 测试类 |
|--------|-------------|------------------|---------|---------|
| Java   | SysUser、Order | MySQL (主库) + PostgreSQL (从库) | `SysUser`、`Order` | `SysUserServiceTest` |
| Kotlin | Book        | MySQL (主库) | `Book` | `BookServiceTest` |
| Scala  | PersonInfo  | PostgreSQL (从库) | `PersonInfo` | `PersonInfoServiceTest` |
| Groovy | Address     | PostgreSQL (从库) | `Address` | `AddressServiceTest` |

### 编译顺序（关键）
Maven 按固定顺序编译各语言。跨语言引用必须遵守此顺序：

1. **Scala**（`src/main/scala`）—— `scala-maven-plugin` 在 process-resources 阶段
2. **Groovy**（`src/main/groovy`）—— `gmavenplus-plugin` 的 generate-stubs + compile 阶段（生成桩文件供 Java 引用）
3. **Kotlin**（`src/main/kotlin`）—— `kotlin-maven-plugin` 在 compile 阶段
4. **Java**（`src/main/java`）—— `maven-compiler-plugin` 在 compile 阶段（可引用其他所有语言）

**原则**：基础设施用 Java 编写，特定领域用对应语言编写。

### 目录结构
```
src/main/
  java/com/zjw/          # Java: SysUser、Order、config、controller、utils
  kotlin/com/zjw/        # Kotlin: Book 领域
  scala/com/zjw/         # Scala: PersonInfo 领域
  groovy/com/zjw/        # Groovy: Address 领域
  resources/
    application.yaml     # 主配置文件（数据源、MyBatis、XXL-Job、SpringDoc）
    mapper/master/       # MySQL 写操作的 XML Mapper
    mapper/slave/        # PostgreSQL 读操作的 XML Mapper
    log4j2.xml          # Log4j2 配置（非 Logback）

src/test/
  java/com/zjw/          # Java 测试
  kotlin/com/zjw/        # Kotlin 测试
  scala/com/zjw/         # Scala 测试
  groovy/com/zjw/        # Groovy 测试
  resources/
    application.yaml     # 测试配置（覆盖主配置、禁用严格模式和 XXL-Job）

scripts/                 # SQL 初始化脚本（查看 00_README.sql 了解执行顺序）
libs/                    # Maven 输出目录（非 target/）
```

## 关键约定

### 编码语言
- **所有代码、文档、注释均使用中文**
- 包括类名、方法名、变量名、提交信息、代码审查注释等

### 数据库和数据源
- **主库**（MySQL）：写操作，默认数据源
- **从库**（PostgreSQL）：读操作
- 通过 `@DS("master")` / `@DS("slave")`（来自 `dynamic-datasource-spring-boot3-starter`）在**方法级**进行路由
- MyBatis-Plus 自动扫描：`@MapperScan("com.zjw.mapper")` 查找所有子目录
- XML Mapper 自动发现：`classpath*:/mapper/**/*.xml`（见 application.yaml）
- 测试环境禁用严格模式（`strict: false`）以避免测试中的数据源不匹配错误

### 包和配置
- 所有代码：`com.zjw.*`
- 构建输出：`libs/`（非 `target/`）—— 通过 Maven 程序集插件配置
- Docker 入口：`java -cp /app/libs/* com.zjw.HelloJvmLanguageApplication`
- 日志格式：Log4j2（非 Logback）—— 见 `src/main/resources/log4j2.xml`
- Java 版本：**JDK 25**

### Controller 日志模式
Controller 方法应包含详细的入参和返回日志，以及执行耗时统计：
```java
log.info("方法名 入参: {}", 参数);
long startTime = System.currentTimeMillis();
// 业务逻辑
long costTime = System.currentTimeMillis() - startTime;
log.info("方法名 返回: result={}, 耗时: {}ms", 结果, costTime);
```

### 配置文件
- `src/main/resources/application.yaml` —— 生产/默认配置（数据源、MyBatis、XXL-Job、SpringDoc、时区：Asia/Shanghai）
- `src/test/resources/application.yaml` —— 测试配置覆盖（连接池较小、`strict: false`、`xxl.job.enabled: false`）
- 根目录：`checkstyle.xml`、`codenarc.xml`、`scalastyle-config.xml` —— 各语言代码规范配置

### 数据库初始化
**运行测试或应用前**，按以下顺序初始化数据库（MySQL 用户：`root/12345678`，PostgreSQL 用户：`postgres/12345678`）：

```bash
# Windows
mysql -u root -p12345678 test < scripts\01_mysql_init.sql
psql -U postgres -d test -f scripts\02_postgres_init.sql
psql -U postgres -d test -f scripts\03_postgres_person_info_init.sql
psql -U postgres -d test -f scripts\04_postgres_address_init.sql
mysql -u root -p12345678 test < scripts\03_mysql_book_init.sql
```

### XXL-Job 任务调度
- 通过 `@ConditionalOnProperty(name = "xxl.job.enabled", havingValue = "true")` 控制
- 执行器配置：appname=`hello-jvm-language`、port=`9999`、logpath=`./logs/xxl-logs`
- **在测试环境禁用**（`src/test/resources/application.yaml`）以避免副作用

### API 文档
- **Swagger UI**：`http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**：`http://localhost:8080/api-docs`
- 框架：SpringDoc OpenAPI 2.8.0

### 容器和依赖
- **Web 服务器**：Jetty（非 Tomcat）—— pom.xml 中显式排除 Tomcat
- **日志框架**：Log4j2（非 Logback）—— 从 Spring 依赖中排除 Logback

### 测试
- 测试类以 `*Test` 后缀结尾
- 在相应的源结构中放置测试：Java 测试放在 `src/test/java` 等
- 提交前始终运行 `mvn test`
- 常见测试类：`SysUserServiceTest`、`BookServiceTest`、`PersonInfoServiceTest`、`AddressServiceTest`

### 版本控制
- 提交信息前缀风格：`[Init]`、`[Fix]`、`[Feat]` 等 + 简短描述
- PR 应文档化：改动内容、数据库/配置影响、验证命令（如 `mvn test` 或指定测试类）

### 禁止修改的文件
- `target/`、`libs/`、`logs/` —— 生成文件，禁止提交
- 添加数据库逻辑时，更新 `scripts/` 中对应的 SQL 脚本
- 修改数据源或数据库架构时，确保 MySQL 和 PostgreSQL 初始化脚本同步更新

## 常见工作流

### 添加新功能（示例：Java 领域）
1. 在 `src/main/java/com/zjw/entity/` 创建实体
2. 在 `src/main/java/com/zjw/mapper/master/` 创建 Mapper 接口，在 `src/main/resources/mapper/master/` 创建 XML
3. 在 `src/main/java/com/zjw/service/` 创建 Service 接口和实现
4. 在 `src/main/java/com/zjw/controller/` 创建 Controller，包含入参和返回日志
5. 在 `src/test/java/com/zjw/service/*ServiceTest.java` 创建测试
6. 如果数据库架构变化，更新 `scripts/`
7. 运行 `mvn validate`（代码检查）
8. 运行 `mvn test -Dtest=NewServiceTest`
9. 运行完整 `mvn test` 确保无回归

### 添加新的 Kotlin/Scala/Groovy 功能
- 遵循相同模式，但将代码放在对应语言的目录中
- 如需跨语言依赖，确保 Java 引用其他语言（由于编译顺序）
- 检查工具在 validate 阶段自动运行

### 本地运行测试
```bash
# 单个测试类
mvn test -Dtest=SysUserServiceTest

# 所有测试
mvn test

# 构建并运行测试
mvn package
```

### 构建问题调试
```bash
# 跳过检查（仅用于快速迭代，提交时不推荐）
mvn package -Dcheckstyle.skip=true -Dktlint.skip=true

# 查看详细输出
mvn clean package -X
```

## 技术栈

- Spring Boot 4.0.6（Spring Framework 7.0，需要 JDK 21+）
- MyBatis-Plus 3.5.16（ORM）
- Dynamic-DataSource 4.5.0（多数据源路由）
- Druid 1.2.28（连接池）
- XXL-Job 3.4.0（任务调度）
- SpringDoc OpenAPI 2.8.0（Swagger/OpenAPI 3.0 文档）
- Jetty（内嵌 Servlet 容器）
- Log4j2（日志框架）
- JDK 25

## 相关文档

更多信息请参考：
- `README.md` —— 项目概览、配置、运行说明
- `CLAUDE.md` —— Claude Code 的详细架构指南
- `AGENTS.md` —— 多代理开发指南
- `CODEBUDDY.md` —— Codebuddy 专用指南
- `GEMINI.md` —— Gemini CLI 指南
