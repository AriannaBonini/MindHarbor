package com.example.mindharbor.session;

import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
        private static SessionManager instance = null;

        protected List<Utente> utenteLoggato;

        protected Utente utenteCorrente;

        public Utente getCurrentUser(){
            return utenteCorrente;
        }

        public List<Utente> getLoggedUsers(){
            return utenteLoggato;
        }

        protected SessionManager() {
            utenteLoggato = new ArrayList<>();
            utenteCorrente = null;
        }

        public static synchronized SessionManager getInstance(){
            if(SessionManager.instance == null)
                SessionManager.instance = new SessionManager();

            return instance;
        }

        public synchronized void login(Utente utente) throws SessionUserException{
            try{
                for (Utente u: utenteLoggato) {
                    if (u.getUsername().equals(utente.getUsername())){
                        throw new SessionUserException("Utente già loggato");
                    }
                }
                utenteLoggato.add(utente);
            }
            finally {
                utenteCorrente = utente;
            }
        }

        public synchronized void logout(){
            utenteLoggato.remove(utenteCorrente);
            utenteCorrente = null;
        }

        public synchronized Utente changeCurrentUser(String username){
            for (Utente u: utenteLoggato) {
                if(u.getUsername().equals(username)){
                    utenteCorrente = u;
                    return u;
                }
            }
            return null;
        }

    public boolean isSessionOpen() {
        if (utenteCorrente != null) {
            return true;
        }else {
            return false;
        }
    }
}


