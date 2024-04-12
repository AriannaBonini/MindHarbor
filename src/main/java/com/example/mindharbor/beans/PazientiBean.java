package com.example.mindharbor.beans;

public class PazientiBean {

    private String nome;
    private String cognome;
    private String genere;
    private String età;
    private String diagnosi;

    public  PazientiBean() { }

    public PazientiBean(String nome, String cognome,String genere,String età,String diagnosi) {
        this.nome = nome;
        this.cognome = cognome;
        this.genere= genere;
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

    public String getEtà() {
        return età;
    }

    public void setEtà(String età) {
        this.età = età;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }
}

