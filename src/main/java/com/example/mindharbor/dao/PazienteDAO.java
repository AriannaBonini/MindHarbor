package com.example.mindharbor.dao;

import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;

import java.util.List;

public interface PazienteDAO {
    List<Paziente> trovaPazienti(Utente psicologo) throws DAOException;
    Paziente getInfoSchedaPersonale(Paziente pazienteSelezionato) throws DAOException;
    boolean checkAnniPaziente(Paziente paziente) throws DAOException;
    String getUsernamePsicologo(Utente paziente) throws DAOException;
    void aggiungiPsicologoAlPaziente(Appuntamento appuntamento) throws DAOException;
}
