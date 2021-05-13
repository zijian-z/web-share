package com.zijian.news.vo;

public class FeedVO {
    private Long id;
    private String folderName;
    private String feedUrl;
    private String feedName;
    private Long unreadCount;

    public FeedVO(Long id, String folderName, String feedUrl, String feedName) {
        this.id = id;
        this.folderName = folderName;
        this.feedUrl = feedUrl;
        this.feedName = feedName;
        this.unreadCount = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getFeedName() {
        return feedName;
    }

    public Long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Long unreadCount) {
        this.unreadCount = unreadCount;
    }
}
