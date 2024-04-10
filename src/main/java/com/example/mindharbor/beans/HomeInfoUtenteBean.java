package com.example.mindharbor.beans;

public class HomeInfoUtenteBean {
    private String nome;

    private String cognome;

    public HomeInfoUtenteBean(String nome,String cognome) {
        this.nome = nome;
        this.cognome= cognome;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

}
