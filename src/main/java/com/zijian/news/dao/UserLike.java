package com.zijian.news.dao;

public class UserLike {
    private Long id;
    private Long linkId;
    private Long userId;
    private Integer liked;

    public UserLike(Long linkId, Long userId) {
        this.linkId = linkId;
        this.userId = userId;
        this.liked = 1;
    }

    public UserLike(Long linkId, Long userId, Integer liked) {
        this.linkId = linkId;
        this.userId = userId;
        this.liked = liked;
    }

    public Long getId() {
        return id;
    }

    public Long getLinkId() {
        return linkId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getLiked() {
        return liked;
    }
}
