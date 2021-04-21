package com.zijian.news.dao;


public class Comment {
    private Long id;
    private Long userId;
    private Long linkId;
    private String content;
    private Long createTime;

    public Comment(Long userId, Long linkId, String content, Long createTime) {
        this.userId = userId;
        this.linkId = linkId;
        this.content = content;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public String getContent() {
        return content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", linkId=" + linkId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
