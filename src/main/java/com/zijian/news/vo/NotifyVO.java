package com.zijian.news.vo;

import com.zijian.news.dao.Notify;
import com.zijian.news.dao.NotifyType;

public class NotifyVO {
    private Long id;
    private String username;
    private Long linkId;
    private String notifyType;
    private Integer unread;

    public String getType(Integer type) {
        if (type.equals(NotifyType.AT)) {
            return "AT";
        } else if (type.equals(NotifyType.COMMENT)) {
            return "COMMENT";
        } else {
            return "unknown";
        }
    }
    public NotifyVO(Notify notify, String username) {
        this.id = notify.getId();
        this.username = username;
        this.linkId = notify.getLinkId();
        this.notifyType = getType(notify.getNotifyType());
        this.unread = notify.getUnread();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getLinkId() {
        return linkId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public Integer getUnread() {
        return unread;
    }
}
