package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPICalendario;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.sql.SQLException;

public class VerificaDisponibilitàController {

    public HomeInfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}

    public Integer getRichiesta() {return Integer.valueOf(NavigatorSingleton.getInstance().getParametro());}

    public void modificaStatoNotifica(Integer idRichiesta) throws DAOException {
        try {
            new AppuntamentoDAO().updateStatoNotifica(idRichiesta);
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean getRichiestaAppuntamento(Integer idAppuntamento) throws DAOException {
        AppuntamentiBean richiestaBean;
        try {
            Appuntamento richiesta = new AppuntamentoDAO().getInfoRichiesta(idAppuntamento);

            richiestaBean= new AppuntamentiBean(richiesta.getData(),
                    richiesta.getOra(),
                    "",
                    "",
                    richiesta.getPaziente().getNome(),
                    richiesta.getPaziente().getCognome(),
                    "",
                    "",
                    null,
                    0,
                    richiesta.getPaziente().getGenere(),
                    null);

            return richiestaBean;
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean verificaDisponibilità(Integer idAppuntamento) throws DAOException {
        try {
            if(!new AppuntamentoDAO().getDisponibilità(idAppuntamento,SessionManager.getInstance().getCurrentUser().getUsername())) {
                return false;
            }
            return BoundaryMockAPICalendario.Calendario();

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaAccettata(Integer idAppuntamento) throws DAOException {
        try {
            new AppuntamentoDAO().updateRichiesta(idAppuntamento);

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaRifiutata(Integer idAppuntamento) throws DAOException {
        try {
            new AppuntamentoDAO().deleteRichiesta(idAppuntamento);

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
