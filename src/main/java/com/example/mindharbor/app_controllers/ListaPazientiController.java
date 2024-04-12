package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {

    public static List<PazientiBean> getListaPazienti() throws SQLException {
        List<Paziente> ListaPazienti = new PazienteDAO().trovaPaziente(
                SessionManager.getInstance().getCurrentUser().getUsername()
        );

        List<PazientiBean> pazientiBeanList = new ArrayList<>();

        for (Paziente paz : ListaPazienti) {
            PazientiBean pazientiBean = new PazientiBean(
                    paz.getNome(),
                    paz.getCognome(),
                    paz.getGenere(),
                    "",
                    ""
            );

            pazientiBeanList.add(pazientiBean);
        }
        return pazientiBeanList;

    }

    public HomeInfoUtenteBean getPagePsiInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }


}
