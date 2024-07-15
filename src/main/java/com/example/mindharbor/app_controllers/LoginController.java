package com.example.mindharbor.app_controllers;

import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.patterns.ClassObserver;
import java.sql.SQLException;

public class LoginController extends AbstractController {

    private final ClassObserver observer= new ClassObserver();


    public void login(LoginCredentialBean credenziali) throws DAOException, SQLException, SessionUserException {
        Utente credenzialiUtenteLogin= new Utente(credenziali.getUsername(), credenziali.getPassword());

        Utente utente= new UtenteDao().trovaUtente(credenzialiUtenteLogin);
        if(utente.getUserType().equals(UserType.PAZIENTE)) {
            storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType(), new PazienteDAO().getUsernamePsicologo(utente));
        }else {
            storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType());
        }

        if (utente != null) {
            if (utente.getUserType()==UserType.PAZIENTE){
                observer.notifyObservers(utente.getUserType());
            }else{
                observer.notifyObservers(utente.getUserType());
            }
        }
    }

    @Override
    protected void storeSessionUtente(String username, String nome, String cognome, UserType userType,String usernamePsicologo) throws SessionUserException {
        SessionManager sessionManager = SessionManager.getInstance();
        Utente currentUser = new Utente(username, nome, cognome, userType);
        sessionManager.login(currentUser,usernamePsicologo);
    }

    @Override
    protected void storeSessionUtente(String username, String nome, String cognome, UserType userType) throws SessionUserException {
        SessionManager sessionManager = SessionManager.getInstance();
        Utente currentUser = new Utente(username, nome, cognome, userType);
        sessionManager.login(currentUser,null);
    }



}