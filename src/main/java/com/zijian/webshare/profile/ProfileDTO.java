package com.zijian.webshare.profile;

import com.zijian.webshare.user.User;

public class ProfileDTO {
    private final boolean self;
    private String email;
    private final String bio;

    public ProfileDTO(String bio) {
        this.self = false;
        this.bio = bio;
    }

    public ProfileDTO(String email, String bio) {
        this.self = true;
        this.email = email;
        this.bio = bio;
    }

    public boolean isSelf() {
        return self;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}
