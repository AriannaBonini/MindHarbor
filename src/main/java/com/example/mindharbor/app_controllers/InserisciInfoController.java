package com.example.mindharbor.app_controllers;

import com.example.mindharbor.user_type.UserType;
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
import java.util.ArrayList;
import java.util.List;

public class InserisciInfoController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public HomeInfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}

    public boolean checkDati(PazientiBean pazienteBean) throws DAOException {
        try {
            List<Object> parametri=new ArrayList<>();
            parametri.add("");
            parametri.add("");
            parametri.add(pazienteBean.getAnni());

            return new PazienteDAO().checkPaziente(new Paziente(SessionManager.getInstance().getCurrentUser().getUsername(),pazienteBean.getNome(), pazienteBean.getCognome(),UserType.PAZIENTE,"","",parametri));

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public AppuntamentiBean checkAppuntamento() {return navigator.getAppuntamentoBean();}

    public void deleteAppuntamento() {navigator.deleteAppuntamentoBean();}
    public void setAppuntamento(AppuntamentiBean appuntamento) {navigator.setAppuntamentoBean(appuntamento);}
}
