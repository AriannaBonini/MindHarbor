package com.example.mindharbor.eccezioni;

import java.io.Serial;

public class SessionUserException extends Exception{

    @Serial
    private static final long serialVersionUID = 3L;

    public SessionUserException(){
        super("The user hasn't been defined yet");
    }

    public SessionUserException(String messaggio) {
        super(messaggio);
    }
}