package com.zijian.news.vo;

import com.zijian.news.dao.Link;

import java.util.List;

public class LinkVO {
    private Long id;
    private Long userId;
    private String title;
    private String uri;
    private Long createTime;
    private boolean liked;
    private List<CommentVO> comments;

    public LinkVO(Link link, boolean liked, List<CommentVO> comments) {
        this.id = link.getId();
        this.userId = link.getUserId();
        this.title = link.getTitle();
        this.uri = link.getUrl();
        this.createTime = link.getCreateTime();
        this.liked = liked;
        this.comments = comments;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
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

    public String getUri() {
        return uri;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public boolean isLiked() {
        return liked;
    }

    public List<CommentVO> getComments() {
        return comments;
    }
}
