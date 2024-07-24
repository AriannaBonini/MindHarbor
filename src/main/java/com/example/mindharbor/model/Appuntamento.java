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

    public Appuntamento(String data, String ora) {
        this.data = data;
        this.ora = ora;
    }

    public Appuntamento(Integer idAppuntamento, Paziente paziente, Integer notificaRichiesta) {
        this.idAppuntamento = idAppuntamento;
        this.paziente = paziente;
        this.notificaRichiesta=notificaRichiesta;
    }

    public Appuntamento(String data, String ora, Integer idAppuntamento, Paziente paziente, Psicologo psicologo) {
        this(data, ora, idAppuntamento, paziente, psicologo, null);
    }

    public Appuntamento(Paziente paziente, Psicologo psicologo) {
        this.paziente=paziente;
        this.psicologo=psicologo;
    }

    public Appuntamento(Integer idAppuntamento) {
        this.idAppuntamento=idAppuntamento;
    }

    public Integer getIdAppuntamento() {
        return idAppuntamento;
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
}

