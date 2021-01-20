package com.zijian.webshare.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zijian.webshare.link.Link;
import com.zijian.webshare.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "comment")
public class Comment {
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id;
    @NotBlank
    private String content;
    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private User createUser;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;

    public Comment() {
    }

    public Comment(String content, User createUser, Link link) {
        this.content = content;
        this.createUser = createUser;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getCreateUser() {
        return createUser;
    }

    public Link getLink() {
        return link;
    }
}
