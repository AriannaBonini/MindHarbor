package com.example.mindharbor.dao;

import com.example.mindharbor.eccezioni.DAOException;
import com.example.mindharbor.model.Terapia;
import com.example.mindharbor.model.Utente;

import java.util.List;

public interface TerapiaDAO {
    void insertTerapia(Terapia terapia) throws DAOException;
    List<Terapia> getTerapie(Utente utente) throws  DAOException;
    Integer getNuoveTerapie(Utente paziente) throws DAOException;
}
