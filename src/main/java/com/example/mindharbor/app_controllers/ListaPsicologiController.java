package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaPsicologiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public static List<PsicologoBean> getListaPsicologi() throws DAOException {
        try {
            List<PsicologoBean> psicologiListBean = new ArrayList<>();
            List<Psicologo> psicologiList = new PsicologoDAO().ListaPsicologi(SessionManager.getInstance().getCurrentUser().getUsername());

            for(Psicologo psi : psicologiList) {
                PsicologoBean psicologoBean=new PsicologoBean(psi.getUsername(),psi.getNome(),psi.getCognome(),0,"",psi.getGenere());

                psicologiListBean.add(psicologoBean);
            }

            return psicologiListBean;

        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public HomeInfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}


    public void deleteAppuntamento() {navigator.deleteAppuntamentoBean();}
}
