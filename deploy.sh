#!/bin/bash

# 部署配置
REGISTRY="crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com"
NAMESPACE="bhuang-repo"
IMAGE_NAME="bhuang-mcp-csdn-server"
CONTAINER_NAME="bhuang-mcp-server"
PORT="8080"

echo "🚀 开始部署 Bhuang MCP CSDN Server..."

# 登录阿里云Registry
echo "📝 登录阿里云Registry..."
docker login --username=黄帅啊 ${REGISTRY}

# 停止并删除现有容器
echo "🛑 停止现有容器..."
docker stop ${CONTAINER_NAME} 2>/dev/null || true
docker rm ${CONTAINER_NAME} 2>/dev/null || true

# 拉取最新镜像
echo "📦 拉取最新镜像..."
docker pull ${REGISTRY}/${NAMESPACE}/${IMAGE_NAME}:latest

# 启动新容器
echo "🏃 启动新容器..."
docker run -d \
  --name ${CONTAINER_NAME} \
  -p ${PORT}:8080 \
  -e JAVA_OPTS="-Xms512m -Xmx1024m" \
  -e TZ="Asia/Shanghai" \
  --restart unless-stopped \
  ${REGISTRY}/${NAMESPACE}/${IMAGE_NAME}:latest

# 等待应用启动
echo "⏳ 等待应用启动..."
sleep 10

# 检查容器状态
if docker ps | grep -q ${CONTAINER_NAME}; then
    echo "✅ 部署成功！"
    echo "🌐 应用访问地址: http://localhost:${PORT}"
    echo "📊 查看日志: docker logs -f ${CONTAINER_NAME}"
else
    echo "❌ 部署失败，请检查日志: docker logs ${CONTAINER_NAME}"
    exit 1
fi 