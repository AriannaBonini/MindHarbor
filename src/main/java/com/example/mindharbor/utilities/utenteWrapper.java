package com.example.mindharbor.utilities;

import com.example.mindharbor.model.Utente;

public class utenteWrapper {
    private Utente utente;
    private String usernamePsicologo;

    public utenteWrapper(Utente utente, String usernamePsicologo) {
        this.utente = utente;
        this.usernamePsicologo = usernamePsicologo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getUsernamePsicologo() {
        return usernamePsicologo;
    }

    public void setUsernamePsicologo(String usernamePsicologo) {
        this.usernamePsicologo = usernamePsicologo;
    }
}

