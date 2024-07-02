package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaRichiesteAppuntamentiController {
    public HomeInfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}

    public List<AppuntamentiBean> getListaRichieste() throws DAOException {
        List<AppuntamentiBean> listaRichiesteBean=new ArrayList<>();

        try {
            List<Appuntamento> listaRichieste = new AppuntamentoDAO().trovaRichiesteAppuntamento(
                    SessionManager.getInstance().getCurrentUser().getUsername());

            for(Appuntamento ric: listaRichieste) {
                AppuntamentiBean ricBean= new AppuntamentiBean(null,
                        null,
                        "",
                        "",
                        ric.getPaziente().getNome(),
                        ric.getPaziente().getCognome(),
                        ric.getPaziente().getUsername(),
                        "",
                        ric.getIdAppuntamento(),
                        0,
                        ric.getPaziente().getGenere(),
                        ric.getNotificaRichiesta());

                listaRichiesteBean.add(ricBean);
            }
            return listaRichiesteBean;
        }catch (SQLException e) {
           throw new DAOException(e.getMessage());
        }
    }

    public void setIdRichiesta(Integer idAppuntamento) {NavigatorSingleton.getInstance().setParametro(String.valueOf(idAppuntamento));}
}
