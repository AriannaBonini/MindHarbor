package com.example.demomindharbor.logic.app_controllers;

import com.example.demomindharbor.logic.enums.UserTypes;
import com.example.demomindharbor.logic.exceptions.SessionUserException;
import com.example.demomindharbor.logic.model.User;
import com.example.demomindharbor.logic.session.SessionManager;
//questo absController serve per gli altri controller, gli fornisce delle operazioni utili a tutti loro per gestire
// le sessioni e per ottenere il tipo di utente che attualmente usa quella determinata sessione.

public abstract class absController {
    protected void storeSessionUser(String username, String name, String surname, UserTypes userTypes) throws SessionUserException{
        SessionManager sessionManager = SessionManager.getInstance();
        User currentUser = new User(username, name, surname, userTypes);
        sessionManager.login(currentUser);
    }
    public static UserTypes getCurrentUserType(){
        return SessionManager.getInstance().getCurrentUser().getUserType();
    }
}
