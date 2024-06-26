package com.example.mindharbor.beans;

public class PsicologoBean {
    private String username;
    private String nome;
    private String cognome;
    private Integer costoOrario;
    private String nomeStudio;
    private String genere;


    public PsicologoBean(String username, String nome, String cognome, Integer costoOrario, String nomeStudio,String genere) {
        this.username=username;
        this.nome=nome;
        this.cognome=cognome;
        this.costoOrario=costoOrario;
        this.nomeStudio=nomeStudio;
        this.genere=genere;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCognome() {return cognome;}

    public void setCognome(String cognome) {this.cognome = cognome;}
    public Integer getCostoOrario() {return costoOrario;}
    public void setCostoOrario(Integer costoOrario) {this.costoOrario = costoOrario;}

    public String getNomeStudio() {
        return nomeStudio;
    }

    public void setNomeStudio(String nomeStudio) {
        this.nomeStudio = nomeStudio;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
