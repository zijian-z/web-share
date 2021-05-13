package com.zijian.news.query;

public class FeedQuery {
    private String folderName;
    private String feedUrl;

    public FeedQuery(String folderName, String feedUrl) {
        this.folderName = folderName;
        this.feedUrl = feedUrl;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }
}
