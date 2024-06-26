package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Terapia;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescrizioniTerapiaPazienteController {
    public HomeInfoUtenteBean getInfoPaziente() {
        return new setInfoUtente().getInfo();
    }

    public List<TerapiaBean> trovaTerapie() throws DAOException {
        List<TerapiaBean> terapieBean=new ArrayList<>();
        try {

            List<Terapia> terapie = new TerapiaDAO().getTerapie(SessionManager.getInstance().getCurrentUser().getUsername());

            for (Terapia terapia : terapie) {
                TerapiaBean terapiaBean = new TerapiaBean(terapia.getTestPsicologico().getPsicologo().getUsername(),
                        " ",
                        terapia.getTerapia(),
                        terapia.getDataTerapia(),
                        null);

                terapieBean.add(terapiaBean);
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return terapieBean;
    }
}
