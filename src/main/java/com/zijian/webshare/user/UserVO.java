package com.zijian.webshare.user;

import javax.validation.constraints.NotBlank;

public class UserVO {
    @NotBlank
    private final String username;
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
    @NotBlank
    private final String code;

    public UserVO(String username, String email, String password, String code) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.code = code;
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

    public String getCode() {
        return code;
    }
}
