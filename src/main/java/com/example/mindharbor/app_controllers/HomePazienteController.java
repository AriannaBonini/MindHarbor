package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class HomePazienteController {

    public HomeInfoUtenteBean getHomepageInfo() {

            HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
            return homeInfoUtente;
    }

    public int cercaNuoviTestDaSvolgere() throws SQLException {

        int count= new TestPsicologicoDAO().getTestAssegnato(SessionManager.getInstance().getCurrentUser().getUsername());

        return count;
    }

    public void Logout() {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }
}
