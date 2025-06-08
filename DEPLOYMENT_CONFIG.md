# 部署配置说明

## GitHub Actions 配置

### 必需的 GitHub Secrets

在 GitHub 仓库的 Settings > Secrets and variables > Actions 中配置以下秘密变量：

#### 阿里云容器镜像服务配置
```
ALIBABA_CLOUD_REGISTRY=registry.cn-hangzhou.aliyuncs.com  # 阿里云镜像仓库地址
ALIBABA_CLOUD_USERNAME=your_username                      # 阿里云镜像仓库用户名
ALIBABA_CLOUD_PASSWORD=your_password                      # 阿里云镜像仓库密码
ALIBABA_CLOUD_NAMESPACE=your_namespace                    # 阿里云镜像仓库命名空间
```

### 阿里云容器镜像服务设置步骤

1. **登录阿里云控制台**
   - 访问 [阿里云容器镜像服务](https://cr.console.aliyun.com/)

2. **创建命名空间**
   - 在容器镜像服务控制台创建一个命名空间
   - 记下命名空间名称，用于 `ALIBABA_CLOUD_NAMESPACE` 配置

3. **创建镜像仓库**
   - 在对应命名空间下创建镜像仓库
   - 仓库名称建议为: `bhuang-ai-mcp-server`

4. **获取访问凭证**
   - 访问凭证管理页面
   - 设置Registry登录密码
   - 用户名通常是阿里云账号的全名
   - 密码是刚设置的Registry登录密码

### 工作流触发条件

- **Push到主分支**: `main` 或 `master` 分支有新提交时触发
- **Pull Request**: 向主分支创建PR时触发（仅构建，不推送）

### 工作流步骤说明

1. **代码检出**: 拉取最新代码
2. **JDK设置**: 安装并配置 JDK 17
3. **Maven构建**: 执行 `mvn clean install -DskipTests`
4. **运行测试**: 执行 `mvn test`
5. **Docker设置**: 配置 Docker Buildx（支持多架构构建）
6. **登录阿里云**: 使用配置的凭证登录阿里云镜像仓库
7. **版本管理**: 自动生成版本标签（项目版本+Git短哈希）
8. **镜像构建**: 构建多架构Docker镜像并推送到阿里云

### 生成的镜像标签

- `latest`: 最新版本
- `{project.version}-{git-short-sha}`: 版本化标签，例如：`0.0.1-SNAPSHOT-abc1234`

## Dockerfile 说明

### 多阶段构建优势
- **第一阶段**: 使用Maven镜像构建应用
- **第二阶段**: 使用轻量级JRE镜像运行应用
- **优化效果**: 最终镜像体积更小，安全性更高

### 安全特性
- 使用非root用户运行应用
- 最小化运行时环境
- 健康检查配置

### 端口配置
- 默认暴露端口: `8080`
- 健康检查端点: `/actuator/health`

## 本地构建测试

### 构建Docker镜像
```bash
docker build -t bhuang-ai-mcp-server:local .
```

### 运行容器
```bash
docker run -p 8080:8080 bhuang-ai-mcp-server:local
```

### 检查健康状态
```bash
curl http://localhost:8080/actuator/health
```

## 故障排除

### 常见问题
1. **Maven构建失败**: 检查pom.xml依赖配置
2. **Docker登录失败**: 验证阿里云凭证配置
3. **镜像推送失败**: 检查网络连接和仓库权限
4. **健康检查失败**: 确认应用正常启动且端口正确

### 日志查看
在GitHub Actions页面查看详细的构建日志，定位具体错误信息。 