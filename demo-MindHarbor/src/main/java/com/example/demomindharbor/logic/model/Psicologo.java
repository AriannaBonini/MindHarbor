package com.example.demomindharbor.logic.model;

import com.example.demomindharbor.logic.enums.UserTypes;
public class Psicologo extends User{
    private Integer costo_orario;
    private String studio;

    public Psicologo(String username, String  name, String surname, UserTypes userType, Integer costo_orario, String studio) {
        super(username, name, surname, userType);
        this.costo_orario = costo_orario;
        this.studio = studio;
    }

    public Integer getCosto_orario() {
        return costo_orario;
    }

    public String getStudio() {
        return studio;
    }
}
