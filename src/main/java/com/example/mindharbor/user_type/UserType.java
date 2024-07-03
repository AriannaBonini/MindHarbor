package com.example.mindharbor.user_type;

public enum UserType{
    PAZIENTE("Paziente"),
    PSICOLOGO("Psicologo");

    private final String id;

    UserType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
