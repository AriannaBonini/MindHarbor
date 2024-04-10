package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

public class HomePsicologoController {

    public HomeInfoUtenteBean getHomepageInfo() {

        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;

    }

}
