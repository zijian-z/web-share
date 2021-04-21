package com.zijian.news.vo;

import com.zijian.news.dao.Profile;

public class ProfileVO {
    private Integer sex;
    private String bio;
    private String email;
    private boolean self;

    public ProfileVO() {
    }

    public ProfileVO(Profile profile, String email) {
        this.sex = profile.getSex();
        this.bio = profile.getBio();
        this.email = email;
        this.self = true;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    public Integer getSex() {
        return sex;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSelf() {
        return self;
    }
}
