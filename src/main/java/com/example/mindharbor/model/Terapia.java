package com.example.mindharbor.model;

import java.util.Date;

public class Terapia {
    private TestPsicologico testPsicologico;
    private String terapia;
    private Date dataTerapia;

    public Terapia(TestPsicologico testPsicologico, String terapia, Date dataTerapia ) {
        this.testPsicologico=testPsicologico;
        this.terapia=terapia;
        this.dataTerapia=dataTerapia;
    }

    public TestPsicologico getTestPsicologico() {
        return testPsicologico;
    }

    public void setTestPsicologico(TestPsicologico testPsicologico) {
        this.testPsicologico = testPsicologico;
    }

    public String getTerapia() {
        return terapia;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

    public Date getDataTerapia() {
        return dataTerapia;
    }

    public void setDataTerapia(Date dataTerapia) {
        this.dataTerapia = dataTerapia;
    }
}
