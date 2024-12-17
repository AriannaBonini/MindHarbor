package com.example.mindharbor.dao;

import com.example.mindharbor.eccezioni.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Utente;
import java.util.List;

public interface UtenteDAO {
    Utente trovaUtente(Utente credenzialiUtenteLogin) throws DAOException;
    Utente trovaNomeCognome(Utente utente) throws DAOException;
    List<Psicologo> listaUtentiDiTipoPsicologo(String usernamePsicologo) throws DAOException;
    List<Appuntamento> richiestaAppuntamentiInfoPaziente(List<Appuntamento> richiesteAppuntamenti) throws DAOException;
    Utente trovaInfoUtente(Utente paziente) throws DAOException;
}
