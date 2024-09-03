package com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.util.ArrayList;
import java.util.List;

public class ListaPazientiController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public List<PazientiBean> getListaPazienti() throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        PazienteDAO pazienteDAO= daoFactoryFacade.getPazienteDAO();
        try {

            List<Paziente> listaPazienti = pazienteDAO.trovaPazienti(
                    SessionManager.getInstance().getCurrentUser());
            List<PazientiBean> pazientiNumTestBeanList = new ArrayList<>();

            for (Paziente paz : listaPazienti) {
                PazientiBean pazientiTestBean = new PazientiBean(
                        paz.getUsername(),
                        paz.getNumeroTest(),
                        paz.getNome(),
                        paz.getCognome(),
                        paz.getGenere());

                pazientiNumTestBeanList.add(pazientiTestBean);
            }
            return pazientiNumTestBeanList;

        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public InfoUtenteBean getInfoPsicologo() {return new SetInfoUtente().getInfo();}
    public void setPazienteSelezionato(PazientiBean pazienteSelezionato) {navigator.setPazienteBean(pazienteSelezionato);}

}
