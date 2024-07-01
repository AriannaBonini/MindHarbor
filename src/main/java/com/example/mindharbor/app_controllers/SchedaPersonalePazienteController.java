package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class SchedaPersonalePazienteController {

    public HomeInfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }
    public String getUsername(){return NavigatorSingleton.getInstance().getParametro();}
    public void setUsername(String username) { NavigatorSingleton.getInstance().setParametro(username);}

    public PazientiBean getSchedaPersonale(String username) throws DAOException {
        PazientiBean pazienteBean;
        try {
            Paziente paziente = new PazienteDAO().getInfoSchedaPersonale(username);

            pazienteBean = new PazientiBean(
                    paziente.getNome(),
                    paziente.getCognome(),
                    paziente.getGenere(),
                    paziente.getEtà(),
                    paziente.getDiagnosi(),
                    ""
            );
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return pazienteBean;
    }

    public int cercaNuoviTestSvoltiPazienteDaNotificare(String usernamePaziente) throws DAOException {
        try {
            return new TestPsicologicoDAO().getNumTestSvoltiDaNotificare(SessionManager.getInstance().getCurrentUser().getUsername(), usernamePaziente);
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }


    public int NumTestSvoltiSenzaPrescrizione(String usernamePaziente) throws DAOException {
        try {
            return new TestPsicologicoDAO().getNumTestSvoltiSenzaPrescrizione(SessionManager.getInstance().getCurrentUser().getUsername(), usernamePaziente);
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
