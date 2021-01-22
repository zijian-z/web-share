package com.zijian.webshare.notify;

import com.zijian.webshare.comment.Comment;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.user.User;


public class NotifyDTO {
    static class NotifyLink {
        private final Long id;
        private final String title;

        public NotifyLink(Link link) {
            this.id = link.getId();
            this.title = link.getTitle();
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    private final Long id;
    private final NotifyLink link;
    private final Comment comment;
    private final NotifyType notifyType;
    private final boolean unread;

    public NotifyDTO(Notify notify) {
        this.id = notify.getId();
        this.link = new NotifyLink(notify.getLink());
        this.comment = notify.getComment();
        this.notifyType = notify.getNotifyType();
        this.unread = notify.isUnread();
    }

    public Long getId() {
        return id;
    }

    public NotifyLink getLink() {
        return link;
    }

    public Comment getComment() {
        return comment;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public boolean isUnread() {
        return unread;
    }
}
