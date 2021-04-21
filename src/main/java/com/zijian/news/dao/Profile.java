package com.zijian.news.dao;


public class Profile {
    private Long id;
    private Long userId;
    private Integer sex;
    private String bio;

    public Profile(Long userId) {
        this.id = null;
        this.userId = userId;
        this.sex = null;
        this.bio = null;
    }

    public Profile(Long userId, Integer sex, String bio) {
        this.id = null;
        this.userId = userId;
        this.sex = sex;
        this.bio = bio;
    }

    public Profile(Long id, Long userId, Integer sex, String bio) {
        this.id = id;
        this.userId = userId;
        this.sex = sex;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getSex() {
        return sex;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", sex=" + sex +
                ", bio='" + bio + '\'' +
                '}';
    }
}
