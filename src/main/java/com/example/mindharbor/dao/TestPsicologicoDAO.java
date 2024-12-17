package com.example.mindharbor.dao;

import com.example.mindharbor.eccezioni.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;

import java.util.List;

public interface TestPsicologicoDAO {
    void assegnaTest(TestPsicologico test) throws DAOException;
    Integer getNotificaPazientePerTestAssegnato(Utente paziente) throws DAOException;
    void modificaStatoNotificaTest(Utente utente, Paziente pazienteSelezionato) throws DAOException;
    List<TestPsicologico> trovaListaTest(Utente paziente) throws DAOException;
    Integer trovaTestPassati(TestPsicologico testDaAggiungere) throws DAOException;
    Integer getNumTestSvoltiDaNotificare(Utente psicologo) throws DAOException;
    Integer getNumTestSvoltiSenzaPrescrizione(Utente utentePsicologo, Paziente paziente) throws DAOException;
    List<TestPsicologico> listaTestSvoltiSenzaPrescrizione(String usernamePaziente, String usernamePsicologo) throws DAOException;
    Paziente numTestSvoltiPerPaziente(Utente paziente) throws DAOException;
    Integer getNumTestAssegnato(Paziente paziente) throws DAOException;

}
