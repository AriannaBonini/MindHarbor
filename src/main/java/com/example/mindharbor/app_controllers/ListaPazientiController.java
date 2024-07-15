package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.PazientiNumTest;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public List<PazientiBean> getListaPazienti() throws DAOException {
        try {

            List<Paziente> listaPazienti = new PazienteDAO().trovaPaziente(
                    SessionManager.getInstance().getCurrentUser().getUsername());

            List<PazientiBean> pazientiNumTestBeanList = new ArrayList<>();

            for (Paziente paz : listaPazienti) {
                PazientiBean pazientiTestBean = new PazientiBean(
                        paz.getNome(),
                        paz.getPaziente().getCognome(),
                        paz.getPaziente().getUsername(),
                        paz.getNumTest(),
                        paz.getPaziente().getGenere());

                pazientiNumTestBeanList.add(pazientiTestBean);
            }
            return pazientiNumTestBeanList;

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public HomeInfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}
    public void setPaziente(String username) {navigator.setParametro(username);}

}
