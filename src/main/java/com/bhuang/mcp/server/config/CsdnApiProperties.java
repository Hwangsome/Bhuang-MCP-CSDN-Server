package com.bhuang.mcp.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * CSDN API配置属性
 */
@Component
@ConfigurationProperties(prefix = "csdn.api")
public class CsdnApiProperties {

    /**
     * CSDN文章分类
     */
    private String categories = "Java场景面试宝典";

    /**
     * CSDN用户Cookie认证信息
     */
    private String cookie;

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 检查Cookie是否已配置且有效
     */
    public boolean isValidCookie() {
        return cookie != null && 
               !cookie.trim().isEmpty() && 
               !cookie.contains("填写你的cookie");
    }
} 