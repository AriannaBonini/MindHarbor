package com.example.mindharbor.app_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.graphic_controllers.HomePazienteGraphicController;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.patterns.ClassObserver;
import com.example.mindharbor.patterns.Observer;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScegliTestController {
    private ClassObserver observer= new ClassObserver();

    public static PazientiBean getInfoPaziente(String username) throws SQLException {
        Paziente paziente= new PazienteDAO().getInfoPaziente(username);

        PazientiBean pazienteBean= new PazientiBean(
                paziente.getNome(),
                paziente.getCognome(),
                paziente.getGenere(),
                paziente.getEtà(),
                "",
                ""
        );

        return pazienteBean;
    }
    public static List<String> getListaTest() {
        List <String> listaTestPsicologici=BoundaryMockAPI.TestPiscologici();

        return listaTestPsicologici;
    }


    public HomeInfoUtenteBean getPagePsiInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }


    public void NotificaTest() {
        HomePazienteGraphicController homePaziente= new HomePazienteGraphicController();

        observer.addObserver(homePaziente);
        observer.notifyObserversTest();
    }
}
