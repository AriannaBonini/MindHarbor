package com.example.demomindharbor.logic.exceptions;

public class SessionUserException extends Exception{
    private static final long serialVersionUID = 3L;

    public SessionUserException(){

        super("Utente non ancora definito!");
    }

    public SessionUserException(Throwable cause){

        super(cause);
    }

    public SessionUserException(String message) {

        super(message);
    }
}
