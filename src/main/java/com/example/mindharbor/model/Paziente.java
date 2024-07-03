package com.example.mindharbor.model;

import com.example.mindharbor.user_type.UserType;

public class Paziente extends Utente{
    private Integer anni;
    private String diagnosi;
    private String usernamePsicologo;


    public Paziente(Integer anni, String diagnosi, String username, String nome, String cognome, UserType userType, String genere, String password, String usernamePsicologo) {
        super(username, nome, cognome, userType, genere, password);
        this.anni = anni;
        this.diagnosi = diagnosi;
        this.usernamePsicologo = usernamePsicologo;
    }

    public Paziente(String username, String nome, String cognome) {
        super(username,nome,cognome,UserType.PAZIENTE);
    }

    public Paziente(String username) { super(username); }

    public Integer getAnni() {return anni;}
    public void setAnni(Integer anni) {
        this.anni = anni;
    }
    public String getDiagnosi() {
        return diagnosi;
    }

}