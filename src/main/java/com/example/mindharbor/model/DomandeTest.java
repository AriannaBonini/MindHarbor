package com.example.mindharbor.model;

import java.util.List;

public class DomandeTest {

    private List<String> domande;
    private List<Integer> punteggi;

    public DomandeTest() {}

    public DomandeTest(List<String> domande, List<Integer> punteggi) {
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

    public void setPunteggio(List<Integer> punteggi) {
        this.punteggi = punteggi;
    }

}
