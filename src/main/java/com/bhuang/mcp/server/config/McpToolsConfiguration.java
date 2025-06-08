package com.bhuang.mcp.server.config;

import com.bhuang.mcp.server.tools.CsdnArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP工具配置类
 * 注册CSDN相关的MCP工具
 */
@Configuration
public class McpToolsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(McpToolsConfiguration.class);

    /**
     * 注册CSDN文章服务Bean
     */
    @Bean
    public CsdnArticleService csdnArticleService() {
        logger.info("✅ 注册CSDN文章服务MCP工具");
        return new CsdnArticleService();
    }
} 