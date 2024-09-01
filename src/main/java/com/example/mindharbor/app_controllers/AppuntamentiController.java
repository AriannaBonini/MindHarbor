package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.UtenteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.SetInfoUtente;

import java.util.ArrayList;
import java.util.List;

public class AppuntamentiController {

    public InfoUtenteBean getInfoUtente() {return new SetInfoUtente().getInfo();}

    public void modificaStatoNotificaAppuntamenti() throws DAOException {
        //Questo metodo viene utilizzato per modificare lo stato della notifica dei nuovi appuntamenti del paziente.
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        try {
            appuntamentoDAO.aggiornaStatoNotificaPaziente(SessionManager.getInstance().getCurrentUser());
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public List<AppuntamentiBean> getAppuntamentiPaziente(String selectedTabName ) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        UtenteDAO utenteDAO= daoFactoryFacade.getUtenteDAO();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();

        List<AppuntamentiBean> appuntamentiPazienteBeanList = new ArrayList<>();
        try {
            Utente infoPsicologo=utenteDAO.trovaNomeCognome(new Utente(SessionManager.getInstance().getUsernamePsicologo()));
            List<Appuntamento> appuntamentoPazienteList = appuntamentoDAO.trovaAppuntamentiPaziente(SessionManager.getInstance().getCurrentUser(), selectedTabName);

            for (Appuntamento app : appuntamentoPazienteList) {
                AppuntamentiBean appuntamentiPazienteBean = new AppuntamentiBean(
                        app.getData(),
                        app.getOra(),
                        new PsicologoBean(infoPsicologo.getUsername(),infoPsicologo.getNome(),infoPsicologo.getCognome())
                );

                appuntamentiPazienteBeanList.add(appuntamentiPazienteBean);
            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentiPazienteBeanList;
    }

    public List<AppuntamentiBean> getAppuntamentiPsicologo(String selectedTabName) throws DAOException {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO = daoFactoryFacade.getAppuntamentoDAO();

        List<AppuntamentiBean> appuntamentiPsicologoBeanList = new ArrayList<>();

        try {
            List<Appuntamento> appuntamentoPsicologoList = appuntamentoDAO.trovaAppuntamentiPsicologo(SessionManager.getInstance().getCurrentUser(),selectedTabName);

            for (Appuntamento app : appuntamentoPsicologoList) {

                AppuntamentiBean appuntamentiPsicologoBean = new AppuntamentiBean(
                        app.getData(),
                        app.getOra(),
                        new PazientiBean(app.getPaziente().getNome(),app.getPaziente().getCognome())
                );

                appuntamentiPsicologoBeanList.add(appuntamentiPsicologoBean);
            }

        }catch(DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentiPsicologoBeanList;
    }

    public boolean getPsicologo() {return SessionManager.getInstance().getUsernamePsicologo() != null;}
}
