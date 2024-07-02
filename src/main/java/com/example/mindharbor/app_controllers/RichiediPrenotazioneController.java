package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;

public class RichiediPrenotazioneController {
    public HomeInfoUtenteBean getPageRichPrenInfo() {return new setInfoUtente().getInfo();}

    public String getUsername(){ return NavigatorSingleton.getInstance().getParametro();}
    public AppuntamentiBean getAppuntamento() {return NavigatorSingleton.getInstance().getAppuntamentoBean();}
    public void salvaRichiestaAppuntamento(AppuntamentiBean appuntamentiBean) throws DAOException {
        appuntamentiBean.setusernamePaziente(SessionManager.getInstance().getCurrentUser().getUsername());
        Appuntamento appuntamento= new Appuntamento(appuntamentiBean.getData(),
                appuntamentiBean.getOra(),
                null,
                new Paziente(appuntamentiBean.getusernamePaziente()),
                new Psicologo(appuntamentiBean.getusernamePsicologo()));

        try {
            new AppuntamentoDAO().insertRichiestaAppuntamento(appuntamento);

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        NavigatorSingleton.getInstance().deleteAppuntamentoBean();
    }

    public PsicologoBean getInfoPsicologo(String username) throws DAOException {
        PsicologoBean psicologoBean;
        try {
            Psicologo psicologo= new PsicologoDAO().getInfoPsicologo(username);

            psicologoBean=new PsicologoBean(username,
                    psicologo.getNome(),
                    psicologo.getCognome(),
                    psicologo.getCostoOrario(),
                    psicologo.getNomeStudio(),
                    psicologo.getGenere());

        }catch (SQLException e){
            throw new DAOException(e.getMessage());
        }

        return psicologoBean;

    }
}
