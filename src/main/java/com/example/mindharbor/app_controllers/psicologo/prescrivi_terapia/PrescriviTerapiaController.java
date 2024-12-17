package com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.*;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.util.ArrayList;
import java.util.List;

public class PrescriviTerapiaController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void deletePazienteSelezionato() {navigator.deletePazienteBean();}
    public InfoUtenteBean getInfoPsicologo() {
        return new SetInfoUtente().getInfo();
    }
    public PazienteBean getPazienteSelezionato() {return navigator.getPazienteBean();}
    public void azzeraIlNumeroDiTestSvolti() {navigator.getPazienteBean().setNumTestSvolti(0);}

    public void modificaStatoTestSvolto(PazienteBean pazienteSelezionato) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();
        try {
            testPsicologicoDAO.modificaStatoNotificaTest(SessionManager.getInstance().getCurrentUser(), new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
            }
    }

    public List<TestBean> getTestSvoltiSenzaPrescrizione(PazienteBean pazienteSelezionato) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();

        List<TestBean> testSvoltiBean= new ArrayList<>();
        try {
            List<TestPsicologico> testSvolti = testPsicologicoDAO.listaTestSvoltiSenzaPrescrizione(pazienteSelezionato.getUsername(), SessionManager.getInstance().getCurrentUser().getUsername());

            for (TestPsicologico test : testSvolti) {
                TestBean testSvoltoBean = new TestBean(test.getTest(),
                        "",
                        "",
                        test.getRisultato(),
                        test.getData(),
                        null);

                testSvoltiBean.add(testSvoltoBean);
            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return testSvoltiBean;
    }


    public void aggiungiTerapia(TerapiaBean terapiaBean) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TerapiaDAO terapiaDAO= daoFactoryFacade.getTerapiaDAO();
        try {
            Terapia terapia = new Terapia(new TestPsicologico(terapiaBean.getDataTest(), null, new Psicologo(terapiaBean.getPsicologo()), new Paziente(terapiaBean.getPaziente()), "", null), terapiaBean.getTerapia(), terapiaBean.getDataTerapia());
            terapiaDAO.insertTerapia(terapia);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
