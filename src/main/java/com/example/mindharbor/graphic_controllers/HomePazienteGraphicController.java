package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class HomePazienteGraphicController {
    @FXML
    private Label listaAppuntamenti;
    @FXML
    private Label terapia;
    @FXML
    private Label prenotaAppuntamento;
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label notificaTest;
    @FXML
    private Label logout;
    @FXML
    private Label notificaTerapie;
    @FXML
    private Label notificaAppuntamenti;
    private final HomePazienteController homeController= new HomePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(HomePazienteGraphicController.class);
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void initialize() {
        InfoUtenteBean infoUtenteBean = homeController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        notificaNuoviTest();
        notificaNuoveTerapie();
        notificaNuoviAppuntamenti();
    }

    private void notificaNuoviAppuntamenti() {
        try {
            int count= homeController.cercaNuoviAppuntamenti();
            if (count>0) {
                notificaAppuntamenti.setVisible(true);
                notificaAppuntamenti.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei nuovi appuntamenti ", e);
        }
    }

    private void notificaNuoveTerapie() {
        try {
            int count= homeController.cercaNuoveTerapie();
            if (count>0) {
                notificaTerapie.setVisible(true);
                notificaTerapie.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca delle nuove terapie assegnate al paziente ", e);
        }

    }

    @FXML
    public void onVisualAppuntamentiClick() {
        try {
            Stage homePaziente = (Stage) listaAppuntamenti.getScene().getWindow();
            homePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamentiPaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia appuntamenti", e);
        }

    }

    @FXML
    public void onVisualTestClick() {
        try {
            Stage homePaziente = (Stage) listaAppuntamenti.getScene().getWindow();
            homePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia dei test", e);
        }
    }


    @FXML
    public void clickPrenotaAppuntamento() {
        try {
            Stage homePaziente = (Stage) prenotaAppuntamento.getScene().getWindow();
            homePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/SelezionaDataEOra.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia di prenotazione", e);
        }

    }


    private void notificaNuoviTest() {
        try {
            Integer count= homeController.cercaNuoviTestDaSvolgere();
            if (count>0) {
                notificaTest.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei nuovi test assegnati al paziente ", e);
        }
    }

    @FXML
    public void logout() {
        try {
            homeController.logout();

            Stage homePsicologo = (Stage) logout.getScene().getWindow();
            homePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void terapia() {
        try {
            Stage homePsicologo = (Stage) terapia.getScene().getWindow();
            homePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/PrescrizioniPaziente.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

}


