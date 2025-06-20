package com.bhuang.infrastructure.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * CSDN保存文章请求参数
 */
public class SaveArticleRequest {

    @JsonProperty("article_id")
    private String articleId = "";

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("content")
    private String content;

    @JsonProperty("tags")
    private String tags;

    @JsonProperty("categories")
    private String categories = "";

    @JsonProperty("type")
    private String type = "original";

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("read_type")
    private String readType = "public";

    @JsonProperty("reason")
    private String reason = "";

    @JsonProperty("original_link")
    private String originalLink = "";

    @JsonProperty("authorized_status")
    private Boolean authorizedStatus = false;

    @JsonProperty("check_original")
    private Boolean checkOriginal = false;

    @JsonProperty("source")
    private String source = "pc_postedit";

    @JsonProperty("not_auto_saved")
    private Integer notAutoSaved = 1;

    @JsonProperty("creator_activity_id")
    private String creatorActivityId = "";

    @JsonProperty("cover_images")
    private List<String> coverImages = List.of();

    @JsonProperty("cover_type")
    private Integer coverType = 1;

    @JsonProperty("vote_id")
    private Integer voteId = 0;

    @JsonProperty("resource_id")
    private String resourceId = "";

    @JsonProperty("scheduled_time")
    private Long scheduledTime = 0L;

    @JsonProperty("is_new")
    private Integer isNew = 1;

    @JsonProperty("sync_git_code")
    private Integer syncGitCode = 0;

    // 构造函数
    public SaveArticleRequest() {}

    public SaveArticleRequest(String title, String content, String description, String tags, String type, Integer status, String readType) {
        this.title = title;
        this.content = content;
        this.description = description;
        this.tags = tags;
        this.type = type;
        this.status = status;
        this.readType = readType;
    }

    // Getters and Setters
    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public Boolean getAuthorizedStatus() {
        return authorizedStatus;
    }

    public void setAuthorizedStatus(Boolean authorizedStatus) {
        this.authorizedStatus = authorizedStatus;
    }

    public Boolean getCheckOriginal() {
        return checkOriginal;
    }

    public void setCheckOriginal(Boolean checkOriginal) {
        this.checkOriginal = checkOriginal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getNotAutoSaved() {
        return notAutoSaved;
    }

    public void setNotAutoSaved(Integer notAutoSaved) {
        this.notAutoSaved = notAutoSaved;
    }

    public String getCreatorActivityId() {
        return creatorActivityId;
    }

    public void setCreatorActivityId(String creatorActivityId) {
        this.creatorActivityId = creatorActivityId;
    }

    public List<String> getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(List<String> coverImages) {
        this.coverImages = coverImages;
    }

    public Integer getCoverType() {
        return coverType;
    }

    public void setCoverType(Integer coverType) {
        this.coverType = coverType;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getSyncGitCode() {
        return syncGitCode;
    }

    public void setSyncGitCode(Integer syncGitCode) {
        this.syncGitCode = syncGitCode;
    }
}