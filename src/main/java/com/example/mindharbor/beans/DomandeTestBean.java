package com.example.mindharbor.beans;

import java.util.List;

public class DomandeTestBean {
    private final List<String> domande;
    private final List<Integer> punteggi;

    public DomandeTestBean(List<String> domande, List<Integer> punteggi) {
        this.domande= domande;
        this.punteggi= punteggi;
    }

    public List<String> getDomande() {
        return domande;
    }

    public List<Integer> getPunteggi() {
        return punteggi;
    }

}
