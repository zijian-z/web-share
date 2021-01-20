package com.zijian.webshare.link;

public class LinkVO {
    private String title;
    private String uri;
    private String firstComment;

    public LinkVO(String title, String uri, String firstComment) {
        this.title = title;
        this.uri = uri;
        this.firstComment = firstComment;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getFirstComment() {
        return firstComment;
    }

    @Override
    public String toString() {
        return "LinkVO{" +
                "tile='" + title + '\'' +
                ", uri='" + uri + '\'' +
                ", firstComment='" + firstComment + '\'' +
                '}';
    }
}
