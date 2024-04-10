package com.example.mindharbor.beans;

public class PazientiBean {

    private String nome;
    private String cognome;
    private String genere;

    public  PazientiBean() { }

    public PazientiBean(String nome, String cognome,String genere) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
    }

    // Metodi getter e setter per il nome e il cognome
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
}

