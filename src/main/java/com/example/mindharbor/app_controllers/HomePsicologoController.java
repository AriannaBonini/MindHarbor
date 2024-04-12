package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

public class HomePsicologoController extends setInfoUtente{

    public HomeInfoUtenteBean getHomepageInfo() {

        SessionManager.getInstance().isSessionOpen();

        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public void logout() throws SessionUserException {
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.logout();

        SessionManager.getInstance().isSessionOpen();
    }


}
