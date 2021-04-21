package com.zijian.news.dao;


public class Notify {
    private Long id;
    private Long userId;
    // 被评论的人和被@的人
    private Long atUserId;
    private Long linkId;
    private Long commentId;
    private Long createTime;
    private Integer notifyType;
    private Integer unread;

    public Notify(Long id, Long userId, Long linkId, Integer notifyType, Integer unread) {
        this.id = id;
        this.userId = userId;
        this.atUserId = null;
        this.linkId = linkId;
        this.commentId = null;
        this.createTime = null;
        this.notifyType = notifyType;
        this.unread = unread;
    }

    public Notify(Long userId, Long atUserId, Long linkId, Long commentId, Long createTime, Integer notifyType, Integer unread) {
        this.userId = userId;
        this.atUserId = atUserId;
        this.linkId = linkId;
        this.commentId = commentId;
        this.createTime = createTime;
        this.notifyType = notifyType;
        this.unread = unread;
    }

    public void setAtUserId(Long atUserId) {
        this.atUserId = atUserId;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAtUserId() {
        return atUserId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public Integer getUnread() {
        return unread;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "id=" + id +
                ", userId=" + userId +
                ", atUserId=" + atUserId +
                ", linkId=" + linkId +
                ", commentId=" + commentId +
                ", createTime=" + createTime +
                ", notifyType=" + notifyType +
                ", unread=" + unread +
                '}';
    }
}
