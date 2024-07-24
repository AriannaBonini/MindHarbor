package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;


public class InserisciInfoController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}

    public boolean controllaInformazioniPaziente(PazientiBean pazienteBean) throws DAOException {
        try {
            if(!pazienteBean.getNome().equals(SessionManager.getInstance().getCurrentUser().getNome()) || !pazienteBean.getCognome().equals(SessionManager.getInstance().getCurrentUser().getCognome()))  {
                return false;
            }
            return new PazienteDAO().checkAnniPaziente(new Paziente(SessionManager.getInstance().getCurrentUser().getUsername(),"","", UserType.PAZIENTE, pazienteBean.getAnni()));

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean getAppuntamentoSelezionato() {return navigator.getAppuntamentoBean();}

    public void deleteAppuntamento() {navigator.deleteAppuntamentoBean();}
    public void setAppuntamento(AppuntamentiBean appuntamento) {navigator.setAppuntamentoBean(appuntamento);}
}
