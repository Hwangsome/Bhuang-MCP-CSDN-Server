# 🚀 Bhuang AI MCP Server

一个为程序员打造的趣味实用 MCP (Model Context Protocol) 服务器，基于 Spring Boot 和 Spring AI MCP Boot Starter 构建。

## 🚀 项目简介

这是一个**Model Context Protocol (MCP) 服务器**，为AI助手提供CSDN文章发布工具。使用Spring AI MCP Server Boot Starter实现，支持AI模型与CSDN平台的结构化交互。

## 📦 功能特性

### 🔧 JSON 工具 (JsonUtilsTool)
- **formatJson** - JSON格式化美化
- **validateJson** - JSON格式验证
- **extractFromJson** - 从JSON中提取指定路径的值
- **compareJson** - 比较两个JSON的差异
- **convertJsonTo** - JSON转换为其他格式 (XML, YAML, CSV)
- **analyzeJson** - 分析JSON结构和统计信息

### 💻 系统信息工具 (SystemInfoTool)
- **getJvmInfo** - 获取JVM详细信息和内存使用情况
- **getOperatingSystemInfo** - 获取操作系统和目录信息
- **getSystemProperties** - 查询Java系统属性（支持过滤）
- **getEnvironmentVariables** - 查看环境变量（支持过滤）
- **getTimeInfo** - 获取当前时间和时区信息
- **getThreadInfo** - 查看线程信息和状态
- **getDiskInfo** - 获取磁盘空间使用情况
- **getNetworkInfo** - 查看网络接口信息
- **performGCAndGetMemoryInfo** - 执行垃圾回收并分析内存变化

### 📝 文本处理工具 (TextProcessingTool)
- **analyzeText** - 文本统计分析（字符数、单词数、阅读时间等）
- **formatText** - 文本格式化（去空白、规范换行等）
- **findAndReplace** - 文本查找替换（支持正则表达式）
- **extractContent** - 提取文本中的特定内容（邮箱、URL、电话等）
- **generateHash** - 生成文本哈希值（MD5、SHA等）
- **convertEncoding** - 文本编码转换（Base64、URL编码、十六进制等）
- **generateRandomText** - 生成随机文本（字母、数字、密码等）
- **compareTexts** - 文本差异比较和相似度分析

### 🌐 网络工具 (NetworkTool)
- **pingHost** - 测试主机连通性
- **resolveDomain** - 域名解析获取IP地址
- **testPort** - 测试端口连通性
- **getLocalNetworkInfo** - 获取本机网络信息
- **measureLatency** - 网络延迟测试（多次ping平均值）

### 📅 日期时间工具 (DateTimeTool)
- **getCurrentTime** - 获取当前时间的各种格式
- **convertTimestamp** - 时间戳转换为可读时间
- **formatDateTime** - 日期时间格式化
- **calculateDateDifference** - 计算两个日期间的差值
- **convertTimezone** - 时区转换
- **calculateDateTime** - 日期时间计算（加减天数、小时等）
- **getDateInfo** - 获取日期详细信息（工作日、季度等）

### MCP工具
1. **publishCsdnArticle** - 发布CSDN文章
2. **saveCsdnArticleDraft** - 保存CSDN文章草稿

### 核心能力
- ✅ Cookie参数化认证
- ✅ 完整的参数验证
- ✅ 结构化JSON响应
- ✅ 异常处理和错误返回
- ✅ Spring AI自动工具发现

## 🛠️ 技术栈

- **Java 17+**
- **Spring Boot 3.x**
- **Spring AI MCP Boot Starter**
- **Jackson** (JSON处理)
- **Maven** (构建工具)
- **Retrofit2** - HTTP客户端
- **OkHttp3** - HTTP日志记录

## 🚀 快速开始

### 1. 克隆项目
```bash
git clone <repository-url>
cd Bhuang-AI-MCP-Server
```

### 2. 构建项目
```bash
mvn clean compile
```

### 3. 运行服务器
```bash
mvn spring-boot:run
```

服务器将在 `http://localhost:8080` 启动

### 4. 配置 MCP 客户端
在你的AI助手配置文件中添加此MCP服务器：

```json
{
  "mcpServers": {
    "bhuang-dev-tools": {
      "command": "java",
      "args": ["-jar", "path/to/Bhuang-AI-MCP-Server.jar"]
    }
  }
}
```

### 2. MCP端点

- **SSE端点**: `http://localhost:8080/sse`
- **消息端点**: `http://localhost:8080/mcp/messages`

## 🔑 Cookie获取方法

为了使用CSDN发布工具，需要获取有效的CSDN登录Cookie：

### 步骤1：登录CSDN
1. 打开浏览器，访问 `https://blog.csdn.net`
2. 登录你的CSDN账号

### 步骤2：获取Cookie
1. 按F12打开开发者工具
2. 切换到"Network"标签页
3. 在CSDN页面进行任意操作（如刷新页面）
4. 在Network中找到任意请求，查看Request Headers
5. 复制完整的Cookie值

### Cookie示例格式
```
uuid_tt_dd=xxx; UserName=xxx; UserInfo=xxx; UserToken=xxx; ...
```

### 步骤3：使用Cookie
将获取的Cookie传递给MCP工具的`cookie`参数。

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试
mvn test -Dtest=APITest
```

**注意**: 测试中的Cookie是示例Cookie，会返回401错误。这是正常的，表明服务器正确处理了认证失败的情况。

## 🔧 API使用示例

### 发布文章

```java
String result = csdnArticleService.publishCsdnArticle(
    "你的有效Cookie",
    "文章标题", 
    "<p>文章内容HTML</p>",
    "Java,Spring Boot,AI",
    "文章描述",
    "original",  // 文章类型
    0,          // 状态(0=发布,1=草稿)
    "public"    // 可见性
);
```

### 保存草稿

```java
String result = csdnArticleService.saveCsdnArticleDraft(
    "你的有效Cookie",
    "草稿标题",
    "<p>草稿内容</p>", 
    "标签",
    "描述"
);
```

## 📝 返回格式

### 成功响应
```json
{
  "success": true,
  "message": "文章发布成功", 
  "articleId": 148454573,
  "url": "https://blog.csdn.net/xxx/article/details/148454573",
  "title": "文章标题"
}
```

### 错误响应  
```json
{
  "success": false,
  "message": "错误信息"
}
```

## 🔍 常见问题

### Q: 测试返回401错误
A: 这是正常的，测试用的Cookie已过期。请按照上述方法获取有效Cookie。

### Q: 如何集成到AI应用？
A: 启动MCP服务器后，AI客户端可以通过MCP协议调用`publishCsdnArticle`和`saveCsdnArticleDraft`工具。

### Q: 支持其他博客平台吗？
A: 当前仅支持CSDN，可以扩展支持其他平台。

## 📊 项目结构

```
src/main/java/com/bhuang/
├── BhuangMcpServerApplication.java      # 主启动类
├── infrastructure/gateway/
│   ├── ICSDNService.java               # Retrofit接口
│   └── dto/                           # 数据传输对象
│       ├── SaveArticleRequest.java
│       └── SaveArticleResponse.java
└── mcp/server/
    ├── config/
    │   └── McpToolsConfiguration.java  # MCP工具配置
    └── tools/
        └── CsdnArticleService.java     # CSDN服务工具
```

## 🎯 特色功能

- **🎨 美观输出** - 所有工具都使用表情符号和格式化输出，提升使用体验
- **🔍 智能分析** - 不仅提供基础功能，还包含深度分析和统计信息
- **🛡️ 错误处理** - 完善的错误处理和用户友好的错误提示
- **🌍 中文支持** - 全中文界面，更适合中文用户使用
- **📊 详细报告** - 每个工具都提供详细的分析报告和统计数据

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目！

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件。

## 👨‍💻 作者

**Bhuang** - 一个热爱编程的开发者

---

**让开发更有趣！让工具更实用！** 🎉 