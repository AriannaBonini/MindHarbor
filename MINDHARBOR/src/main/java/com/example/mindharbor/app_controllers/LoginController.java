package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.model.Utente;

import java.sql.SQLException;

public class LoginController {
    public static void login(LoginCredentialBean credenziali) throws DAOException, SQLException {
        Utente utente= new UtenteDao().TrovaUtente(credenziali.getUsername(),credenziali.getPassword());

        if (utente!= null) {
            //autenticazione avvenuta con successo
        }else {
            //autenticazione fallita
        }
    }
}