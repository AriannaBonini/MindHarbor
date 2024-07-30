package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.*;
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
    public PazientiBean getPazienteSelezionato() {return navigator.getPazienteBean();}

    public void modificaStatoTestSvolto(PazientiBean pazienteSelezionato) throws DAOException {
        try {
            new TestPsicologicoDAO().modificaStatoNotificaTest(SessionManager.getInstance().getCurrentUser(), new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
            }
    }

    public List<TestBean> getTestSvoltiSenzaPrescrizione(PazientiBean pazienteSelezionato) throws DAOException {
        List<TestBean> testSvoltiBean= new ArrayList<>();
        try {

            List<TestPsicologico> testSvolti = new TestPsicologicoDAO().listaTestSvoltiSenzaPrescrizione(pazienteSelezionato.getUsername(), SessionManager.getInstance().getCurrentUser().getUsername());

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


    public Utente ricercaNomeCognomePaziente(String username) throws DAOException {
        try {
            return new UtenteDao().trovaNomeCognome(new Utente(username));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void aggiungiTerapia(TerapiaBean terapiaBean) throws DAOException {
        try {
            Terapia terapia = new Terapia(new TestPsicologico(terapiaBean.getDataTest(), null, new Psicologo(terapiaBean.getPsicologo()), new Paziente(terapiaBean.getPaziente()), "", null), terapiaBean.getTerapia(), terapiaBean.getDataTerapia());
            new TerapiaDAO().insertTerapia(terapia);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
