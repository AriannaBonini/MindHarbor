package com.example.mindharbor.utilities;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.session.SessionManager;

public class setInfoUtente {
    public HomeInfoUtenteBean getInfo() {
        SessionManager sessionManager = SessionManager.getInstance();

        return new HomeInfoUtenteBean(
                sessionManager.getCurrentUser().getNome(),
                sessionManager.getCurrentUser().getCognome()
        );
    }

}
