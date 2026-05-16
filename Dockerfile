# 第 1 阶段：构建自定义 JRE (仅包含运行所需的模块)
FROM eclipse-temurin:25-jdk-alpine AS jre-build

RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base,java.logging,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.sql,java.xml,jdk.unsupported,java.scripting,java.net.http,jdk.management.agent,java.compiler \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress 2 \
         --output /javaruntime

# 第 2 阶段：Maven 构建
FROM maven:3.9.11-eclipse-temurin-25-alpine AS build
WORKDIR /workspace

# 利用 Maven 依赖缓存：先拷贝 pom.xml
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

# 拷贝源码并打包 (pom.xml 配置了将 jar 和依赖都输出到 libs 目录)
COPY src ./src
RUN mvn -q -DskipTests package

# 第 3 阶段：最终运行环境
FROM alpine:3.21 AS runtime
WORKDIR /app

# 安装 tini (用于正确处理 PID 1 信号和回收僵尸进程)
RUN apk add --no-cache tini

# 拷贝自定义 JRE
COPY --from=jre-build /javaruntime /opt/java
ENV JAVA_HOME=/opt/java
ENV PATH="$JAVA_HOME/bin:$PATH"

# 安全性：非 root 运行
RUN addgroup -S app && adduser -S app -G app

# 拷贝运行所需内容：libs 目录包含了项目 jar 和所有依赖
COPY --from=build /workspace/libs ./libs

# 准备日志目录并赋权
RUN mkdir -p /app/logs && chown -R app:app /app

USER app
EXPOSE 8080

# 优化启动参数：
# 1. 使用 tini 作为 init 进程
# 2. -Djava.security.egd=file:/dev/./urandom: 解决容器环境下熵池不足导致的启动卡顿
ENTRYPOINT ["/sbin/tini", "--", "java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx512m", "-Xms256m", "-cp", "/app/libs/*", "com.zjw.HelloJvmLanguageApplication"]
