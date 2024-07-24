package com.example.mindharbor.beans;

public class AppuntamentiBean {
        private String data;
        private String ora;
        private PazientiBean paziente;
        private PsicologoBean psicologo;
        private Integer idAppuntamento;
        private Integer notificaRichiesta;


    public AppuntamentiBean() {
    }

    public AppuntamentiBean(String data, String ora, PazientiBean paziente, PsicologoBean psicologo, Integer idAppuntamento, Integer notificaRichiesta) {
        this.data=data;
        this.ora=ora;
        this.paziente=paziente;
        this.psicologo=psicologo;
        this.idAppuntamento=idAppuntamento;
        this.notificaRichiesta=notificaRichiesta;
    }

    public AppuntamentiBean(PazientiBean paziente, Integer idAppuntamento, Integer notificaRichiesta) {
        this.paziente=paziente;
        this.idAppuntamento=idAppuntamento;
        this.notificaRichiesta=notificaRichiesta;
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




    public Integer getNotificaRichiesta() {return notificaRichiesta;}

    public PazientiBean getPaziente() {
        return paziente;
    }

    public void setPaziente(PazientiBean paziente) {
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

    public void setIdAppuntamento(Integer idAppuntamento) {
        this.idAppuntamento = idAppuntamento;
    }
}
