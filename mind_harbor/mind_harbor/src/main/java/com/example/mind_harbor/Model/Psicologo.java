package com.example.mind_harbor.Model;

public class Psicologo extends Utente {
    private String nomeStudio;
    private double costoOrario;

    public Psicologo(){
        /*
        il costruttore vuoto si aggiunge per l'evenienza di
        voler creare un'istanza prima ancora di sapere i dati
        da inserire all'interno
         */
    }
    public Psicologo(String nomeStudio, Integer costoOrario){
        this.nomeStudio = nomeStudio;
        this.costoOrario = costoOrario;
    }

    public double getCostoOrario() {
        return costoOrario;
    }

    public void setCostoOrario(double costoOrario) {
        this.costoOrario = costoOrario;
    }

    public String getNomeStudio() {
        return nomeStudio;
    }

    public void setNomeStudio(String nomeStudio) {
        this.nomeStudio = nomeStudio;
    }
}
