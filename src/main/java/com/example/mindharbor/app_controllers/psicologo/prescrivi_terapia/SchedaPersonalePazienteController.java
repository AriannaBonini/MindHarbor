package com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;

public class SchedaPersonalePazienteController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();


    public void deletePazienteSelezionato() {navigator.deletePazienteBean();}
    public InfoUtenteBean getInfoPsicologo() {
        return new SetInfoUtente().getInfo();
    }
    public PazienteBean getPazienteSelezionato(){
        return navigator.getPazienteBean();
    }
    public void setPazienteSelezionato(PazienteBean pazienteSelezionato) { navigator.setPazienteBean(pazienteSelezionato);}

    public PazienteBean getSchedaPersonale(PazienteBean pazienteSelezionato) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        PazienteDAO pazienteDAO = daoFactoryFacade.getPazienteDAO();
        try {
            Paziente paziente = pazienteDAO.getInfoSchedaPersonale(new Paziente(pazienteSelezionato.getUsername()));

            pazienteSelezionato.setAnni(paziente.getAnni());
            pazienteSelezionato.setDiagnosi(paziente.getDiagnosi());
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return pazienteSelezionato;
    }

    public Integer numTestSvoltiSenzaPrescrizione(PazienteBean pazienteSelezionato) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();
        try {
            return testPsicologicoDAO.getNumTestSvoltiSenzaPrescrizione(SessionManager.getInstance().getCurrentUser(), new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Integer getNumTestOdiernoAssegnato(PazienteBean pazienteSelezionato) throws DAOException{
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();
        try {
            return testPsicologicoDAO.getNumTestAssegnato(new Paziente(pazienteSelezionato.getUsername()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

}
