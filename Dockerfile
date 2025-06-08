# 多阶段构建的 Dockerfile
# 第一阶段：构建应用
FROM maven:3.9.4-openjdk-17-slim AS builder

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和 .mvn 文件
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# 下载依赖（利用Docker缓存）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src src

# 构建应用
RUN mvn clean package -DskipTests

# 第二阶段：运行应用
FROM openjdk:17-jre-slim

# 设置工作目录
WORKDIR /app

# 创建非root用户
RUN groupadd -r appuser && useradd -r -g appuser appuser

# 从构建阶段复制jar文件
COPY --from=builder /app/target/*.jar app.jar

# 修改文件所有者
RUN chown appuser:appuser app.jar

# 切换到非root用户
USER appuser

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-jar", "/app/app.jar"] 