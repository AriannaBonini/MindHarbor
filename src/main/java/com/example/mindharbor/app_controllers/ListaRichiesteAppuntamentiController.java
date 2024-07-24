package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.util.ArrayList;
import java.util.List;

public class ListaRichiesteAppuntamentiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPsicologo() {return new setInfoUtente().getInfo();}

    public List<AppuntamentiBean> getListaRichieste() throws DAOException {
        List<AppuntamentiBean> listaRichiesteBean=new ArrayList<>();

        try {
            List<Appuntamento> listaRichieste = new AppuntamentoDAO().trovaRichiesteAppuntamento(
                    SessionManager.getInstance().getCurrentUser());

            for(Appuntamento ric: listaRichieste) {
                AppuntamentiBean ricBean= new AppuntamentiBean(
                        new PazientiBean(ric.getPaziente().getNome(),ric.getPaziente().getCognome(),ric.getPaziente().getGenere(),0),
                        ric.getIdAppuntamento(),
                        ric.getNotificaRichiesta());

                listaRichiesteBean.add(ricBean);
            }
            return listaRichiesteBean;
        }catch (DAOException e) {
           throw new DAOException(e.getMessage());
        }
    }

    public void setRichiestaAppuntamentoSelezionato(AppuntamentiBean richiestaAppuntamentoSelezionato) {navigator.setAppuntamentoBean(richiestaAppuntamentoSelezionato);}
}
