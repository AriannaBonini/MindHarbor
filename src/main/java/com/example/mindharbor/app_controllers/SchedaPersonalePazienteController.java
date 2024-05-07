package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class SchedaPersonalePazienteController {

    public HomeInfoUtenteBean getPagePsiInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public static PazientiBean getSchedaPersonale(String username) throws SQLException {
        Paziente paziente= new PazienteDAO().getInfoSchedaPersonale(username);

        PazientiBean pazienteBean= new PazientiBean(
                paziente.getNome(),
                paziente.getCognome(),
                paziente.getGenere(),
                paziente.getEtà(),
                paziente.getDiagnosi(),
                ""
        );

        return pazienteBean;
    }
}
