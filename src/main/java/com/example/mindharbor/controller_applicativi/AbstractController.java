package com.example.mindharbor.controller_applicativi;

import com.example.mindharbor.eccezioni.SessionUserException;
import com.example.mindharbor.tipo_utente.UserType;

public abstract class AbstractController {
    protected abstract void storeSessionUtente(String username, String nome, String cognome, UserType userType,String usernamePsicologo) throws SessionUserException;
    protected abstract void storeSessionUtente(String username, String nome, String cognome, UserType userType) throws SessionUserException;
}
