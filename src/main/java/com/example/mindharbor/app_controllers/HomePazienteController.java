package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class HomePazienteController {

    public HomeInfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}

    public Integer cercaNuoviTestDaSvolgere() throws DAOException{
        try {
            return new TestPsicologicoDAO().getNotificaPazientePerTestAssegnato(SessionManager.getInstance().getCurrentUser().getUsername());

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }

    public int cercaNuoveTerapie() throws DAOException {
        try {
            return new TerapiaDAO().getNuoveTerapie(SessionManager.getInstance().getCurrentUser().getUsername());
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public int cercaNuoviAppuntamenti() throws DAOException {
        try {
            return new AppuntamentoDAO().getNumRicAppDaNotificare(SessionManager.getInstance().getCurrentUser());
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
