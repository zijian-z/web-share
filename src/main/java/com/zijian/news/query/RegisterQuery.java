package com.zijian.news.query;

public class RegisterQuery {
    private String username;
    private String email;
    private String password;
    private String registerCode;


    public RegisterQuery(String username, String email, String password, String registerCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registerCode = registerCode;
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

    public String getRegisterCode() {
        return registerCode;
    }

    @Override
    public String toString() {
        return "RegisterQuery{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registerCode='" + registerCode + '\'' +
                '}';
    }
}
