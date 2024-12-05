package com.example.mindharbor.beans;

public class AppuntamentiBean {
        private String data;
        private String ora;
        private PazienteBean paziente;
        private PsicologoBean psicologo;
        private Integer idAppuntamento;
        private Integer notificaRichiesta;


    public AppuntamentiBean() {
    }

    public AppuntamentiBean(String data, String ora, PazienteBean paziente, PsicologoBean psicologo, Integer idAppuntamento, Integer notificaRichiesta) {
        this.data=data;
        this.ora=ora;
        this.paziente=paziente;
        this.psicologo=psicologo;
        this.idAppuntamento=idAppuntamento;
        this.notificaRichiesta=notificaRichiesta;
    }

    public AppuntamentiBean(String data, String ora, PazienteBean paziente) {
        this.data=data;
        this.ora=ora;
        this.paziente=paziente;
    }

    public AppuntamentiBean(String data, String ora, PsicologoBean psicologo) {
        this.data=data;
        this.ora=ora;
        this.psicologo=psicologo;
    }

    public AppuntamentiBean(PazienteBean paziente, Integer idAppuntamento, Integer notificaRichiesta) {
        this.paziente=paziente;
        this.idAppuntamento=idAppuntamento;
        this.notificaRichiesta=notificaRichiesta;
    }

    public String getData() {return data;}
    public void setData(String data) {
        this.data = data;
    }
    public String getOra() {
        return ora;
    }
    public void setOra(String ora) {
        this.ora = ora;
    }
    public Integer getNotificaRichiesta() {return notificaRichiesta;}
    public PazienteBean getPaziente() {
        return paziente;
    }
    public void setPaziente(PazienteBean paziente) {
        this.paziente = paziente;
    }
    public PsicologoBean getPsicologo() {
        return psicologo;
    }
    public void setPsicologo(PsicologoBean psicologo) {
        this.psicologo = psicologo;
    }
    public Integer getIdAppuntamento() {
        return idAppuntamento;
    }

}
