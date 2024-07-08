package com.example.mindharbor.model;

import com.example.mindharbor.user_type.UserType;
import java.util.ArrayList;
import java.util.List;

public class Psicologo extends Utente {
    private List<Object> parametri= new ArrayList<>();

    //0 nomeStudio;
    //1 costoOrario;


    public Psicologo(List<Object> parametri, String username, String nome, String cognome, UserType userType, String genere, String password) {
        super(username, nome, cognome, userType, genere, password);
        this.parametri=parametri;
    }

    public Psicologo(String username, String nome, String cognome) {
        super(username,nome,cognome,UserType.PSICOLOGO);
    }
    public Psicologo(String username) { super(username);}

    public List<Object> getParametri() {
        return parametri;
    }

    public void setParametri(List<Object> parametri) {
        this.parametri = parametri;
    }
}
