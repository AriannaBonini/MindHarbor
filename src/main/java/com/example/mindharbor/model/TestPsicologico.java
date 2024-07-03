package com.example.mindharbor.model;

import java.util.Date;

public class TestPsicologico {
    private Date data;
    private final Integer risultato;
    private Psicologo psicologo;
    private Paziente paziente;
    private String test;
    private final Integer svolto;

    public TestPsicologico(Date data, Integer risultato, Psicologo psicologo, Paziente paziente, String test, Integer svolto) {
        this.data = data;
        this.risultato = risultato;
        this.psicologo= psicologo;
        this.paziente=paziente;
        this.test=test;
        this.svolto=svolto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getRisultato() {
        return risultato;
    }

    public Psicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Integer getSvolto() {
        return svolto;
    }
}
