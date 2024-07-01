package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppuntamentiPsicologoController {

    public HomeInfoUtenteBean getInfoPsicologo() {
        return new setInfoUtente().getInfo();
    }
    public List<AppuntamentiBean> getAppuntamentiPsicologo(String selectedTabName) throws DAOException {
        List<AppuntamentiBean> appuntamentiBeanList = new ArrayList<>();

        try {
            List<Appuntamento> appuntamentoList = new AppuntamentoDAO().trovaAppuntamentoPsicologo(SessionManager.getInstance().getCurrentUser().getUsername(), selectedTabName
            );


        for (Appuntamento app : appuntamentoList) {
            AppuntamentiBean appuntamentiBean = new AppuntamentiBean(
                    app.getData(),
                    app.getOra(),
                    app.getPsicologo().getNome(),
                    app.getPsicologo().getCognome(),
                    app.getPaziente().getNome(),
                    app.getPaziente().getCognome(),
                    app.getPaziente().getUsername(),
                    app.getPsicologo().getUsername(),
                    "",
                    0);

            appuntamentiBeanList.add(appuntamentiBean);
        }

    }catch(SQLException e) {
        throw new DAOException(e.getMessage());
    }
    return appuntamentiBeanList;
    }
}
