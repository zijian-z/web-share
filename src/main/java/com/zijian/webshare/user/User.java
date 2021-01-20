package com.zijian.webshare.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "user")
public class User {
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    public User(String username) {
        this.username = username;
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
