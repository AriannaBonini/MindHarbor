package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;

import java.util.ArrayList;
import java.util.List;

public class ListaTestController {
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}
    public void setTestSelezionato(TestBean testSelezionato) {navigator.setTestBean(testSelezionato);}

    public List<TestBean> getListaTest() throws DAOException {
        List<TestBean> testBeanList = new ArrayList<>();

        if (SessionManager.getInstance().getUsernamePsicologo() != null) {
            try {
                List<TestPsicologico> listaTest = new TestPsicologicoDAO().trovaListaTest(SessionManager.getInstance().getCurrentUser());

                for (TestPsicologico test : listaTest) {
                    TestBean testBean = new TestBean(
                            test.getTest(),
                            test.getRisultato(),
                            test.getData(),
                            test.getSvolto());

                    testBeanList.add(testBean);
                }
            } catch (DAOException e) {
                throw new DAOException(e.getMessage());
            }
        }
        return testBeanList;
    }

    public void modificaStatoTest() throws DAOException {
        try {
            new TestPsicologicoDAO().modificaStatoNotificaTest(SessionManager.getInstance().getCurrentUser(),null);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public InfoUtenteBean infoPsicologo() throws DAOException {
        if(SessionManager.getInstance().getUsernamePsicologo()==null) {
            return null;
        }
        try {
            Utente utente= new UtenteDao().trovaNomeCognome(new Utente(SessionManager.getInstance().getUsernamePsicologo()));
            return new InfoUtenteBean(utente.getNome(),utente.getCognome());
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

}
