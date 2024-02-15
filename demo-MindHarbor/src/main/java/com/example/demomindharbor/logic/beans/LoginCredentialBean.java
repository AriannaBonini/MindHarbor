package com.example.demomindharbor.logic.beans;

import com.example.demomindharbor.logic.exceptions.InvalidFormatException;
public class LoginCredentialBean {
    private final String username;
    private final String password;

    public LoginCredentialBean(String username, String password) throws InvalidFormatException {
        checkUsername(username);
        checkPassword(password);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void checkUsername(String username) throws  InvalidFormatException {
        if(username.isEmpty() || username.isBlank()) {
            throw new InvalidFormatException("Formato username scorretto");
        }
    }

    private void checkPassword(String password) throws InvalidFormatException {
        if(password.isEmpty() || password.isBlank() || password.length() > 8) {
            throw new InvalidFormatException("Formato password scorretto");
        }
    }
}
