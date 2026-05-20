# 编码约定

## 语言规则（全中文）

**所有代码、文档和注释均使用中文**。这是 hello-jvm-language 项目的强制约定。

### 适用范围
- 类名、方法名、变量名
- 代码注释（单行、多行、块）
- Javadoc、KDoc、Scaladoc、Groovydoc 文档
- 提交信息、PR 描述、代码审查
- 文档、日志输出、配置说明

### 示例

**正确示例**：
```java
// 获取用户列表
public List<User> getUserList() {
    log.info("获取用户列表入参: {}", pageNum);
    long startTime = System.currentTimeMillis();
    List<User> result = userMapper.selectList();
    long costTime = System.currentTimeMillis() - startTime;
    log.info("获取用户列表返回: result={}, 耗时: {}ms", result.size(), costTime);
    return result;
}
```

**错误示例**（勿用）：
```java
// Get user list (❌ 英文注释)
public List<User> getUserList() {
    List<User> users = userMapper.selectList(); // selecting from DB (❌ 英文)
    return users;
}
```

### 提交信息格式
```
[Feat] 添加订单管理接口
[Fix] 修复数据源路由在高并发下的问题  
[Docs] 更新 API 使用文档
[Test] 补充用户服务单元测试
```

### 验证清单
- [ ] 代码注释全部为中文？
- [ ] 提交信息是否为中文？
- [ ] Javadoc/KDoc 是否为中文？
- [ ] 日志信息是否为中文？
- [ ] 文件编码是否为 UTF-8？

### 相关链接
- 详细指南：`.github/copilot-instructions.md`
- 编译顺序：见下文

## 多语言编译顺序（重要）

1. **Scala** → process-resources 阶段
2. **Groovy** → generate-stubs + compile 阶段
3. **Kotlin** → compile 阶段
4. **Java** → compile 阶段（可引用其他所有语言）

## 数据源路由

- **MySQL (主库)**：写操作 → `@DS("master")`
- **PostgreSQL (从库)**：读操作 → `@DS("slave")`

## 包结构

```
com.zjw.entity      # 实体
com.zjw.mapper      # Mapper（按数据源分为 master/ 和 slave/）
com.zjw.service     # Service
com.zjw.controller  # Controller
com.zjw.util        # 工具类
com.zjw.config      # 配置
```

## 约束条件

- ❌ 禁止修改：`target/`、`libs/`、`logs/` 目录
- ✅ 修改数据库逻辑时：同步更新 `scripts/` 中的 SQL 脚本
- ✅ 提交前：运行 `mvn validate && mvn test`
