package com.example.mindharbor.beans;

public class TestResultBean {
    private Integer risultatoUltimoTest;
    private Double risultatoTestPrecedente;

    public TestResultBean() {
        // Questo costruttore è vuoto perché non sono necessarie operazioni iniziali specifiche.
    }

    public Integer getRisultatoUltimoTest() {
        return risultatoUltimoTest;
    }

    public void setRisultatoUltimoTest(Integer risultatoUltimoTest) {
        this.risultatoUltimoTest = risultatoUltimoTest;
    }

    public Double getRisultatoTestPrecedente() {
        return risultatoTestPrecedente;
    }

    public void setRisultatoTestPrecedente(Double risultatoTestPrecedente) {
        this.risultatoTestPrecedente = risultatoTestPrecedente;
    }
}
