package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.PazientiNumTest;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {

    public List<PazientiNumTestBean> getListaPazienti() throws SQLException {
        List<PazientiNumTest> ListaPazienti = new PazienteDAO().trovaPaziente(
                SessionManager.getInstance().getCurrentUser().getUsername()
        );

        List<PazientiNumTestBean> pazientiNumTestBeanList = new ArrayList<>();

        for (PazientiNumTest paz : ListaPazienti) {
            PazientiNumTestBean pazientiTestBean = new PazientiNumTestBean(
                    paz.getUsername(),
                    paz.getNumTest(),
                    paz.getNome(),
                    paz.getCognome(),
                    paz.getGenere());

            pazientiNumTestBeanList.add(pazientiTestBean);
        }
        return pazientiNumTestBeanList;
    }

    public HomeInfoUtenteBean getPagePsiInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

}
