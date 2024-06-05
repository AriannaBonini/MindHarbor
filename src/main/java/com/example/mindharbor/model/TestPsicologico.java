package com.example.mindharbor.model;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class TestPsicologico {
    private String data;
    private Integer risultato;
    private String psicologo;
    private String paziente;
    private String test;
    private Integer stato;

    public TestPsicologico(String data, Integer risultato, String psicologo, String paziente, String test, Integer stato) {
        this.data = data;
        this.risultato = risultato;
        this.psicologo= psicologo;
        this.paziente=paziente;
        this.test=test;
        this.stato=stato;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getRisultato() {
        return risultato;
    }
    public void setRisultato(Integer risultato) {
        this.risultato = risultato;
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

    public String getTest() {
        return test;
    }
    public void setTest(String test) {
        this.test = test;
    }

    public Integer getStato() {
        return stato;
    }
    public void setStato(Integer stato) {
        this.stato = stato;
    }

















}
