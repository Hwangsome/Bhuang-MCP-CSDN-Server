package com.bhuang.mcp.server.tools;

import com.bhuang.infrastructure.gateway.ICSDNService;
import com.bhuang.infrastructure.gateway.dto.SaveArticleRequest;
import com.bhuang.infrastructure.gateway.dto.SaveArticleResponse;
import com.bhuang.mcp.server.config.CsdnApiProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Map;

/**
 * CSDN文章发布MCP工具服务
 * 提供AI助手调用的工具函数
 */
@Service
public class CsdnArticleService {

    private static final Logger logger = LoggerFactory.getLogger(CsdnArticleService.class);

    @Autowired
    private ICSDNService csdnService;

    @Autowired
    private CsdnApiProperties csdnApiProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发布CSDN文章的工具函数
     * 这个方法将被Spring AI MCP Server自动识别为工具
     * Cookie和分类通过配置文件配置，无需每次传递
     * 
     * @param title 文章标题
     * @param content 文章内容（HTML格式）
     * @param tags 文章标签（逗号分隔）
     * @param description 文章描述（可选）
     * @param type 文章类型（默认: original）
     * @param status 发布状态（0=发布, 1=草稿，默认: 0）
     * @param readType 阅读权限（默认: public）
     * @return JSON格式的操作结果
     */
    @Tool(name = "publishCsdnArticle", description = "发布文章到CSDN平台。Cookie和分类已通过配置文件预设。")
    public String publishCsdnArticle(
            String title,
            String content,
            String tags,
            String description,
            String type,
            Integer status,
            String readType) {
        
        try {
            logger.info("开始发布CSDN文章: {}", title);
            
            // 首先验证必要参数
            if (title == null || title.trim().isEmpty()) {
                return createErrorResponse("文章标题不能为空");
            }
            if (content == null || content.trim().isEmpty()) {
                return createErrorResponse("文章内容不能为空");
            }
            if (tags == null || tags.trim().isEmpty()) {
                return createErrorResponse("文章标签不能为空");
            }
            
            // 然后检查配置的Cookie是否有效
            if (!csdnApiProperties.isValidCookie()) {
                return createErrorResponse("Cookie未配置或无效，请在配置文件中设置有效的CSDN Cookie");
            }
            
            // 创建文章请求对象，使用配置文件中的values
            SaveArticleRequest request = new SaveArticleRequest();
            request.setTitle(title);
            request.setContent(content);
            request.setTags(tags);
            request.setDescription(description != null ? description : "");
            request.setType(type != null ? type : "original");
            request.setStatus(status != null ? status : 0);
            request.setReadType(readType != null ? readType : "public");

            // 使用配置文件中的Cookie
            String configuredCookie = csdnApiProperties.getCookie();
            logger.info("使用配置的分类: {}", csdnApiProperties.getCategories());

            // 调用CSDN API
            Call<SaveArticleResponse> call = csdnService.saveArticle(configuredCookie, request);
            Response<SaveArticleResponse> response = call.execute();

            if (response.isSuccessful()) {
                SaveArticleResponse result = response.body();
                if (result != null && result.getCode() == 200) {
                    // 检查data字段的类型和内容
                    if (result.getData() instanceof java.util.Map) {
                        // data是对象类型（发布文章的响应）
                        @SuppressWarnings("unchecked")
                        java.util.Map<String, Object> dataMap = (java.util.Map<String, Object>) result.getData();
                        
                        Object articleIdObj = dataMap.get("article_id");
                        Object urlObj = dataMap.get("url");
                        Object titleObj = dataMap.get("title");
                        
                        if (articleIdObj != null) {
                            logger.info("✅ 文章发布成功！文章ID: {}, URL: {}", 
                                    articleIdObj, urlObj);
                            
                            Map<String, Object> successResult = Map.of(
                                "success", true,
                                "message", "文章发布成功",
                                "articleId", articleIdObj.toString(),
                                "url", urlObj != null ? urlObj.toString() : "",
                                "title", titleObj != null ? titleObj.toString() : title,
                                "categories", csdnApiProperties.getCategories()
                            );
                            return objectMapper.writeValueAsString(successResult);
                        }
                    } else if (result.getData() instanceof String) {
                        // data是字符串类型（保存草稿的响应）
                        String dataString = (String) result.getData();
                        logger.info("✅ 操作成功！响应: {}, 数据: {}", result.getActualMessage(), dataString);
                        
                        Map<String, Object> successResult = Map.of(
                            "success", true,
                            "message", result.getActualMessage() != null ? result.getActualMessage() : "操作成功",
                            "data", dataString,
                            "title", title,
                            "categories", csdnApiProperties.getCategories()
                        );
                        return objectMapper.writeValueAsString(successResult);
                    }
                    
                    // 如果data为null但响应成功
                    logger.info("✅ 操作成功！响应消息: {}", result.getActualMessage());
                    Map<String, Object> successResult = Map.of(
                        "success", true,
                        "message", result.getActualMessage() != null ? result.getActualMessage() : "操作成功",
                        "title", title,
                        "categories", csdnApiProperties.getCategories()
                    );
                    return objectMapper.writeValueAsString(successResult);
                } else {
                    String errorMsg = result != null ? result.getMessage() : "响应为空";
                    logger.error("❌ 文章发布失败: {}", errorMsg);
                    return createErrorResponse("文章发布失败: " + errorMsg);
                }
            } else {
                String errorBody = response.errorBody() != null ? 
                    response.errorBody().string() : "未知错误";
                logger.error("❌ HTTP请求失败: {} {}, 错误内容: {}", 
                    response.code(), response.message(), errorBody);
                return createErrorResponse("HTTP请求失败: " + response.code() + " " + response.message());
            }
            
        } catch (Exception e) {
            logger.error("❌ 发布文章时出现异常: {}", e.getMessage(), e);
            return createErrorResponse("发布文章时出现异常: " + e.getMessage());
        }
    }

    /**
     * 保存CSDN文章草稿的工具函数
     * 这个方法将被Spring AI MCP Server自动识别为工具
     * Cookie和分类通过配置文件配置，无需每次传递
     */
    @Tool(name = "saveCsdnArticleDraft", description = "保存文章草稿到CSDN平台。Cookie和分类已通过配置文件预设。")
    public String saveCsdnArticleDraft(
            String title,
            String content,
            String tags,
            String description) {
        
        return publishCsdnArticle(title, content, tags, description, "original", 1, "public");
    }

    /**
     * 获取当前CSDN配置信息
     */
    @Tool(name = "getCsdnConfig", description = "获取当前CSDN配置信息，包括分类设置和Cookie状态。")
    public String getCsdnConfig() {
        try {
            Map<String, Object> configInfo = Map.of(
                "categories", csdnApiProperties.getCategories(),
                "cookieConfigured", csdnApiProperties.isValidCookie(),
                "cookieStatus", csdnApiProperties.isValidCookie() ? "已配置有效Cookie" : "Cookie未配置或无效"
            );
            return objectMapper.writeValueAsString(configInfo);
        } catch (Exception e) {
            return createErrorResponse("获取配置信息失败: " + e.getMessage());
        }
    }

    /**
     * 创建错误响应
     */
    private String createErrorResponse(String message) {
        try {
            Map<String, Object> errorResult = Map.of(
                "success", false,
                "message", message
            );
            return objectMapper.writeValueAsString(errorResult);
        } catch (Exception e) {
            return "{\"success\":false,\"message\":\"" + message + "\"}";
        }
    }
} 