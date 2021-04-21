package com.zijian.news.query;

public class ProfileQuery {
    private String bio;
    private Integer sex;
    private String email;

    public ProfileQuery(String bio, Integer sex, String email) {
        this.bio = bio;
        this.sex = sex;
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public Integer getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }
}
