package com.example.mindharbor.model;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.Date;

public class TestPsicologico {
    private Date data;
    private Integer risultato;
    private String psicologo;
    private String paziente;
    private String test;

    public TestPsicologico(Date data, Integer risultato, String psicologo, String paziente, String test) {
        this.data = data;
        this.risultato = risultato;
        this.psicologo= psicologo;
        this.paziente=paziente;
        this.test=test;
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

}
