package com.example.mindharbor.dao;

import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Utente;

import java.util.List;

public interface AppuntamentoDAO {
    List<Appuntamento> trovaAppuntamentiPaziente(Utente paziente, String selectedTabName) throws DAOException;
    List<Appuntamento> trovaAppuntamentiPsicologo(Utente psicologo, String selectedTabName) throws DAOException;
    void insertRichiestaAppuntamento(Appuntamento appuntamento) throws DAOException;
    Integer getNumRicAppDaNotificare(Utente utente) throws DAOException;
    List<Appuntamento> trovaRichiesteAppuntamento(Utente utente) throws DAOException;
    void updateStatoNotifica(Appuntamento richiestaAppuntamento) throws DAOException;
    Appuntamento getInfoRichiesta(Appuntamento richiestaAppuntamento) throws DAOException;
    void updateRichiesta(Appuntamento appuntamento) throws DAOException;
    void eliminaRichiesteDiAppuntamentoPerAltriPsicologi(Appuntamento appuntamento) throws DAOException;
    void eliminaRichiesta(Appuntamento appuntamento) throws DAOException;
    boolean getDisp(Integer idAppuntamento, Utente utente) throws DAOException;
    void aggiornaStatoNotificaPaziente(Utente utente) throws DAOException;



}
