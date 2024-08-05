package com.example.mindharbor.app_controllers.paziente;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.SetInfoUtente;


public class HomePazienteController {

    private PazientiBean pazientiBean;

    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}

    public PazientiBean cercaNuoviTestDaSvolgere() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();
        try {
            pazientiBean= new PazientiBean(testPsicologicoDAO.getNotificaPazientePerTestAssegnato(SessionManager.getInstance().getCurrentUser()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazientiBean;
    }

    public PazientiBean cercaNuoveTerapie() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TerapiaDAO terapiaDAO= daoFactoryFacade.getTerapiaDAO();
        try {
            pazientiBean=new PazientiBean(terapiaDAO.getNuoveTerapie(SessionManager.getInstance().getCurrentUser()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazientiBean;
    }

    public PazientiBean cercaNuoviAppuntamenti() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        try {
            pazientiBean=new PazientiBean(appuntamentoDAO.getNumRicAppDaNotificare(SessionManager.getInstance().getCurrentUser()));
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
