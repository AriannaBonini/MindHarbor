package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Terapia;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.util.ArrayList;
import java.util.List;

public class PrescrizioniTerapiaPazienteController {
    public InfoUtenteBean getInfoPaziente() {
        return new setInfoUtente().getInfo();
    }

    public List<TerapiaBean> trovaTerapie() throws DAOException {
        List<TerapiaBean> terapieBean=new ArrayList<>();
        try {

            List<Terapia> terapie = new TerapiaDAO().getTerapie(SessionManager.getInstance().getCurrentUser());

            for (Terapia terapia : terapie) {
                TerapiaBean terapiaBean = new TerapiaBean(terapia.getTestPsicologico().getPsicologo().getUsername(),
                        " ",
                        terapia.getTerapia(),
                        terapia.getDataTerapia(),
                        null);

                terapieBean.add(terapiaBean);
            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

        return terapieBean;
    }
}
