package com.example.mindharbor.model;

import com.example.mindharbor.Enum.UserType;

public class Paziente extends Utente{
    private Integer età;
    private String diagnosi;
    private String usernamePsicologo;


    public Paziente(Integer età, String diagnosi, String username, String nome, String cognome, UserType userType, String genere, String password, String usernamePsicologo) {
        super(username, nome, cognome, userType, genere, password);
        this.età=età;
        this.diagnosi=diagnosi;
        this.usernamePsicologo=usernamePsicologo;
    }

    public Paziente(String username, String nome, String cognome) {
        super(username,nome,cognome,UserType.PAZIENTE);
    }

    public Paziente(String username) { super(username); }

    public Integer getEtà() {return età;}
    public void setEtà(Integer età) {
        this.età = età;
    }
    public String getDiagnosi() {
        return diagnosi;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }
}