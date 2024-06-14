package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class HomePsicologoController extends setInfoUtente{

    public HomeInfoUtenteBean getHomepageInfo() {

        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public void logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }

    public Integer cercaNuoviTestSvolti() throws SQLException {
        int count= new TestPsicologicoDAO().getTestSvolto(SessionManager.getInstance().getCurrentUser().getUsername(), "");
        return count;
    }
}
