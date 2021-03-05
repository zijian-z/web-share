package com.zijian.webshare.link;

import com.zijian.webshare.comment.Comment;
import com.zijian.webshare.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "link")
public class Link {
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id;
    @NotBlank
    private final String title;
    @NotBlank
    private final String uri;
    private final Long createTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private final User user;
    @OneToMany(mappedBy = "link")
    //mappedBy指向维护关系的对象的某个属性名
    private final List<Comment> comments;
    @Transient
    private boolean userLiked;

    /**
     *
     * @param uri 链接的uri
     * @param createTime 创建时间
     * @param user 创建者
     * @param comments
     */
    public Link(String title, String uri, Long createTime, User user, List<Comment> comments) {
        this.title = title;
        this.uri = uri;
        this.createTime = createTime;
        this.user = user;
        this.comments = comments;
    }

    public Link(String title, String uri, Long createTime, User user, List<Comment> comments, boolean userLiked) {
        this.title = title;
        this.uri = uri;
        this.createTime = createTime;
        this.user = user;
        this.comments = comments;
        this.userLiked = userLiked;
    }

    public Link() {
        this.title = null;
        this.uri = null;
        this.createTime = null;
        this.user = null;
        this.comments = null;
    }

    public String getTitle() {
        return title;
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

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
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
