package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPICalendario;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import wiremock.org.checkerframework.checker.units.qual.A;

public class VerificaDispController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public InfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}

    public AppuntamentiBean getRichiestaAppuntamentoSelezionato() {return navigator.getAppuntamentoBean();}
    public void deleteRichiestaAppuntamentoSelezionato() {navigator.deleteAppuntamentoBean();}

    public void modificaStatoNotifica(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        try {
            new AppuntamentoDAO().updateStatoNotifica(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean getRichiestaAppuntamento(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        Appuntamento richiesta;
        try {
            richiesta = new AppuntamentoDAO().getInfoRichiesta(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));

            richiestaAppuntamento.setOra(richiesta.getOra());
            richiestaAppuntamento.setData(richiesta.getData());

            return richiestaAppuntamento;
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean verificaDisp(Integer idAppuntamento) throws DAOException {
        try {
            if(!new AppuntamentoDAO().getDisp(idAppuntamento,SessionManager.getInstance().getCurrentUser())) {
                return false;
            }
            return BoundaryMockAPICalendario.Calendario();

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaAccettata(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        try {
            new AppuntamentoDAO().updateRichiesta(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));

            //eliminiamo tutte le altre richieste di appuntamento del paziente ad altri psicologi.
            new AppuntamentoDAO().eliminaRichiesteDiAppuntamentoPerAltriPsicologi(new Appuntamento(new Paziente(richiestaAppuntamento.getPaziente().getUsername()),new Psicologo(richiestaAppuntamento.getPsicologo().getUsername())));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaRifiutata(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        try {
            new AppuntamentoDAO().eliminaRichiesta(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
