package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public List<PazientiBean> getListaPazienti() throws DAOException {
        try {

            List<Paziente> listaPazienti = new PazienteDAO().trovaPaziente(
                    SessionManager.getInstance().getCurrentUser());

            List<PazientiBean> pazientiNumTestBeanList = new ArrayList<>();

            for (Paziente paz : listaPazienti) {
                PazientiBean pazientiTestBean = new PazientiBean(
                        paz.getUsername(),
                        paz.getNumeroTest(),
                        paz.getNome(),
                        paz.getCognome(),
                        paz.getGenere());

                pazientiNumTestBeanList.add(pazientiTestBean);
            }
            return pazientiNumTestBeanList;

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public InfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}
    public void setPazienteSelezionato(PazientiBean pazienteSelezionato) {navigator.setPazienteBean(pazienteSelezionato);}

}
