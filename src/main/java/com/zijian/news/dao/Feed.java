package com.zijian.news.dao;

public class Feed {
    private Long id;
    private Long userId;
    private Long folderId;
    private String feedUrl;
    private String feedName;

    public Feed(Long userId, Long folderId, String feedUrl, String feedName) {
        this.userId = userId;
        this.folderId = folderId;
        this.feedUrl = feedUrl;
        this.feedName = feedName;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getFeedName() {
        return feedName;
    }
}
