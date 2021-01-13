package com.zijian.webshare.user;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {
    @GeneratedValue
    @Id
    private Long id;
    @NotBlank
    private final String username;
    @NotBlank
    //用户能post密码，但是所有返回的json不会出现密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;

    public User() {
        this.username = null;
        this.password = null;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
