package com.example.mindharbor.model;

import com.example.mindharbor.user_type.UserType;

public class Psicologo extends Utente {
    private Integer costoOrario;
    private String nomeStudio;


    public Psicologo(Integer costoOrario, String nomeStudio, String username, String nome, String cognome, UserType userType, String genere, String password) {
        super(username, nome, cognome, userType, genere, password);
        this.costoOrario=costoOrario;
        this.nomeStudio=nomeStudio;
    }

    public Psicologo(String username, String nome, String cognome) {
        super(username,nome,cognome,UserType.PSICOLOGO);
    }


    public Psicologo(String username) { super(username);}

    public Integer getCostoOrario() {
        return costoOrario;
    }

    public String getNomeStudio() {
        return nomeStudio;
    }
}
