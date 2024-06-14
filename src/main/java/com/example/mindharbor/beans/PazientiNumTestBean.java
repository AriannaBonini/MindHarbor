package com.example.mindharbor.beans;

public class PazientiNumTestBean {
    private String username;
    private Integer numTest;
    private String nome;
    private String cognome;
    private String genere;

    public PazientiNumTestBean() {}

    public PazientiNumTestBean(String username, Integer numTest, String nome, String cognome, String genere) {
        this.username=username;
        this.numTest=numTest;
        this.nome=nome;
        this.cognome=cognome;
        this.genere=genere;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumTest() {
        return numTest;
    }

    public void setNumTest(Integer numTest) {
        this.numTest = numTest;
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
}
