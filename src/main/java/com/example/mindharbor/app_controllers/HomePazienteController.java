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

    public int cercaNuoviTest(String username) throws SQLException {

        int count= new TestPsicologicoDAO().getTestAssegnato(username);

        return count;
    }
}
