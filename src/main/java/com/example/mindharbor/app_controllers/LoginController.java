package com.example.mindharbor.app_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.patterns.ClassObserver;
import java.sql.SQLException;

public class LoginController extends abstractController{

    private ClassObserver observer= new ClassObserver();


    public void login(LoginCredentialBean credenziali) throws DAOException, SQLException, SessionUserException {
        Utente credenzialiUtenteLogin= new Utente(credenziali.getUsername(), credenziali.getPassword());

        Utente utente= new UtenteDao().TrovaUtente(credenzialiUtenteLogin);


        if (utente!= null) {
            storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType());

            if (utente.getUserType()==UserType.PAZIENTE){
                observer.notifyObservers(utente.getUserType());
            }else if(utente.getUserType()==UserType.PSICOLOGO) {
                observer.notifyObservers(utente.getUserType());
            }
        }
    }
}