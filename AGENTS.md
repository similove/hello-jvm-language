# 仓库规范

## 项目结构与模块组织
这是一个单模块 Spring Boot 4 项目，Java、Kotlin、Scala、Groovy 共用同一个 Maven 构建。主代码位于 `src/main/{java,kotlin,scala,groovy}`，测试代码对应放在 `src/test/{java,kotlin,scala,groovy}`。配置文件和 SQL 脚本分别位于 `src/main/resources`、`src/test/resources` 和 `scripts/`。根目录下的 `checkstyle.xml`、`codenarc.xml`、`scalastyle-config.xml` 是各语言的规范文件。

## 构建、测试与本地运行
- `mvn package`：构建应用并输出产物到 `libs/`。
- `mvn test`：运行全部测试。
- `mvn spring-boot:run`：本地启动服务。
- `mvn clean`：清理构建产物，包括 `libs/`。
- `mvn test -Dtest=SysUserServiceTest`：只跑指定测试类。

启动应用或执行测试前，请先确认 MySQL 和 PostgreSQL 的初始化脚本已按 `scripts/` 中的顺序执行。

## 编码风格与命名
统一使用 UTF-8 和 `pom.xml` 中配置的 Java 25 工具链。包名沿用 `com.zjw`，类名和测试名保持清晰、直观，示例：`SysUserService`、`BookServiceTest`、`PersonInfoServiceImpl`、`AddressController`。Java、Kotlin、Groovy、Scala 分别通过 Maven 插件执行 `checkstyle`、`ktlint`、`codenarc`、`scalastyle` 检查。

## 测试约定
测试按语言分目录放在 `src/test` 下，命名建议统一使用 `*Test` 后缀。提交前至少执行一次 `mvn test`；调试时优先使用单测类定向运行。

## 提交与合并请求
当前 Git 历史使用类似 `[Init]`、`[Fix]` 这样的前缀加简短说明。提交信息建议保持同样风格。Pull Request 需要说明改动内容、数据库或配置影响，并附上验证命令，例如 `mvn test` 或指定测试类。

## 代理工作说明
不要修改 `target/`、`libs/`、`logs/` 中的生成文件。如果改动涉及数据库访问，请同步更新相关 SQL 脚本或测试配置，保证本地环境可复现。
