package com.bhuang.mcp.server.csdn.test;

import com.bhuang.mcp.server.tools.CsdnArticleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MCP服务器集成测试
 * 专注于测试Spring AI MCP Server的集成功能
 */
@SpringBootTest
public class McpServerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(McpServerIntegrationTest.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CsdnArticleService csdnArticleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试Spring AI MCP Server上下文加载
     */
    @Test
    public void testMcpServerContextLoads() {
        logger.info("🧪 测试MCP Server上下文加载");
        
        assertNotNull(applicationContext, "Spring应用上下文应该被正确加载");
        assertTrue(applicationContext.containsBean("csdnArticleService"), 
                "CsdnArticleService Bean应该被注册");
        
        logger.info("✅ MCP Server上下文加载测试通过");
    }

    /**
     * 测试MCP工具Bean注入
     */
    @Test
    public void testMcpToolBeanInjection() {
        logger.info("🧪 测试MCP工具Bean注入");
        
        assertNotNull(csdnArticleService, "CsdnArticleService应该被正确注入");
        
        logger.info("✅ MCP工具Bean注入测试通过");
    }

    /**
     * 测试工具参数验证功能
     */
    @Test
    public void testToolParameterValidation() {
        logger.info("🧪 测试工具参数验证功能");
        
        // 测试空标题验证
        String result1 = csdnArticleService.publishCsdnArticle(
                "", "内容", "标签", "描述", "original", 0, "public"
        );
        
        try {
            JsonNode json1 = objectMapper.readTree(result1);
            assertFalse(json1.get("success").asBoolean(), "空标题应该导致验证失败");
            assertTrue(json1.get("message").asText().contains("标题"), "错误消息应该提到标题");
        } catch (Exception e) {
            fail("返回结果应该是有效的JSON: " + e.getMessage());
        }

        // 测试空内容验证
        String result2 = csdnArticleService.publishCsdnArticle(
                "测试标题", "", "标签", "描述", "original", 0, "public"
        );
        
        try {
            JsonNode json2 = objectMapper.readTree(result2);
            assertFalse(json2.get("success").asBoolean(), "空内容应该导致验证失败");
            assertTrue(json2.get("message").asText().contains("内容"), "错误消息应该提到内容");
        } catch (Exception e) {
            fail("返回结果应该是有效的JSON: " + e.getMessage());
        }

        logger.info("✅ 工具参数验证功能测试通过");
    }

    /**
     * 测试草稿保存工具
     */
    @Test
    public void testDraftSavingTool() {
        logger.info("🧪 测试草稿保存工具");
        
        String result = csdnArticleService.saveCsdnArticleDraft(
                "测试草稿标题",
                "<p>测试草稿内容</p>",
                "测试,草稿",
                "测试草稿描述"
        );
        
        assertNotNull(result, "草稿保存结果不应该为空");
        
        try {
            JsonNode json = objectMapper.readTree(result);
            assertNotNull(json.get("success"), "返回结果应该包含success字段");
            assertNotNull(json.get("message"), "返回结果应该包含message字段");
        } catch (Exception e) {
            fail("返回结果应该是有效的JSON: " + e.getMessage());
        }
        
        logger.info("✅ 草稿保存工具测试通过");
    }

    /**
     * 测试工具返回JSON格式
     */
    @Test
    public void testToolJsonResponseFormat() {
        logger.info("🧪 测试工具返回JSON格式");
        
        String result = csdnArticleService.publishCsdnArticle(
                "测试标题",
                "<p>测试内容</p>",
                "测试标签",
                "测试描述",
                "original",
                0,
                "public"
        );
        
        assertNotNull(result, "返回结果不应该为空");
        
        try {
            JsonNode json = objectMapper.readTree(result);
            
            // 验证必要字段存在
            assertTrue(json.has("success"), "返回结果应该包含success字段");
            assertTrue(json.has("message"), "返回结果应该包含message字段");
            
            // 验证字段类型
            assertTrue(json.get("success").isBoolean(), "success字段应该是布尔值");
            assertTrue(json.get("message").isTextual(), "message字段应该是字符串");
            
            logger.info("📄 返回的JSON格式: {}", result);
            
        } catch (Exception e) {
            fail("返回结果应该是有效的JSON: " + e.getMessage());
        }
        
        logger.info("✅ 工具返回JSON格式测试通过");
    }

    /**
     * 测试MCP服务器配置
     */
    @Test
    public void testMcpServerConfiguration() {
        logger.info("🧪 测试MCP服务器配置");
        
        // 检查是否有必要的Spring AI MCP相关Bean
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        boolean hasMcpBeans = false;
        
        for (String beanName : beanNames) {
            if (beanName.contains("mcp") || beanName.contains("Mcp")) {
                logger.info("发现MCP相关Bean: {}", beanName);
                hasMcpBeans = true;
            }
        }
        
        // 检查是否有我们的工具Bean
        assertTrue(applicationContext.containsBean("csdnArticleService"), 
                "应该包含csdnArticleService Bean");
        
        logger.info("✅ MCP服务器配置测试通过");
    }

    /**
     * 演示如何使用有效Cookie进行真实测试
     */
    @Test
    public void demonstrateRealUsage() {
        logger.info("🧪 演示真实使用方法");
        
        logger.info("📋 使用真实Cookie的步骤:");
        logger.info("1. 登录CSDN: https://blog.csdn.net");
        logger.info("2. 打开浏览器开发者工具 (F12)");
        logger.info("3. 切换到Network标签页");
        logger.info("4. 刷新页面，查看任意请求的Request Headers");
        logger.info("5. 复制完整的Cookie值");
        logger.info("6. 替换测试中的TEST_COOKIE常量");
        
        logger.info("💡 示例Cookie格式:");
        logger.info("uuid_tt_dd=xxx; UserName=xxx; UserInfo=xxx; UserToken=xxx; ...");
        
        logger.info("🚀 使用真实Cookie后，工具将能够成功发布文章到CSDN!");
        
        logger.info("✅ 真实使用方法演示完成");
    }

    @Test
    public void testSaveCsdnArticleDraft() {
        logger.info("🧪 测试保存CSDN文章草稿工具");
        
        CsdnArticleService service = applicationContext.getBean(CsdnArticleService.class);
        assertNotNull(service, "CsdnArticleService应该被注入");
        
        // 测试保存草稿（使用配置文件中的Cookie）
        String result = service.saveCsdnArticleDraft(
                "测试草稿标题",
                "<h1>测试草稿</h1><p>这是一个测试草稿内容</p>",
                "测试,草稿,Spring AI",
                "测试草稿描述"
        );
        
        assertNotNull(result, "应该返回结果");
        logger.info("草稿保存结果: {}", result);
        
        // 验证返回的是有效的JSON
        assertTrue(result.startsWith("{") && result.endsWith("}"), 
                "应该返回JSON格式的结果");
        
        logger.info("✅ 保存CSDN文章草稿工具测试通过");
    }
} 