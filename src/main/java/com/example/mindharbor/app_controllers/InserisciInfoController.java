package com.example.mindharbor.app_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class InserisciInfoController {
    private  NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public HomeInfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}

    public boolean CheckDati(PazientiBean pazienteBean) throws DAOException {
        try {
            if (new PazienteDAO().CheckPaziente(new Paziente(pazienteBean.getEtà(), "", SessionManager.getInstance().getCurrentUser().getUsername(), pazienteBean.getNome(), pazienteBean.getCognome(), UserType.PAZIENTE, "", "", "")) == true) {
                return true;
            } else {
                return false;
            }

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean CheckAppuntamento() {
        AppuntamentiBean appuntamento=navigator.getAppuntamentoBean();
        return appuntamento;
    }

    public void deleteAppuntamento() {navigator.deleteAppuntamentoBean();}
    public void setAppuntamento(AppuntamentiBean appuntamento) {navigator.setAppuntamentoBean(appuntamento);}
}
