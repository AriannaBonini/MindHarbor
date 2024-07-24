package com.example.mindharbor.beans;

public class PazientiBean {
    private String nome;
    private String cognome;
    private String genere;
    private Integer anni;
    private String diagnosi;
    private String username;
    private Integer numTest;

    public PazientiBean(String username, Integer numTest, String nome, String cognome, String genere) {
        this.username=username;
        this.numTest=numTest;
        this.nome=nome;
        this.cognome=cognome;
        this.genere=genere;
    }

    public PazientiBean(String nome, String cognome, String genere, Integer anni) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
        this.anni = anni;
    }


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
    public void setAnni(Integer anni){this.anni=anni;}

    public String getDiagnosi() {
        return diagnosi;
    }
    public void setDiagnosi(String diagnosi){this.diagnosi=diagnosi;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumTest() {
        return numTest;
    }
    public void setNumTest(Integer numTest) {this.numTest = numTest;}
}

