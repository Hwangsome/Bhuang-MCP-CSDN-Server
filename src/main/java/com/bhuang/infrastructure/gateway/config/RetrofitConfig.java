package com.bhuang.infrastructure.gateway.config;

import com.bhuang.infrastructure.gateway.ICSDNService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Retrofit2配置类
 * 配置OkHttp客户端和Retrofit实例
 */
@Configuration
public class RetrofitConfig {

    /**
     * CSDN API基础URL
     */
    private static final String CSDN_BASE_URL = "https://bizapi.csdn.net/";

    /**
     * 配置OkHttp客户端
     */
    @Bean
    public OkHttpClient okHttpClient() {
        // HTTP日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor) // 添加日志拦截器
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
                .readTimeout(30, TimeUnit.SECONDS) // 读取超时
                .writeTimeout(30, TimeUnit.SECONDS) // 写入超时
                .build();
    }

    /**
     * 配置Retrofit实例
     */
    @Bean
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(CSDN_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson转换器
                .build();
    }

    /**
     * 创建CSDN服务实例
     */
    @Bean
    public ICSDNService csdnService(Retrofit retrofit) {
        return retrofit.create(ICSDNService.class);
    }
} 