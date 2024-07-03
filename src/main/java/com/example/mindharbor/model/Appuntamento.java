package com.example.mindharbor.model;

public class Appuntamento {
    private Integer idAppuntamento;
    private String data;
    private String ora;
    private Paziente paziente;
    private Psicologo psicologo;
    private Integer notificaRichiesta;


    public Appuntamento(String data, String ora, Integer idAppuntamento, Paziente paziente, Psicologo psicologo, Integer notificaRichiesta) {
        this.idAppuntamento = idAppuntamento;
        this.data = data;
        this.ora = ora;
        this.paziente = paziente;
        this.psicologo = psicologo;
        this.notificaRichiesta=notificaRichiesta;
    }

    public Appuntamento(String data, String ora, Integer idAppuntamento, Paziente paziente, Psicologo psicologo) {
        this(data, ora, idAppuntamento, paziente, psicologo, null);
    }

    public Integer getIdAppuntamento() {
        return idAppuntamento;
    }

    public void setIdAppuntamento(Integer idAppuntamento) {
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

    public Integer getNotificaRichiesta() {return notificaRichiesta;}

    public void setNotificaRichiesta(Integer notificaRichiesta) {this.notificaRichiesta = notificaRichiesta;}
}

