package com.example.mindharbor.model;

import com.example.mindharbor.user_type.UserType;

import java.util.ArrayList;
import java.util.List;

public class Paziente extends Utente{

    private List<Object> parametri=new ArrayList<>();
    //0 diagnosi e 1 usernamePsicologo e 2 anni


    public Paziente(String username, String nome, String cognome, UserType userType, String genere, String password, List<Object> parametri) {
        super(username, nome, cognome, userType, genere, password);
        this.parametri=parametri;
    }

    public Paziente(String username, String nome, String cognome) {
        super(username,nome,cognome,UserType.PAZIENTE);
    }

    public Paziente(String username) { super(username); }

    public List<Object> getParametri() {
        return parametri;
    }

    public void setParametri(List<Object> parametri) {
        this.parametri = parametri;
    }
}