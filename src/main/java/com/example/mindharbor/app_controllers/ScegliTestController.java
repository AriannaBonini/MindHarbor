package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.sql.SQLException;
import java.util.List;

public class ScegliTestController {

    public  PazientiBean getInfoPaziente(String username) throws DAOException {
        PazientiBean pazienteBean;
        try {
            Paziente paziente = new PazienteDAO().getInfoPaziente(new Utente(username));

            pazienteBean = new PazientiBean(
                    paziente.getNome(),
                    paziente.getCognome(),
                    paziente.getGenere(),
                    paziente.getEtà(),
                    "",
                    ""
            );

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return pazienteBean;
    }
    public List<String> getListaTest() {
        return BoundaryMockAPI.TestPiscologici();
    }
    public HomeInfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }
    public String getUsername() {return NavigatorSingleton.getInstance().getParametro();}
    public void setUsername(String username) {NavigatorSingleton.getInstance().setParametro(username);}

    public void NotificaTest(String usernamePaziente, String nomeTest) throws DAOException {
        try {
            new TestPsicologicoDAO().assegnaTest(new TestPsicologico(null, null, (new Psicologo(SessionManager.getInstance().getCurrentUser().getUsername())), new Paziente(usernamePaziente), nomeTest, null));
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
