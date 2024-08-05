package com.example.mindharbor.app_controllers.paziente.prenota_appuntamento;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;

public class RichiediPrenotazioneController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public InfoUtenteBean getPageRichPrenInfo() {return new SetInfoUtente().getInfo();}

    public PsicologoBean getPsicologoSelezionato(){ return navigator.getPsicologoBean();}
    public void deleteAppuntamentoSelezionato(){
        navigator.deleteAppuntamentoBean();
        eliminaPsicologoSelezionato();
    }
    public void eliminaPsicologoSelezionato(){navigator.deletePsicologoBean();}


    public AppuntamentiBean getAppuntamentoSelezionato() {return navigator.getAppuntamentoBean();}
    public void salvaRichiestaAppuntamento(AppuntamentiBean appuntamentiBean) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();

        appuntamentiBean.getPaziente().setUsername(SessionManager.getInstance().getCurrentUser().getUsername());
        Appuntamento appuntamento= new Appuntamento(appuntamentiBean.getData(),
                appuntamentiBean.getOra(),
                null,
                new Paziente(appuntamentiBean.getPaziente().getUsername()),
                new Psicologo(appuntamentiBean.getPsicologo().getUsername()));

        try {
            appuntamentoDAO.insertRichiestaAppuntamento(appuntamento);
            deleteAppuntamentoSelezionato();

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

    }

    public PsicologoBean getInfoPsicologo(PsicologoBean psicologoSelezionato) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        PsicologoDAO psicologoDAO= daoFactoryFacade.getPsicologoDAO();
        try {
            Psicologo psicologo= psicologoDAO.getInfoPsicologo(new Psicologo(psicologoSelezionato.getUsername()));

            psicologoSelezionato.setCostoOrario(psicologo.getCostoOrario());
            psicologoSelezionato.setNomeStudio(psicologo.getNomeStudio());

        }catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
        return psicologoSelezionato;
    }
}
