package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.SetInfoUtente;

public class HomePsicologoController extends SetInfoUtente {
    private PsicologoBean psicologoBean;

    public InfoUtenteBean getInfoPsicologo() {
        return new SetInfoUtente().getInfo();
    }

    public PsicologoBean cercaNuoviTestSvolti() throws DAOException {
        try {
             psicologoBean=new PsicologoBean(new TestPsicologicoDAO().getNumTestSvoltiDaNotificare(SessionManager.getInstance().getCurrentUser()));
        } catch (DAOException e ) {
            throw new DAOException(e.getMessage());
        }
        return psicologoBean;
    }

    public PsicologoBean cercaRichiesteAppuntamenti() throws DAOException {
        try {
            psicologoBean= new PsicologoBean(new AppuntamentoDAO().getNumRicAppDaNotificare(SessionManager.getInstance().getCurrentUser()));
        } catch (DAOException e ) {
            throw new DAOException(e.getMessage());
        }
        return psicologoBean;
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }
}
