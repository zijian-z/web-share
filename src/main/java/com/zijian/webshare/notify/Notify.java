package com.zijian.webshare.notify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zijian.webshare.comment.Comment;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.user.User;

import javax.persistence.*;

@Entity(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private final User createUser;
    @ManyToOne
    @JoinColumn(name = "consume_user_id")
    private final User consumeUser;
    @ManyToOne
    @JoinColumn(name = "link_id")
    private final Link link;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private final Comment comment;
    @Enumerated(EnumType.STRING)
    private final NotifyType notifyType;
    private boolean unread;


    public Notify() {
        this.createUser = null;
        this.consumeUser = null;
        this.link = null;
        this.comment = null;
        this.notifyType = null;
        this.unread = true;
    }

    public Notify(User createUser, User consumeUser, Link link, Comment comment, NotifyType notifyType, boolean unread) {
        this.createUser = createUser;
        this.consumeUser = consumeUser;
        this.link = link;
        this.comment = comment;
        this.notifyType = notifyType;
        this.unread = unread;
    }

    public Long getId() {
        return id;
    }

    public User getCreateUser() {
        return createUser;
    }

    public User getConsumeUser() {
        return consumeUser;
    }

    public Link getLink() {
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

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
