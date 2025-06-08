FROM openjdk:17-jdk-slim

LABEL maintainer="bhuang"

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用 (使用通配符匹配jar文件)
ADD target/*.jar /bhuang-mcp-server.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /bhuang-mcp-server.jar $PARAMS"] 