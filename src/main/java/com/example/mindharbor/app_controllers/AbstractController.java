package com.example.mindharbor.app_controllers;

import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;

public class AbstractController {
    protected void storeSessionUtente(String username, String nome, String cognome, UserType userType) throws SessionUserException {

        SessionManager sessionManager = SessionManager.getInstance();
        Utente currentUser = new Utente(username, nome, cognome, userType);
        sessionManager.login(currentUser);
    }

    public static UserType getCurrentUserType(){
        return SessionManager.getInstance().getCurrentUser().getUserType();
    }
}
