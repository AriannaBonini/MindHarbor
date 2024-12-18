package com.example.mindharbor.controller_applicativi.paziente;


import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.dao.UtenteDAO;
import com.example.mindharbor.eccezioni.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPICalendario;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.sessione.SessionManager;
import com.example.mindharbor.tipo_utente.UserType;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PrenotaAppuntamento {

    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public InfoUtenteBean getInfoUtente() {return new SetInfoUtente().getInfo();}

    public boolean controlloFormatoAnni(String anni) {
        //controlla se la stringa anni è formata solo da cifre numeriche.
        return anni.matches("\\d+");
    }

    private final List<LocalTime> timeRules = List.of(
            LocalTime.of(8, 30),
            LocalTime.of(18, 30),
            LocalTime.of(12, 0),
            LocalTime.of(13, 30)
    );


    public boolean controllaInformazioniPaziente(PazienteBean pazienteBean) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        PazienteDAO pazienteDAO= daoFactoryFacade.getPazienteDAO();
        try {
            if(!pazienteBean.getNome().equals(SessionManager.getInstance().getCurrentUser().getNome()) || !pazienteBean.getCognome().equals(SessionManager.getInstance().getCurrentUser().getCognome()))  {
                return false;
            }
            return pazienteDAO.checkAnniPaziente(new Paziente(SessionManager.getInstance().getCurrentUser().getUsername(),"","", UserType.PAZIENTE, pazienteBean.getAnni()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean controllaOrario(String insertTime) throws DateTimeParseException {
        try {
            LocalTime time = LocalTime.parse(insertTime, timeFormatter);

            if (time.isAfter(timeRules.get(0)) && time.isBefore(timeRules.get(1)) && (time.isBefore(timeRules.get(2)) || time.isAfter(timeRules.get(3)))) {
                return true;
            }

        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return false;
    }

    public List<PsicologoBean> getListaPsicologi() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        UtenteDAO utenteDAO= daoFactoryFacade.getUtenteDAO();

        try {
            List<PsicologoBean> listaPsicologiBean = new ArrayList<>();
            List<Psicologo> listaPsicologi = utenteDAO.listaUtentiDiTipoPsicologo(SessionManager.getInstance().getUsernamePsicologo());
            for(Psicologo psi : listaPsicologi) {
                PsicologoBean psicologoBean=new PsicologoBean(psi.getUsername(),psi.getNome(),psi.getCognome(),0,"",psi.getGenere());

                listaPsicologiBean.add(psicologoBean);
            }

            return listaPsicologiBean;

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void eliminaAppuntamentoSelezionato(){
        eliminaRichiestaAppuntamento();
        eliminaPsicologoSelezionato();
    }

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
            eliminaAppuntamentoSelezionato();

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

    public List<AppuntamentiBean> getListaRichieste() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        List<AppuntamentiBean> listaRichiesteBean=new ArrayList<>();

        try {
            List<Appuntamento> listaRichieste = appuntamentoDAO.trovaRichiesteAppuntamento(
                    SessionManager.getInstance().getCurrentUser());

            for(Appuntamento ric: listaRichieste) {
                AppuntamentiBean ricBean= new AppuntamentiBean(
                        new PazienteBean(ric.getPaziente().getUsername(),ric.getPaziente().getNome(),ric.getPaziente().getCognome(),ric.getPaziente().getGenere()),
                        ric.getIdAppuntamento(),
                        ric.getNotificaRichiesta());

                listaRichiesteBean.add(ricBean);
            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return listaRichiesteBean;
    }

    public AppuntamentiBean aggiungiInfoRichiestaAppuntamento(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        Appuntamento richiesta;
        try {
            richiesta = appuntamentoDAO.getInfoRichiesta(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));

            richiestaAppuntamento.setOra(richiesta.getOra());
            richiestaAppuntamento.setData(richiesta.getData());

            return richiestaAppuntamento;
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void modificaStatoNotifica(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        try {
            appuntamentoDAO.updateStatoNotifica(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean verificaDisponibilita(Integer idAppuntamento) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        try {
            if(!appuntamentoDAO.getDisp(idAppuntamento,SessionManager.getInstance().getCurrentUser())) {
                return false;
            }
            return BoundaryMockAPICalendario.calendario();

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaAccettata(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();

        Appuntamento appuntamentoAccettato= new Appuntamento(richiestaAppuntamento.getIdAppuntamento(),new Psicologo(SessionManager.getInstance().getCurrentUser().getUsername()),new Paziente(richiestaAppuntamento.getPaziente().getUsername()));
        try {
            appuntamentoDAO.updateRichiesta(appuntamentoAccettato);

            //eliminiamo tutte le altre richieste di appuntamento del paziente ad altri psicologi.
            appuntamentoDAO.eliminaRichiesteDiAppuntamentoPerAltriPsicologi(appuntamentoAccettato);

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void richiestaRifiutata(AppuntamentiBean richiestaAppuntamento) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        AppuntamentoDAO appuntamentoDAO= daoFactoryFacade.getAppuntamentoDAO();
        try {
            appuntamentoDAO.eliminaRichiesta(new Appuntamento(richiestaAppuntamento.getIdAppuntamento()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean getRichiestaAppuntamento() {return navigator.getAppuntamentoBean();}
    public void setRichiestaAppuntamento(AppuntamentiBean appuntamento) {navigator.setAppuntamentoBean(appuntamento);}
    public void eliminaRichiestaAppuntamento() {navigator.eliminaAppuntamentoBean();}
    public void setPsicologoSelezionato(PsicologoBean psicologoSelezionato) {navigator.setPsicologoBean(psicologoSelezionato);}
    public PsicologoBean getPsicologoSelezionato(){ return navigator.getPsicologoBean();}
    public void eliminaPsicologoSelezionato(){navigator.eliminaPsicologoBean();}


}
