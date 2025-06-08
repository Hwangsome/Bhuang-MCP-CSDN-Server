package com.bhuang.infrastructure.gateway;

import com.bhuang.infrastructure.gateway.dto.SaveArticleRequest;
import com.bhuang.infrastructure.gateway.dto.SaveArticleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * CSDN文章服务接口
 * 使用Retrofit2框架调用CSDN API
 */
public interface ICSDNService {

    /**
     * 保存/发布文章到CSDN
     * 
     * @param cookie CSDN登录Cookie
     * @param request 文章请求参数
     * @return 保存/发布结果
     */
    @POST("blog-console-api/v1/postedit/saveArticle")
    @Headers({
        "accept: application/json, text/plain, */*",
        "accept-language: en,zh-CN;q=0.9,zh;q=0.8,hy;q=0.7",
        "content-type: application/json;",
        "origin: https://mpbeta.csdn.net",
        "referer: https://mpbeta.csdn.net/",
        "sec-ch-ua: \"Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24\"",
        "sec-ch-ua-mobile: ?0",
        "sec-ch-ua-platform: \"macOS\"",
        "sec-fetch-dest: empty",
        "sec-fetch-mode: cors",
        "sec-fetch-site: same-site",
        "user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36",
        "x-ca-key: 203803574",
        "x-ca-nonce: 17731954-35a9-40e3-94c8-78489bd4f93d",
        "x-ca-signature: 53+fXIO90fW95gbK2vG+J/CC50NLAAAPQNG2/fFTwy8=",
        "x-ca-signature-headers: x-ca-key,x-ca-nonce"
    })
    Call<SaveArticleResponse> saveArticle(
            @Header("Cookie") String cookie,
            @Body SaveArticleRequest request
    );
}