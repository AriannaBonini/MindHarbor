package com.example.mindharbor.app_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.patterns.Observer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginController extends abstractController{
    private static List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    protected static void notifyObservers(UserType userType) {
        for (Observer observer : observers) {
            observer.updateUserStatus(userType);
        }
    }


    public void login(LoginCredentialBean credenziali) throws DAOException, SQLException, SessionUserException {
        Utente utente= new UtenteDao().TrovaUtente(credenziali.getUsername(),credenziali.getPassword());

        if (utente!= null) {
            storeSessionUtente(utente.getUsername(), utente.getNome(), utente.getCognome(), utente.getUserType());

            if (utente.getUserType()==UserType.PAZIENTE){
                notifyObservers(utente.getUserType());
            }else if(utente.getUserType()==UserType.PSICOLOGO) {
                notifyObservers(utente.getUserType());
            }
        }else {
            //autenticazione fallita
        }
    }
}