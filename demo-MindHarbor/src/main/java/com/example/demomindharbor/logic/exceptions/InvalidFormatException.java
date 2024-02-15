package com.example.demomindharbor.logic.exceptions;

public class InvalidFormatException extends Exception{
    private static final long serialVersionUID = 1L;        //non capisco perchè 1L

    public InvalidFormatException() {
        super("I campi sono vuoti");
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidFormatException(String message) {
        super(message);
    }
}
