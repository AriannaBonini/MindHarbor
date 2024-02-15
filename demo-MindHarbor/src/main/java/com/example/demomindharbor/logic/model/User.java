package com.example.demomindharbor.logic.model;

import com.example.demomindharbor.logic.enums.UserTypes;

public class User {
    protected String username;
    protected String name;
    protected String surname;
    protected final UserTypes userType;
    public User(String username, String name, String surname, UserTypes userType) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.userType = userType;
    }
    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserTypes getUserType() {
        return userType;
    }
}
