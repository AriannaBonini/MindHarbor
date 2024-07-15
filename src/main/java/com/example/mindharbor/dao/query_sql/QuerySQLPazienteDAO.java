package com.example.mindharbor.dao.query_sql;

public abstract class QuerySQLPazienteDAO {

    protected QuerySQLPazienteDAO() {/*Costruttore vuoto*/}
    protected static final String NOME= "Nome";
    protected static final String COGNOME= "Cognome";
    protected static final String GENERE= "Genere";
    protected static final String ETA= "Età";
    protected static final String DIAGNOSI= "Diagnosi";
    protected static final String USERNAME= "Username";
    protected static final String TABELLA_UTENTE= "utente";
    protected static final String TABELLA_PAZIENTE= "paziente";
    protected static final String PAZIENTE_USERNAME= "Paziente_Username";
    protected static final String PSICOLOGO_USERNAME= "Username_Psicologo";
    protected static final String TROVA_PAZIENTE = "SELECT P." + PAZIENTE_USERNAME + ", SUM(T.StatoPsicologo), " +
            "U." + NOME + ", U." + COGNOME + ", U." + GENERE + " " +
            "FROM " + TABELLA_PAZIENTE + " P " +
            "LEFT JOIN testpsicologico T ON P." + PAZIENTE_USERNAME + " = T.Paziente " +
            "JOIN " + TABELLA_UTENTE + " U ON U." + USERNAME + " = P." + PAZIENTE_USERNAME + " " +
            "WHERE P." + USERNAME + "_Psicologo = ? " +
            "GROUP BY P." + PAZIENTE_USERNAME;

    protected static final String INFO_SCHEDA_PERSONALE= "SELECT " +
            TABELLA_UTENTE + "." + NOME + ", " +
            TABELLA_UTENTE + "." + COGNOME + ", " +
            TABELLA_PAZIENTE + "." + ETA + ", " +
            TABELLA_PAZIENTE + "." + DIAGNOSI + ", " +
            TABELLA_UTENTE + "." + GENERE + " " +
            "FROM " + TABELLA_PAZIENTE + " " +
            "JOIN " + TABELLA_UTENTE + " ON " +
            TABELLA_UTENTE + "." + USERNAME + " = " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " " +
            "WHERE " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = ?";





}
