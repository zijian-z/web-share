package com.zijian.news.query;

public class CommentQuery {
    private Long linkId;
    private String content;

    public CommentQuery() {
    }

    public CommentQuery(Long linkId, String content) {
        this.linkId = linkId;
        this.content = content;
    }

    public Long getLinkId() {
        return linkId;
    }

    public String getContent() {
        return content;
    }
}
