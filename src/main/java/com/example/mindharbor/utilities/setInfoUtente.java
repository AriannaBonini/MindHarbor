package com.example.mindharbor.utilities;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.session.SessionManager;

public class setInfoUtente {
    public InfoUtenteBean getInfo() {
        SessionManager sessionManager = SessionManager.getInstance();

        return new InfoUtenteBean(
                sessionManager.getCurrentUser().getNome(),
                sessionManager.getCurrentUser().getCognome()
        );
    }

}
