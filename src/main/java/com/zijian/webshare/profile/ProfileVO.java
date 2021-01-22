package com.zijian.webshare.profile;

public class ProfileVO {
    private final String email;
    private final String bio;

    public ProfileVO(String email, String bio) {
        this.email = email;
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }
}
