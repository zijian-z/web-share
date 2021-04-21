package com.zijian.news.vo;

import com.zijian.news.dao.Comment;

public class CommentVO {
    private String username;
    private Long linkId;
    private String content;
    private Long createTime;

    public CommentVO(Comment comment, String username) {
        this.username = username;
        this.linkId = comment.getLinkId();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
    }

    public String getUsername() {
        return username;
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
}
