package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.graphic_controllers.HomePsicologoGraphicController;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppuntamentiPsicologoController {

    public HomeInfoUtenteBean getAppPsiInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }
    public static List<AppuntamentiBean> getAppuntamenti(String selectedTabName) throws SQLException {
        List<Appuntamento> appuntamentoList = new AppuntamentoDAO().trovaAppuntamento(
                SessionManager.getInstance().getCurrentUser().getUsername()
        );

        List<AppuntamentiBean> appuntamentiBeanList = new ArrayList<>();

            for (Appuntamento app : appuntamentoList) {
                AppuntamentiBean appuntamentiBean = new AppuntamentiBean(
                        app.getData(),
                        app.getOra(),
                        app.getNomePsicologo(),
                        app.getCognomePsicologo(),
                        app.getNomePaziente(),
                        app.getCognomePaziente(),
                        app.getUsernamePaziente(),
                        app.getUsernamePsicologo(),
                        "");

                appuntamentiBeanList.add(appuntamentiBean);
            }
            return appuntamentiBeanList;
    }
}
