package com.example.mindharbor.beans;

public class DomandeTestBean {

    private String domanda;
    private Integer punteggio;

    public DomandeTestBean(String domanda, Integer punteggio) {
        this.domanda= domanda;
        this.punteggio= punteggio;
    }

    public String getDomanda() {
        return domanda;
    }

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

}
