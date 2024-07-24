package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;
import java.util.ArrayList;
import java.util.List;

public class ListaPsicologiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public static List<PsicologoBean> getListaPsicologi() throws DAOException {
        try {
            List<PsicologoBean> listaPsicologiBean = new ArrayList<>();
            List<Psicologo> listaPsicologi = new UtenteDao().listaUtentiDiTipoPsicologo(SessionManager.getInstance().getUsernamePsicologo());
            for(Psicologo psi : listaPsicologi) {
                PsicologoBean psicologoBean=new PsicologoBean(psi.getUsername(),psi.getNome(),psi.getCognome(),0,"",psi.getGenere());

                listaPsicologiBean.add(psicologoBean);
            }

            return listaPsicologiBean;

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public InfoUtenteBean getInfoPaziente() {return new setInfoUtente().getInfo();}
    public void setPsicologoSelezionato(PsicologoBean psicologoSelezionato) {navigator.setPsicologoBean(psicologoSelezionato);}


    public void deleteAppuntamento() {navigator.deleteAppuntamentoBean();}
}
