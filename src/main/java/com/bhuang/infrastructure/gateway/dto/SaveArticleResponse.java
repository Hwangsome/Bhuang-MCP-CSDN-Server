package com.bhuang.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CSDN保存文章响应对象
 */
public class SaveArticleResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private Object data;

    public static class Data {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("article_id")
        private Long articleId;

        @JsonProperty("url")
        private String url;

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getArticleId() {
            return articleId;
        }

        public void setArticleId(Long articleId) {
            this.articleId = articleId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", articleId=" + articleId +
                    ", url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    // Getters and Setters
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取实际的消息内容
     */
    public String getActualMessage() {
        if (message != null) {
            return message;
        }
        return msg;
    }

    /**
     * 获取数据对象（如果data是对象类型）
     */
    public Data getDataAsObject() {
        if (data instanceof java.util.Map) {
            // 这里可以进行手动转换
            return null; // 简化处理
        }
        return null;
    }

    /**
     * 获取数据字符串（如果data是字符串类型）
     */
    public String getDataAsString() {
        if (data instanceof String) {
            return (String) data;
        }
        return null;
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return code != null && code == 200;
    }

    @Override
    public String toString() {
        return "SaveArticleResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}