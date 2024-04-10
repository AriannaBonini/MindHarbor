package com.example.mindharbor.model;

public class Paziente {
    private String nome;
    private String cognome;
    private String genere;

    // Costruttore
    public Paziente(String nome, String cognome, String genere) {
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