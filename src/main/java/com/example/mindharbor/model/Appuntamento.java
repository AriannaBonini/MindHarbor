package com.example.mindharbor.model;

public class Appuntamento {
    private String idAppuntamento;
    private String data;
    private String ora;
    private Paziente paziente;
    private Psicologo psicologo;


    public Appuntamento(String data, String ora, String idAppuntamento, Paziente paziente, Psicologo psicologo) {
        this.idAppuntamento = idAppuntamento;
        this.data = data;
        this.ora = ora;
        this.paziente = paziente;
        this.psicologo = psicologo;

    }

    public String getIdAppuntamento() {
        return idAppuntamento;
    }

    public void setIdAppuntamento(String idAppuntamento) {
        this.idAppuntamento = idAppuntamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public Psicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }
}

