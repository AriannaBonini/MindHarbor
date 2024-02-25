package com.example.mind_harbor.Model;

public class Paziente extends Utente {
    private Integer eta;
    private String codiceFiscale;

    public Paziente(){

    }
    public Paziente(Integer eta, String codiceFiscale) {
        this.eta = eta;
        this.codiceFiscale = codiceFiscale;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
}

