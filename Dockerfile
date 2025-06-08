FROM openjdk:17-jdk-slim

MAINTAINER bhuang

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/Bhuang-AI-MCP-Server-0.0.1-SNAPSHOT.jar /bhuang-mcp-server.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /bhuang-mcp-server.jar $PARAMS"] 