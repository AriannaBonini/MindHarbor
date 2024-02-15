package com.example.demomindharbor.logic.model;

import com.example.demomindharbor.logic.enums.UserTypes;

public class Paziente extends User {
    private  Integer eta;
    private String cf;

    public Paziente(String username, String name, String surname, UserTypes userType, Integer eta, String cf) {
        super(username, name, surname, userType);
        this.eta = eta;
        this.cf = cf;
    }

    public Integer getEta() {
        return eta;
    }

    public String getCf() {
        return cf;
    }
}
