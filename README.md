# hello-jvm-language

一个基于 Spring Boot 4 的多语言 JVM 示例项目。Java、Kotlin、Scala、Groovy 共用同一个 Maven 构建，演示多语言混合开发、MyBatis-Plus、动态数据源、OpenAPI 文档、XXL-JOB，以及一组常用基础工具类。

## 项目特点

- 单模块 Maven 工程，统一由 `pom.xml` 管理
- Java、Kotlin、Scala、Groovy 混合开发
- MySQL 和 PostgreSQL 双数据源
- MyBatis-Plus 数据访问
- SpringDoc OpenAPI / Swagger UI 接口文档
- XXL-JOB 任务调度示例
- Jetty 作为内嵌容器
- 内置常用工具类：时间、字符串、加解密、MD5、UUID、Base64、Jackson

## 技术栈

- Spring Boot 4.0.6
- Java 25
- Kotlin 2.2.20
- Scala 3.8.0
- Groovy 5.0.6
- MyBatis-Plus 3.5.16
- Dynamic Datasource 4.5.0
- XXL-JOB 3.4.0
- SpringDoc OpenAPI 2.8.0

## 目录结构

```text
src/main/java      Java 代码
src/main/kotlin    Kotlin 代码
src/main/scala     Scala 代码
src/main/groovy    Groovy 代码
src/main/resources 配置文件、SQL Mapper、日志配置
src/test/...       各语言对应测试代码
scripts/           数据库初始化脚本
```

## 运行环境

- JDK 25
- Maven 3.9+
- MySQL
- PostgreSQL

默认数据库配置位于 [`src/main/resources/application.yaml`](src/main/resources/application.yaml)：

- MySQL: `jdbc:mysql://localhost:3306/test`
- PostgreSQL: `jdbc:postgresql://localhost:5432/test`
- MySQL 账号: `root / 12345678`
- PostgreSQL 账号: `postgres / 12345678`

测试环境配置位于 [`src/test/resources/application.yaml`](src/test/resources/application.yaml)，与主配置保持一致。

## 数据库初始化

启动应用或执行测试前，请先按顺序初始化数据库。

### Windows

```powershell
mysql -u root -p12345678 test < scripts\01_mysql_init.sql
psql -U postgres -d test -f scripts\02_postgres_init.sql
```

### Linux / macOS

```bash
mysql -u root -p12345678 test < scripts/01_mysql_init.sql
psql -U postgres -d test -f scripts/02_postgres_init.sql
```

`scripts/00_README.sql` 里也写了脚本执行顺序说明。

## 构建与运行

### 打包

```bash
mvn package
```

打包产物会输出到 `libs/`。

### 运行测试

```bash
mvn test
```

只运行指定测试类：

```bash
mvn test -Dtest=SysUserServiceTest
```

### 本地启动

```bash
mvn spring-boot:run
```

默认情况下应用监听 `8080` 端口，除非你在配置中另外指定。

### 清理

```bash
mvn clean
```

## 接口入口

项目集成了 SpringDoc OpenAPI，启动后可直接访问：

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

### 主要示例接口

- `GET /user/list`
- `GET /user/page`
- `GET /user/{id}`
- `GET /user/username/{username}`
- `POST /user`
- `PUT /user/{id}`
- `DELETE /user/{id}`

- `GET /order/list`

- `GET /book/list`
- `GET /book/page`
- `GET /book/{id}`
- `GET /book/name/{name}`
- `POST /book`
- `PUT /book/{id}`
- `DELETE /book/{id}`

- `GET /person-info/list`
- `GET /person-info/page`
- `GET /person-info/{id}`
- `GET /person-info/name/{name}`
- `POST /person-info`
- `PUT /person-info/{id}`
- `DELETE /person-info/{id}`

- `GET /address/list`
- `GET /address/page`
- `GET /address/{id}`
- `GET /address/name/{name}`
- `POST /address`
- `PUT /address/{id}`
- `DELETE /address/{id}`

- `GET /kotlin/hello`
- `GET /kotlin/info`
- `GET /scala/hello`
- `GET /scala/info`
- `GET /groovy/hello`
- `GET /groovy/info`

## XXL-JOB

项目内置了 XXL-JOB 配置，默认执行器配置如下：

- `appname`: `hello-jvm-language`
- `port`: `9999`
- `logpath`: `./logs/xxl-logs`

如果要接入调度中心，需要在 [`src/main/resources/application.yaml`](src/main/resources/application.yaml) 中补充 `xxl.job.admin.addresses`。

## 常用工具类

项目已经补充了一组基础工具类，位于 `src/main/java/com/zjw/util/`：

- `JacksonUtil`
  - `toJson(...)`
  - `toPrettyJson(...)`
  - `writeValueAsBytes(...)`
  - `parseObject(...)`
  - `parseArray(...)`
  - `parseList(...)`
  - `readTree(...)`
  - `convertValue(...)`
- `TimeUtil`
  - `now()`
  - `format(...)`
  - `parseLocalDateTime(...)`
  - `parseLocalDate(...)`
  - `addDays(...)`
  - `addHours(...)`
  - `toEpochMilli(...)`
  - `fromEpochMilli(...)`
- `StringUtil`
  - `isBlank(...)`
  - `defaultIfBlank(...)`
  - `trimToNull(...)`
  - `join(...)`
  - `splitAndTrim(...)`
  - `mask(...)`
- `CryptoUtil`
  - `aesEncrypt(...)`
  - `aesDecrypt(...)`
  - `sha256Hex(...)`
  - `md5Hex(...)`
- `Md5Util`
  - `md5(...)`
- `UuidUtil`
  - `randomUuid()`
  - `compactUuid()`
- `Base64Util`
  - `encode(...)`
  - `decode(...)`
  - `urlEncode(...)`
  - `urlDecode(...)`

这些工具类都配有基础单测，便于后续扩展和回归验证。

## 开发说明

- 主代码按语言分目录存放，测试代码也按同样结构组织
- Mapper XML 位于 `src/main/resources/mapper/`
- 不要修改 `target/`、`libs/`、`logs/` 中的生成文件
- 如果改动数据库访问逻辑，建议同步更新 `scripts/` 中的初始化脚本

## 许可证

未单独声明许可证，默认按项目仓库约定使用。
