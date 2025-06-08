package com.bhuang.mcp.server.csdn.test;

import com.bhuang.mcp.server.tools.CsdnArticleService;
import com.bhuang.infrastructure.gateway.ICSDNService;
import com.bhuang.infrastructure.gateway.dto.SaveArticleRequest;
import com.bhuang.infrastructure.gateway.dto.SaveArticleResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CSDN API测试类
 * 
 * 注意：运行此测试需要有效的CSDN Cookie。
 * 请先在application.yml中配置有效的Cookie。
 */
@SpringBootTest
public class APITest {

    private static final Logger logger = LoggerFactory.getLogger(APITest.class);

    @Autowired
    private CsdnArticleService csdnArticleService;

    @Autowired
    private ICSDNService icsdnService;

    // 测试用的Cookie (用户提供的真实Cookie)
    private static final String TEST_COOKIE = "uuid_tt_dd=10_31079443030-1729242043889-293345; fid=20_81146319811-1729242047052-380713; UserName=weixin_54726354; UserInfo=b781988e08134d86bae37186a50e60bb; UserToken=b781988e08134d86bae37186a50e60bb; UserNick=weixin_54726354; AU=C0A; UN=weixin_54726354; BT=1745287695903; p_uid=U010000; csdn_newcert_weixin_54726354=1; dc_sid=51685bd30bd7837c61f33d39a0ce1df4; c_segment=1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1746690222; HMACCOUNT=A3B81F8B6438565A; FCNEC=%5B%5B%22AKsRol9ojkfWEwDsbJlwPaDf8qXtgODkfXWng0Y1QgObK3betYsMPcjKH2cUksf0aqxRya4uPTPSDv_lz4Hhpv34DGYx-raTxcwHVRBPm4lW3OVMCico1izUwpPp7krNRcTYipEhq6sX9XZv_r0pP1s_6eWDEFkXmQ%3D%3D%22%5D%5D; c_adb=1; dc_session_id=10_1749025448235.878197; _clck=1xgbyfl%7C2%7Cfwh%7C0%7C1954; _gid=GA1.2.510293052.1749025453; c_first_page=https%3A//blog.csdn.net/qq_42961150/article/details/122545929; _ga=GA1.1.254017774.1746690230; _ga_7W1N0GEY1P=GS2.1.s1749025452$o4$g1$t1749026038$j60$l0$h0; c_first_ref=default; c_dsid=11_1749026273764.655104; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20231011044944.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A0%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Atrue%2C%22oldFullVersion%22%3Afalse%2C%22userName%22%3A%22weixin_54726354%22%7D; _clsk=1ak9yaz%7C1749026279694%7C3%7C0%7Cl.clarity.ms%2Fcollect; c_pref=https%3A//mpbeta.csdn.net/mp_blog/creation/success/148430024; c_ref=https%3A//mpbeta.csdn.net/; c_page_id=default; log_Id_pv=8; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1749026519; log_Id_view=175; log_Id_click=9; dc_tos=sxbpii";

    /**
     * 测试发布CSDN文章
     * 
     * 注意：需要在application.yml中配置有效Cookie才能成功
     */
    @Test
    public void testPublishArticle() {
        System.out.println("🧪 测试发布CSDN文章");
        
        String result = csdnArticleService.publishCsdnArticle(
            "Spring AI MCP Server 开发指南",
            "<h1>Spring AI MCP Server</h1>" +
            "<p>这是一个基于Spring AI开发的MCP服务器，提供CSDN文章发布功能。</p>" +
            "<h2>主要特性</h2>" +
            "<ul>" +
            "<li>支持自动文章发布</li>" +
            "<li>支持草稿保存</li>" +
            "<li>配置化Cookie管理</li>" +
            "</ul>" +
            "<h2>使用方法</h2>" +
            "<p>通过MCP协议调用相关工具即可。</p>",
            "Spring AI,MCP,CSDN,教程",
            "介绍如何使用Spring AI MCP Server发布文章到CSDN",
            "original",
            0,
            "public"
        );
        
        System.out.println("📄 发布结果:");
        System.out.println(result);
    }
    
    /**
     * 测试发布CSDN文章 - 使用中文内容
     */
    @Test 
    public void testPublishChineseArticle() {
        System.out.println("🧪 测试发布中文CSDN文章");
        
        String result = csdnArticleService.publishCsdnArticle(
            "人工智能时代的Java开发",
            "<h1>人工智能时代的Java开发</h1>" +
            "<p>随着人工智能技术的快速发展，Java开发者面临着新的机遇和挑战。</p>" +
            "<h2>主要变化</h2>" +
            "<ol>" +
            "<li><strong>AI集成</strong>：更多Java应用需要集成AI功能</li>" +
            "<li><strong>数据处理</strong>：大数据和机器学习成为必备技能</li>" +
            "<li><strong>云原生</strong>：容器化和微服务架构成为标准</li>" +
            "</ol>" +
            "<h2>发展建议</h2>" +
            "<p>Java开发者应该：</p>" +
            "<ul>" +
            "<li>学习Spring AI等AI集成框架</li>" +
            "<li>掌握云原生开发技术</li>" +
            "<li>提升数据处理能力</li>" +
            "</ul>",
            "Java,人工智能,Spring AI,云原生",
            "探讨人工智能时代Java开发者的发展方向",
            "original",
            0,
            "public"
        );
        
        System.out.println("📄 发布结果:");
        System.out.println(result);
    }
    
    /**
     * 测试保存草稿
     */
    @Test
    public void testSaveDraft() {
        System.out.println("🧪 测试保存CSDN草稿");
        
        String result = csdnArticleService.saveCsdnArticleDraft(
            "Spring Boot 3.0 新特性解析（草稿）",
            "<h1>Spring Boot 3.0 新特性</h1>" +
            "<p>这是一篇关于Spring Boot 3.0新特性的文章草稿。</p>" +
            "<h2>主要更新</h2>" +
            "<p>待补充内容...</p>",
            "Spring Boot,Java,框架",
            "Spring Boot 3.0新特性的详细解析"
        );
        
        System.out.println("📄 草稿保存结果:");
        System.out.println(result);
    }
    
    /**
     * 测试参数验证
     */
    @Test
    public void testParameterValidation() {
        System.out.println("🧪 测试参数验证");
        
        // 测试空标题
        String result1 = csdnArticleService.publishCsdnArticle(
            "",
            "内容",
            "标签",
            "描述",
            "original",
            0,
            "public"
        );
        System.out.println("空标题测试结果: " + result1);
        
        // 测试空内容
        String result2 = csdnArticleService.publishCsdnArticle(
            "标题",
            "",
            "标签", 
            "描述",
            "original",
            0,
            "public"
        );
        System.out.println("空内容测试结果: " + result2);
    }

    /**
     * 测试服务Bean注入
     */
    @Test
    public void testServiceInjection() {
        logger.info("🧪 测试服务Bean注入");
        
        assertNotNull(csdnArticleService, "CsdnArticleService应该被正确注入");
        
        logger.info("✅ 服务注入测试通过");
    }

    /**
     * 测试ICSDNService接口
     */
    @Test
    public void testICSDNServiceInterface() {
        logger.info("开始测试ICSDNService接口...");
        
        // 创建测试请求
        SaveArticleRequest request = new SaveArticleRequest();
        request.setTitle("测试文章标题");
        request.setContent("这是一篇测试文章的内容");
        request.setTags("Java,Spring Boot,MCP");
        request.setType("original");
        request.setStatus(2); // 2表示草稿状态
        
        try {
            // 测试保存草稿
            Call<SaveArticleResponse> call = icsdnService.saveArticle(
                "cookie=test_cookie", 
                request
            );
            
            Response<SaveArticleResponse> response = call.execute();
            
            logger.info("ICSDNService响应状态码: {}", response.code());
            
            if (response.isSuccessful() && response.body() != null) {
                SaveArticleResponse responseBody = response.body();
                logger.info("ICSDNService响应: {}", responseBody.getMessage());
            } else {
                logger.warn("ICSDNService请求失败，状态码: {}", response.code());
                if (response.errorBody() != null) {
                    logger.warn("错误信息: {}", response.errorBody().string());
                }
            }
            
        } catch (Exception e) {
            logger.error("ICSDNService测试异常: {}", e.getMessage(), e);
        }
        
        logger.info("ICSDNService接口测试完成");
    }
} 