# CSDN API 简化封装使用指南

## 📝 项目简介

这是一个基于 Retrofit2 框架封装的 CSDN 文章发布 API，实现了对 CSDN 接口的简化调用。只需要提供 Cookie 参数即可实现文章发布功能。

## 🛠️ 技术栈

- **Spring Boot 3.2.0**
- **Retrofit2 3.0.0**
- **OkHttp3 4.9.3**
- **Gson 2.8.9**

## 🚀 快速开始

### 1. 依赖配置

在 `pom.xml` 中已包含所有必要依赖：

```xml
<!-- Retrofit2 核心库 -->
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>retrofit</artifactId>
    <version>3.0.0</version>
</dependency>

<!-- Retrofit2 Gson转换器 -->
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>converter-gson</artifactId>
    <version>3.0.0</version>
</dependency>

<!-- OkHttp3 -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.9.3</version>
</dependency>

<!-- OkHttp3 日志拦截器 -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>logging-interceptor</artifactId>
    <version>4.9.3</version>
</dependency>
```

### 2. 获取 CSDN Cookie

1. 登录 CSDN 网站
2. 打开浏览器开发者工具 (F12)
3. 在 Network 标签页中找到任意请求
4. 复制 Cookie 值

### 3. 使用示例

```java
@Autowired
private ICSDNService csdnService;

public void publishArticle() {
    try {
        // 创建文章请求对象
        SaveArticleRequest request = new SaveArticleRequest();
        request.setTitle("我的测试文章");
        request.setContent("<h1>标题</h1><p>这是文章内容</p>");
        request.setTags("Java,Spring Boot");
        request.setDescription("文章描述");
        request.setType("original");
        request.setStatus(0); // 0=发布，1=草稿
        request.setReadType("public");

        // 调用API（只需要Cookie）
        String cookie = "your_csdn_cookie_here";
        Call<SaveArticleResponse> call = csdnService.saveArticle(cookie, request);
        
        // 执行请求
        Response<SaveArticleResponse> response = call.execute();
        
        if (response.isSuccessful()) {
            SaveArticleResponse result = response.body();
            System.out.println("文章发布成功，URL: " + result.getData().getUrl());
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

## 📋 API 接口说明

### ICSDNService.saveArticle()

**接口描述：** 保存/发布文章到 CSDN

**方法签名：**
```java
Call<SaveArticleResponse> saveArticle(
    @Header("Cookie") String cookie,
    @Body SaveArticleRequest request
);
```

**参数说明：**
- `cookie`: CSDN 用户的 Cookie 认证信息
- `request`: 文章保存请求对象

### SaveArticleRequest 字段说明

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | ✅ | 文章标题 |
| content | String | ✅ | 文章内容(HTML格式) |
| tags | String | ✅ | 文章标签(逗号分隔) |
| description | String | ❌ | 文章描述 |
| type | String | ❌ | 文章类型(默认: "original") |
| status | int | ❌ | 发布状态(0=发布, 1=草稿) |
| readType | String | ❌ | 阅读权限(默认: "public") |

### SaveArticleResponse 响应说明

```json
{
    "code": 200,
    "traceId": "5d49c761-8e9e-4bf6-a7dd-f37361f16c97",
    "data": {
        "url": "https://blog.csdn.net/username/article/details/148430113",
        "article_id": 148430113,
        "title": "文章标题",
        "description": "文章描述"
    },
    "msg": "发布成功。"
}
```

## 🧪 测试用例

项目中包含完整的测试用例，可以运行以下命令进行测试：

```bash
# 运行所有测试
mvn test -Dtest=APITest

# 运行特定测试方法
mvn test -Dtest=APITest#testSaveArticleSimplified
```

## ⚠️ 注意事项

1. **Cookie 有效期：** CSDN Cookie 有一定的有效期，失效后需要重新获取
2. **发布频率限制：** CSDN 对文章发布有频率限制，请避免频繁发布
3. **内容格式：** 文章内容支持 HTML 格式
4. **错误处理：** 请妥善处理 API 调用异常和错误响应

## 🔧 项目结构

```
src/main/java/com/bhuang/infrastructure/gateway/
├── ICSDNService.java                    # CSDN API 接口定义
├── config/
│   └── RetrofitConfig.java             # Retrofit 配置类
├── dto/
│   ├── SaveArticleRequest.java         # 请求 DTO
│   └── SaveArticleResponse.java        # 响应 DTO
└── util/
    └── CSDNSignatureUtil.java          # 签名工具类（已简化）

src/test/java/cn/bugstack/mcp/server/csdn/test/
└── APITest.java                        # API 测试类

src/main/java/com/bhuang/example/
└── CSDNServiceExample.java             # 使用示例
```

## 🎯 简化优势

相比之前的复杂版本，当前版本的优势：

1. **简化参数：** 只需要 Cookie 一个参数，无需复杂的签名计算
2. **易于使用：** 接口调用更加直观和简单
3. **维护简单：** 减少了签名相关的复杂逻辑
4. **稳定性好：** 固定的请求头配置，减少了出错可能性

## 📞 技术支持

如有问题，请查看测试用例或参考使用示例。 