package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;

public class LoginController extends AbstractController {


    public InfoUtenteBean login(LoginCredentialBean credenziali) throws DAOException, SessionUserException {
        Utente credenzialiUtenteLogin= new Utente(credenziali.getUsername(), credenziali.getPassword());
        InfoUtenteBean infoUtente=null;
        try {
            Utente utente = new UtenteDao().trovaUtente(credenzialiUtenteLogin);
            if (utente != null) {
                infoUtente = new InfoUtenteBean(utente.getUserType());
                if (utente.getUserType().equals(UserType.PAZIENTE)) {
                    storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType(), new PazienteDAO().getUsernamePsicologo(utente));
                } else {
                    storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType());
                }
            }
            return infoUtente;
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
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