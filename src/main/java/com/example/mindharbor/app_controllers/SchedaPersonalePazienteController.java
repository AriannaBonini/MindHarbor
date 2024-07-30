package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;

public class SchedaPersonalePazienteController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();


    public void deletePazienteSelezionato() {navigator.deletePazienteBean();}
    public InfoUtenteBean getInfoPsicologo() {
        return new SetInfoUtente().getInfo();
    }
    public PazientiBean getPazienteSelezionato(){return navigator.getPazienteBean();}
    public void setPazienteSelezionato(PazientiBean pazienteSelezionato) { navigator.setPazienteBean(pazienteSelezionato);}

    public PazientiBean getSchedaPersonale(PazientiBean pazienteSelezionato) throws DAOException {
        try {
            Paziente paziente = new PazienteDAO().getInfoSchedaPersonale(new Paziente(pazienteSelezionato.getUsername()));

            pazienteSelezionato.setAnni(paziente.getAnni());
            pazienteSelezionato.setDiagnosi(paziente.getDiagnosi());

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

        return pazienteSelezionato;
    }

    public Integer numTestSvoltiSenzaPrescrizione(PazientiBean pazienteSelezionato) throws DAOException {
        try {
            return new TestPsicologicoDAO().getNumTestSvoltiSenzaPrescrizione(SessionManager.getInstance().getCurrentUser(), new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Integer getNumTestOdiernoAssegnato(PazientiBean pazienteSelezionato) throws DAOException{
        try {
            return new TestPsicologicoDAO().getNumTestAssegnato(new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
