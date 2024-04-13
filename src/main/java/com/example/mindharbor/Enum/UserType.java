package com.example.mindharbor.Enum;

public enum UserType{
    PAZIENTE("Paziente"),
    PSICOLOGO("Psicologo");

    private final String id;

    private UserType(String id) {

        this.id = id;
    }

    public UserType fromString(String id) {
        for (UserType type : values()) {
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
