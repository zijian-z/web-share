package com.zijian.news.dao;

import com.rometools.rome.feed.synd.SyndEntry;

public class FeedContent {
    private Long id;
    private Long feedId;
    private String title;
    private String description;
    private Long publishTime;
    private String link;

    public FeedContent(Long feedId, String title, String description, Long publishTime, String link) {
        this.feedId = feedId;
        this.title = title;
        this.description = description;
        this.publishTime = publishTime;
        this.link = link;
    }

    public FeedContent(String title, String description, Long publishTime, String link) {
        this.title = title;
        this.description = description;
        this.publishTime = publishTime;
        this.link = link;
    }

    public FeedContent(Long feedId, SyndEntry entry) {
        this.feedId = feedId;
        this.title = entry.getTitle();
        this.description = entry.getDescription().getValue();
        this.publishTime = entry.getPublishedDate().getTime();
        this.link = entry.getLink();
    }

    public Long getId() {
        return id;
    }

    public Long getFeedId() {
        return feedId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public String getLink() {
        return link;
    }
}
