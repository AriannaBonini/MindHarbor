package com.example.mindharbor.beans;

public class TestResultBean {
    private Integer risultatoUltimoTest;
    private Double risultatoTestPrecedente;

    public TestResultBean() {}

    public TestResultBean(Integer risultatoUltimoTest, Double risultatoTestPrecedente) {
        this.risultatoUltimoTest=risultatoUltimoTest;
        this.risultatoTestPrecedente=risultatoTestPrecedente;
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
