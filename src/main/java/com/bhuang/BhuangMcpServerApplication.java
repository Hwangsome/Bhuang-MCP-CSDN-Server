package com.bhuang;

import com.bhuang.infrastructure.gateway.ICSDNService;
import com.bhuang.mcp.server.config.CsdnApiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Spring AI MCP Server 主应用程序
 * 
 * 这个应用程序实现了Model Context Protocol (MCP) 服务器，
 * 为AI助手提供CSDN文章发布工具。
 * 
 * MCP是一个标准化协议，允许AI模型与外部工具和资源进行结构化交互。
 */
@SpringBootApplication
@EnableConfigurationProperties(CsdnApiProperties.class)
public class BhuangMcpServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(BhuangMcpServerApplication.class);

    public static void main(String[] args) {
        logger.info("🚀 启动 Spring AI MCP Server...");
        
        SpringApplication app = new SpringApplication(BhuangMcpServerApplication.class);
        app.run(args);
        
        logger.info("✅ Spring AI MCP Server 启动完成!");
        logger.info("📡 MCP Server可通过以下端点访问:");
        logger.info("   - SSE端点: http://localhost:8080/sse");
        logger.info("   - 消息端点: http://localhost:8080/mcp/messages");
        logger.info("🛠️  可用工具: publishCsdnArticle, saveCsdnArticleDraft, getCsdnConfig");
        logger.info("⚙️  配置提示: 请在application.yml中设置有效的CSDN Cookie");
    }

    /**
     * 配置CSDN API服务
     */
    @Bean
    public ICSDNService icsdnService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bizapi.csdn.net/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        
        return retrofit.create(ICSDNService.class);
    }
} 