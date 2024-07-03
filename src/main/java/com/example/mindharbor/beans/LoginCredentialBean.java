package com.example.mindharbor.beans;


public class LoginCredentialBean {
    private String username;
    private final String password;

    public LoginCredentialBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


}
