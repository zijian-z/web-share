package com.zijian.webshare.profile;

import com.zijian.webshare.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private final User user;
    private String email;
    private String bio;

    public Profile() {
        this.user = null;
        this.email = null;
        this.bio = null;
    }

    public Profile(User user, String email, String bio) {
        this.user = user;
        this.email = email;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
