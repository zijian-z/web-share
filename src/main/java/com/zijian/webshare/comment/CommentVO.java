package com.zijian.webshare.comment;

public class CommentVO {
    private final Long linkId;
    private final String content;

    public CommentVO(Long linkId, String content) {
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
