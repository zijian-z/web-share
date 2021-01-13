package com.zijian.webshare.link;

import com.zijian.webshare.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Link {
    @GeneratedValue
    @Id
    private Long id;
    @NotBlank
    private final String uri;
    private final Long createTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private final User user;

    public Link(String uri, Long createTime, User user) {
        this.uri = uri;
        this.createTime = createTime;
        this.user = user;
    }

    public Link() {
        this.uri = null;
        this.createTime = null;
        this.user = null;
    }

    public Long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", createTime=" + createTime +
                ", user=" + user +
                '}';
    }
}
