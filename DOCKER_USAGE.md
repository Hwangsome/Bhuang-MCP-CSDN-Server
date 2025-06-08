# 🐳 Docker 部署使用指南

## 📋 镜像信息
- **Registry**: `crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com`
- **命名空间**: `bhuang-repo`
- **镜像名**: `bhuang-mcp-csdn-server`

## 🚀 快速开始

### 1. 登录阿里云Registry
```bash
docker login --username=黄帅啊 crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com
```

### 2. 拉取镜像
```bash
# 拉取最新版本
docker pull crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest

# 或拉取指定版本
docker pull crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:0.0.1-SNAPSHOT-abc1234
```

## 🏃 运行方式

### 方式1: 直接运行 Docker 容器

#### 基本运行
```bash
docker run -d --name bhuang-mcp-server -p 8080:8080 \
  crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest
```

#### 完整配置运行
```bash
docker run -d \
  --name bhuang-mcp-server \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xms512m -Xmx1024m -Dspring.profiles.active=prod" \
  -e TZ="Asia/Shanghai" \
  --restart unless-stopped \
  -v ./logs:/app/logs \
  crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest
```

### 方式2: 使用 Docker Compose

1. 下载配置文件
```bash
curl -O https://raw.githubusercontent.com/your-username/Bhuang-AI-MCP-Server/master/docker-compose.yml
```

2. 启动服务
```bash
docker-compose up -d
```

3. 查看日志
```bash
docker-compose logs -f
```

4. 停止服务
```bash
docker-compose down
```

### 方式3: 一键部署脚本

1. 下载脚本
```bash
curl -O https://raw.githubusercontent.com/your-username/Bhuang-AI-MCP-Server/master/deploy.sh
chmod +x deploy.sh
```

2. 执行部署
```bash
./deploy.sh
```

## 📊 监控和维护

### 查看容器状态
```bash
docker ps
docker ps -a  # 包括停止的容器
```

### 查看应用日志
```bash
docker logs bhuang-mcp-server
docker logs -f bhuang-mcp-server  # 实时查看
```

### 进入容器调试
```bash
docker exec -it bhuang-mcp-server /bin/bash
```

### 健康检查
```bash
curl http://localhost:8080/actuator/health
```

### 应用访问
- **主页**: http://localhost:8080
- **健康检查**: http://localhost:8080/actuator/health
- **Swagger API文档**: http://localhost:8080/swagger-ui.html

## 🔧 常用操作

### 更新应用
```bash
# 停止现有容器
docker stop bhuang-mcp-server
docker rm bhuang-mcp-server

# 拉取最新镜像
docker pull crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest

# 重新启动
docker run -d --name bhuang-mcp-server -p 8080:8080 \
  crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest
```

### 清理资源
```bash
# 停止并删除容器
docker stop bhuang-mcp-server
docker rm bhuang-mcp-server

# 删除镜像
docker rmi crpi-wzl2k45d0lxbiagj.cn-shenzhen.personal.cr.aliyuncs.com/bhuang-repo/bhuang-mcp-csdn-server:latest

# 清理无用的镜像和容器
docker system prune -f
```

## 🐛 故障排查

### 容器启动失败
```bash
# 查看错误日志
docker logs bhuang-mcp-server

# 检查端口占用
lsof -i :8080
netstat -tulpn | grep 8080
```

### 应用无法访问
1. 检查容器是否运行: `docker ps`
2. 检查端口映射: `docker port bhuang-mcp-server`
3. 检查防火墙设置
4. 查看应用日志: `docker logs bhuang-mcp-server`

### 性能问题
```bash
# 查看资源使用情况
docker stats bhuang-mcp-server

# 调整内存限制
docker run --memory=1g --cpus=1 ...
```

## 📝 环境变量说明

| 变量名 | 默认值 | 说明 |
|--------|--------|------|
| `JAVA_OPTS` | `-Xms512m -Xmx1024m` | JVM启动参数 |
| `PARAMS` | `--server.port=8080` | 应用启动参数 |
| `TZ` | `Asia/Shanghai` | 时区设置 |

## 📞 支持与反馈

如有问题或建议，请提交 Issue 或联系维护团队。 