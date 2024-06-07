package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaTestController {
    public HomeInfoUtenteBean getPagePazInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public List<TestBean> getListaTest() throws SQLException {
        List<TestPsicologico> listaTest= new TestPsicologicoDAO().TrovaListaTest(SessionManager.getInstance().getCurrentUser().getUsername());

        List<TestBean> testBeanList= new ArrayList<>();

        for(TestPsicologico test: listaTest) {
            TestBean testBean= new TestBean(
                    test.getTest(),
                    test.getPsicologo(),
                    test.getPaziente(),
                    test.getRisultato(),
                    test.getData());


            testBeanList.add(testBean);
        }
        return testBeanList;
    }

    public void modificaStatoTest() throws SQLException {
        new TestPsicologicoDAO().modificaStatoTest(SessionManager.getInstance().getCurrentUser().getUsername());

    }

    public String ricercaPsicologo() throws SQLException {
            String nomePsicologo= new PazienteDAO().TrovaPsicologo(SessionManager.getInstance().getCurrentUser().getUsername());
            return nomePsicologo;
    }

}
