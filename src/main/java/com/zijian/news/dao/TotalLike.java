package com.zijian.news.dao;

public class TotalLike {
    private Long id;
    private Long linkId;
    private Long likeCount;

    public TotalLike(Long linkId, Long likeCount) {
        this.linkId = linkId;
        this.likeCount = likeCount;
    }

    public Long getId() {
        return id;
    }

    public Long getLinkId() {
        return linkId;
    }

    public Long getLikeCount() {
        return likeCount;
    }
}
