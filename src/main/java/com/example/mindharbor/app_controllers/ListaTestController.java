package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaTestController {
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    public HomeInfoUtenteBean getInfoPaziente() {
       return new setInfoUtente().getInfo();
    }
    public void setNomeTestData(String nomeTest, Date data) {
        navigator.setParametro(nomeTest);
        navigator.setData(data);
    }

    public List<TestBean> getListaTest() throws DAOException {
        List<TestBean> testBeanList = new ArrayList<>();

        try {
            List<TestPsicologico> listaTest = new TestPsicologicoDAO().TrovaListaTest(SessionManager.getInstance().getCurrentUser().getUsername());


            for (TestPsicologico test : listaTest) {
                TestBean testBean = new TestBean(
                        test.getTest(),
                        null,
                        null,
                        test.getRisultato(),
                        test.getData(),
                        test.getSvolto());


                testBeanList.add(testBean);
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return testBeanList;
    }

    public void modificaStatoTest() throws DAOException {
        try {
            new TestPsicologicoDAO().modificaStatoNotificaTest(null, "paziente", SessionManager.getInstance().getCurrentUser().getUsername());
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public String ricercaPsicologo() throws DAOException {
        try {
            return new PazienteDAO().TrovaNomeCognomePsicologo(SessionManager.getInstance().getCurrentUser().getUsername());
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

}
