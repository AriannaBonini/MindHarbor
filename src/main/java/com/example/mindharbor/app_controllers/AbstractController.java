package com.example.mindharbor.app_controllers;

import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.user_type.UserType;

public abstract class AbstractController {
    protected abstract void storeSessionUtente(String username, String nome, String cognome, UserType userType,String usernamePsicologo) throws SessionUserException;
    protected abstract void storeSessionUtente(String username, String nome, String cognome, UserType userType) throws SessionUserException;
}
