package com.example.mindharbor.app_controllers.psicologo.prenota_appuntamento;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;

import java.util.ArrayList;
import java.util.List;

public class ListaRichiesteAppuntamentiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPsicologo() {return new SetInfoUtente().getInfo();}

    public List<AppuntamentiBean> getListaRichieste() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        List<AppuntamentiBean> listaRichiesteBean=new ArrayList<>();

        try {
            List<Appuntamento> listaRichieste = appuntamentoDAO.trovaRichiesteAppuntamento(
                    SessionManager.getInstance().getCurrentUser());

            for(Appuntamento ric: listaRichieste) {
                AppuntamentiBean ricBean= new AppuntamentiBean(
                        new PazientiBean(ric.getPaziente().getUsername(),ric.getPaziente().getNome(),ric.getPaziente().getCognome(),ric.getPaziente().getGenere()),
                        ric.getIdAppuntamento(),
                        ric.getNotificaRichiesta());

                listaRichiesteBean.add(ricBean);
            }
        }catch (DAOException e) {
           throw new DAOException(e.getMessage());
        }
        return listaRichiesteBean;
    }

    public void setRichiestaAppuntamentoSelezionato(AppuntamentiBean richiestaAppuntamentoSelezionato) {navigator.setAppuntamentoBean(richiestaAppuntamentoSelezionato);}
}
