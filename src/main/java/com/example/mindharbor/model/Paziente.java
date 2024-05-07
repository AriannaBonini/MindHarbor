package com.example.mindharbor.model;

public class Paziente {
    private String nome;
    private String cognome;
    private String genere;
    private String username;
    private Integer età;
    private String diagnosi;


    // Costruttore
    public Paziente(String nome, String cognome, String genere, String username,Integer età,String diagnosi) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
        this.username=username;
        this.età=età;
        this.diagnosi=diagnosi;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEtà() {
        return età;
    }

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