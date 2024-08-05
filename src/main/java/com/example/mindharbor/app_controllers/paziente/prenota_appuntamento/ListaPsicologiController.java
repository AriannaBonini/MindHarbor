package com.example.mindharbor.app_controllers.paziente.prenota_appuntamento;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.dao.UtenteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.util.ArrayList;
import java.util.List;

public class ListaPsicologiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public static List<PsicologoBean> getListaPsicologi() throws DAOException {
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

    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}
    public void setPsicologoSelezionato(PsicologoBean psicologoSelezionato) {navigator.setPsicologoBean(psicologoSelezionato);}
    public void eliminaAppuntamentoSelezionato() {navigator.deleteAppuntamentoBean();}
}
