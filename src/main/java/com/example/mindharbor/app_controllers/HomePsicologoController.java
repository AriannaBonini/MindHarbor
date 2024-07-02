package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class HomePsicologoController extends setInfoUtente{

    public HomeInfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }

    public Integer cercaNuoviTestSvolti() throws DAOException {
        try {
             return new TestPsicologicoDAO().getNumTestSvoltiDaNotificare(SessionManager.getInstance().getCurrentUser().getUsername(), "");
        } catch (SQLException e ) {
            throw new DAOException(e.getMessage());
        }
    }

    public Integer cercaRichiesteAppuntamenti() throws DAOException {
        try {
            return new AppuntamentoDAO().getNumRicAppDaNotificare(SessionManager.getInstance().getCurrentUser().getUsername(),null);
        } catch (SQLException e ) {
            throw new DAOException(e.getMessage());
        }

    }
}
