package com.example.mindharbor.app_controllers.paziente.prenota_appuntamento;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class RichiestaAppuntamentoController {
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    private final List<LocalTime> timeRules = List.of(
            LocalTime.of(8, 30),
            LocalTime.of(18, 30),
            LocalTime.of(12, 0),
            LocalTime.of(13, 30)
    );

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}

    public boolean controllaInformazioniPaziente(PazientiBean pazienteBean) throws DAOException {
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


    public AppuntamentiBean getRichiestaAppuntamentoScelta() {return navigator.getAppuntamentoBean();}

    public void eliminaRichiestaAppuntamentoScelta() {navigator.deleteAppuntamentoBean();}
    public void setRichiestaAppuntamentoScelta(AppuntamentiBean appuntamento) {navigator.setAppuntamentoBean(appuntamento);}
}
