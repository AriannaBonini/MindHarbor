package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.SetInfoUtente;


public class HomePazienteController {

    private PazientiBean pazientiBean;

    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}

    public PazientiBean cercaNuoviTestDaSvolgere() throws DAOException {
        try {
            pazientiBean= new PazientiBean(new TestPsicologicoDAO().getNotificaPazientePerTestAssegnato(SessionManager.getInstance().getCurrentUser()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazientiBean;
    }

    public PazientiBean cercaNuoveTerapie() throws DAOException {
        try {
            pazientiBean=new PazientiBean(new TerapiaDAO().getNuoveTerapie(SessionManager.getInstance().getCurrentUser()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazientiBean;
    }

    public PazientiBean cercaNuoviAppuntamenti() throws DAOException {
        try {
            pazientiBean=new PazientiBean(new AppuntamentoDAO().getNumRicAppDaNotificare(SessionManager.getInstance().getCurrentUser()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazientiBean;
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }
}
