package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPICalendario;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class VerificaDispController {

    private Appuntamento richiesta;

    public HomeInfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}

    public Integer getRichiesta() {return Integer.valueOf(NavigatorSingleton.getInstance().getParametro());}

    public void modificaStatoNotifica(Integer idRichiesta) throws DAOException {
        try {
            new AppuntamentoDAO().updateStatoNotifica(idRichiesta);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean getRichiestaAppuntamento(Integer idAppuntamento) throws DAOException {
        AppuntamentiBean richiestaBean;
        try {
            richiesta = new AppuntamentoDAO().getInfoRichiesta(idAppuntamento);

            richiestaBean= new AppuntamentiBean(richiesta.getData(),
                    richiesta.getOra(),
                    new PazientiBean(richiesta.getPaziente().getNome(),richiesta.getPaziente().getCognome(),richiesta.getPaziente().getGenere(),0,"",richiesta.getPaziente().getUsername()),
                    null,
                    null,
                    null);
            return richiestaBean;
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

    public void richiestaAccettata() throws DAOException {
        try {
            new AppuntamentoDAO().updateRichiesta(richiesta);

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaRifiutata() throws DAOException {
        try {
            new AppuntamentoDAO().eliminaRichiesta(richiesta);

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
