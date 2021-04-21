package com.zijian.news.dao;

public class Link {
    private Long id;
    private Long userId;
    private String title;
    private String url;
    private Long createTime;

    public Link() {
    }

    public Link(Long userId, String title, String url, Long createTime) {
        this.userId = userId;
        this.title = title;
        this.url = url;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
