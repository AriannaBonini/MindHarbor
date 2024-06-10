package com.example.mindharbor.beans;

public class PazientiBean {
//aljehja
    private String nome;
    private String cognome;
    private String genere;
    private Integer età;
    private String diagnosi;
    private String username;

    public  PazientiBean() { }

    public PazientiBean(String nome, String cognome,String genere,Integer età,String diagnosi,String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
        this.età=età;
        this.diagnosi=diagnosi;
        this.username=username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

