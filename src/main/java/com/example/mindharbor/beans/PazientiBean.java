package com.example.mindharbor.beans;

public class PazientiBean {
    private String nome;
    private String cognome;
    private String genere;
    private final Integer anni;
    private final String diagnosi;
    private String username;


    public PazientiBean(String nome, String cognome, String genere, Integer anni, String diagnosi, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
        this.anni = anni;
        this.diagnosi=diagnosi;
        this.username=username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Integer getAnni() {
        return anni;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

