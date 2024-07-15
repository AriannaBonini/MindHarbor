package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentiController {

    public HomeInfoUtenteBean getInfoUtente() {return new setInfoUtente().getInfo();}

    public void modificaStatoNotificaAppuntamenti() throws DAOException {
        //Questo metodo viene utilizzato per modificare lo stato della notifica del paziente.
        try {
            new AppuntamentoDAO().updateStatoNotificaPaziente(SessionManager.getInstance().getCurrentUser());
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public List<AppuntamentiBean> getAppuntamenti(String selectedTabName) throws DAOException {
        List<AppuntamentiBean> appuntamentiBeanList = new ArrayList<>();

        try {
            List<Appuntamento> appuntamentoList = new AppuntamentoDAO().trovaAppuntamenti(SessionManager.getInstance().getCurrentUser(), selectedTabName);


            for (Appuntamento app : appuntamentoList) {
                AppuntamentiBean appuntamentiBean = new AppuntamentiBean(
                        app.getData(),
                        app.getOra(),
                        new PazientiBean( app.getPaziente().getNome(),app.getPaziente().getCognome(),"",0,"",app.getPaziente().getUsername()),
                        new PsicologoBean(app.getPsicologo().getUsername(), app.getPsicologo().getNome(),app.getPsicologo().getCognome(),0,"",""),
                        null,
                        null);

                appuntamentiBeanList.add(appuntamentiBean);
            }

        }catch(DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentiBeanList;
    }
}
