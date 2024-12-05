package com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.util.List;

public class ScegliTestController {

    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public List<TestBean> getListaTest() {return BoundaryMockAPI.testPiscologici();}
    public InfoUtenteBean getInfoPsicologo() {
        return new SetInfoUtente().getInfo();
    }
    public PazienteBean getPazienteSelezionato() {return navigator.getPazienteBean();}
    public void eliminaPazienteSelezionato() {navigator.deletePazienteBean();}

    public boolean controlloNumTest(Integer contatore,String nomeTest) throws DAOException{
        if (contatore !=1) {
            return false;
        }else {
            try {
                //Se il controllo va a buon fine, il controller applicativo chiede di inserire il nuovo test in persistenza
                //inoltre, elimina la bean pazienteSelezionato dal Navigator Singleton visto che da questo momento non servirà più.
                notificaTest(nomeTest);
                return true;
            }catch (DAOException e) {
                throw new DAOException(e.getMessage());
            }
        }

    }

    private void notificaTest(String nomeTest) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();
        try {
            testPsicologicoDAO.assegnaTest(new TestPsicologico((new Psicologo(SessionManager.getInstance().getCurrentUser().getUsername())), new Paziente(getPazienteSelezionato().getUsername()), nomeTest));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
