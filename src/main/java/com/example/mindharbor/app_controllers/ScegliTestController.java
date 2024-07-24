package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.util.List;

public class ScegliTestController {

    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public List<String> getListaTest() {
        return BoundaryMockAPI.TestPiscologici();
    }
    public InfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }
    public PazientiBean getPazienteSelezionato() {return navigator.getPazienteBean();}
    public void eliminaPazienteSelezionato() {navigator.deletePazienteBean();}

    public boolean controlloNumTest(Integer contatore,String nomeTest) throws DAOException{
        if (contatore !=1) {
            return false;
        }else {
            try {
                //Se il controllo va a buon fine, il controller applicativo chiede di inserire il nuovo test in persistenza
                //inoltre, elimina la bean pazienteSelezionato dal Navigator Singleton visto che da questo momento non servirà più.
                notificaTest(nomeTest);
                eliminaPazienteSelezionato();
                return true;
            }catch (DAOException e) {
                throw new DAOException(e.getMessage());
            }
        }

    }

    private void notificaTest(String nomeTest) throws DAOException {
        try {
            new TestPsicologicoDAO().assegnaTest(new TestPsicologico((new Psicologo(SessionManager.getInstance().getCurrentUser().getUsername())), new Paziente(getPazienteSelezionato().getUsername()), nomeTest));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
