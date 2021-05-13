package com.zijian.news.vo;

public class FeedContentVO {
    private Long id;
    private String title;
    private String description;
    private Long publishTime;
    private String link;
    private Integer hasRead;

    public FeedContentVO(Long id, String title, String description, Long publishTime, String link, Integer hasRead) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishTime = publishTime;
        this.link = link;
        this.hasRead = hasRead;
    }

    public Long getId() {
        return id;
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

    public Integer getHasRead() {
        return hasRead;
    }
}
