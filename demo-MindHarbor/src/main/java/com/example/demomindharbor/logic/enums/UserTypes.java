package com.example.demomindharbor.logic.enums;

public enum UserTypes {
    PAZIENTE("Paziente"),
    PSICOLOGO("Psicologo");

    private final String id;

    private UserTypes(String id) {

        this.id = id;
    }

    public UserTypes fromString(String id) {
        for (UserTypes type : values()) {
            if(type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
    public String getId() {
        return id;
    }
}
