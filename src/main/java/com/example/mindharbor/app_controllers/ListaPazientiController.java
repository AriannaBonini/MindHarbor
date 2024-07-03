package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.PazientiNumTest;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public List<PazientiNumTestBean> getListaPazienti() throws DAOException {
        List<PazientiNumTestBean> result;
        try {

            List<PazientiNumTest> listaPazienti = new PazienteDAO().trovaPaziente(
                    SessionManager.getInstance().getCurrentUser().getUsername());

            List<PazientiNumTestBean> pazientiNumTestBeanList = new ArrayList<>();

            for (PazientiNumTest paz : listaPazienti) {
                PazientiNumTestBean pazientiTestBean = new PazientiNumTestBean(
                        paz.getPaziente().getUsername(),
                        paz.getNumTest(),
                        paz.getPaziente().getNome(),
                        paz.getPaziente().getCognome(),
                        paz.getPaziente().getGenere());

                pazientiNumTestBeanList.add(pazientiTestBean);
            }
            result = pazientiNumTestBeanList;
            return result;

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }
    public HomeInfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}
    public void setUsername(String username) {navigator.setParametro(username);}

}
