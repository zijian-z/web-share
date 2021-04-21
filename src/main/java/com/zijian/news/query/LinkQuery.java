package com.zijian.news.query;

public class LinkQuery {
    private String title;
    private String url;
    private String firstComment;

    public LinkQuery(String title, String url, String firstComment) {
        this.title = title;
        this.url = url;
        this.firstComment = firstComment;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getFirstComment() {
        return firstComment;
    }
}
