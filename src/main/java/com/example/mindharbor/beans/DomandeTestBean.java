package com.example.mindharbor.beans;

import java.util.List;

public class DomandeTestBean {
    private List<String> domande;
    private List<Integer> punteggi;

    public DomandeTestBean(List<String> domande, List<Integer> punteggi) {
        this.domande= domande;
        this.punteggi= punteggi;
    }

    public List<String> getDomande() {
        return domande;
    }

    public void setDomande(List<String> domande) {
        this.domande = domande;
    }

    public List<Integer> getPunteggi() {
        return punteggi;
    }

    public void setPunteggi(List<Integer> punteggi) {
        this.punteggi = punteggi;
    }

}
