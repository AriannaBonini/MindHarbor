package com.example.mindharbor.beans;

import java.util.Date;

public class TestBean {

    private String nomeTest;
    private String psicologo;
    private String paziente;
    private Integer risultato;
    private Date data;

    public TestBean() {}

    public TestBean(String nomeTest, String psicologo, String paziente, Integer risultato, Date data ) {
        this.nomeTest=nomeTest;
        this.psicologo=psicologo;
        this.paziente=paziente;
        this.risultato=risultato;
        this.data=data;
    }

    public String getNomeTest() {
        return nomeTest;
    }
    public void setNomeTest(String nomeTest) {
        this.nomeTest = nomeTest;
    }

    public String getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(String psicologo) {
        this.psicologo = psicologo;
    }

    public String getPaziente() {
        return paziente;
    }

    public void setPaziente(String paziente) {
        this.paziente = paziente;
    }
    public Integer getRisultato() {
        return risultato;
    }
    public void setRisultato(Integer risultato) {this.risultato = risultato;}

    public Date getData() {
        return data;
    }

    public void setData(Date data) {this.data = data;}
}
