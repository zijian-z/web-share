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
    @JsonIgnore
    @NotBlank
    private final String email;
    @JsonIgnore
    @NotBlank
    private final String password;

    public User() {
        this.username = null;
        this.email = null;
        this.password = null;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
