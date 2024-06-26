package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.*;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriviTerapiaController {

    public HomeInfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }
    public String getUsername() {return NavigatorSingleton.getInstance().getParametro();}

    public void modificaStatoTestSvolto(String usernamePaziente) throws DAOException {
        try {
            new TestPsicologicoDAO().modificaStatoNotificaTest(SessionManager.getInstance().getCurrentUser().getUsername(), "psicologo", usernamePaziente);
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
            }
    }

    public List<TestBean> getTestSvoltiSenzaPrescrizione(String username) throws DAOException {
        List<TestBean> testSvoltiBean= new ArrayList<>();
        try {

            List<TestPsicologico> testSvolti = new TestPsicologicoDAO().ListaTestSvoltiSenzaPrescrizione(username, SessionManager.getInstance().getCurrentUser().getUsername());

            for (TestPsicologico test : testSvolti) {
                TestBean testSvoltoBean = new TestBean(test.getTest(),
                        "",
                        "",
                        test.getRisultato(),
                        test.getData(),
                        null);

                testSvoltiBean.add(testSvoltoBean);
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return testSvoltiBean;
    }


    public Utente ricercaNomeCognomePaziente(String username) throws DAOException {
        try {
            return new PazienteDAO().trovaNomeCognomePaziente(new Utente(username));
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void aggiungiTerapia(TerapiaBean terapiaBean) throws DAOException {
        try {
            Terapia terapia = new Terapia(new TestPsicologico(terapiaBean.getDataTest(), null, new Psicologo(terapiaBean.getPsicologo()), new Paziente(terapiaBean.getPaziente()), "", null), terapiaBean.getTerapia(), terapiaBean.getDataTerapia());
            new TerapiaDAO().InsertTerapia(terapia);
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
