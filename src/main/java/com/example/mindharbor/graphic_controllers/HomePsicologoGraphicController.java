package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class HomePsicologoGraphicController {
    @FXML
    private Label logout;
    @FXML
    private Label prescriviTerapia;
    @FXML
    private Label richiestaPrenotazione;
    @FXML
    private Label visualizzaAppuntamenti;
    @FXML
    private Label labelNomePsicologo;
    @FXML
    private Label notificaTest;
    @FXML
    private Label notificaRichieste;
    private static final Logger logger = LoggerFactory.getLogger(HomePsicologoGraphicController.class);
    private final HomePsicologoController homeController= new HomePsicologoController();
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();

    public void initialize() {
        InfoUtenteBean infoUtenteBean = homeController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        notificaTestEffettuati();
        notificaRichiesteAppuntamenti();
    }

    private void notificaRichiesteAppuntamenti() {
        try {
            int count = homeController.cercaRichiesteAppuntamenti();
            if (count > 0) {
                notificaRichieste.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }

    }

    private void notificaTestEffettuati() {
        try {
            int count = homeController.cercaNuoviTestSvolti();
            if (count > 0) {
                notificaTest.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }

    }

    @FXML
    public void onVisualAppuntamentiClick() {
        try {
            Stage homePsicologo = (Stage) visualizzaAppuntamenti.getScene().getWindow();
            homePsicologo.close();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamenti2.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia degli appuntamenti", e);
        }

    }

    @FXML
    public void onPrescriviTerapiaClick() {
        try {
            Stage homePsicologo = (Stage) prescriviTerapia.getScene().getWindow();
            homePsicologo.close();
            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia per la prescrizione delle terapie", e);
        }
    }

    @FXML
    public void logout() {
        try {
            Stage homePsicologo = (Stage) logout.getScene().getWindow();
            homePsicologo.close();
            homeController.logout();
            navigator.gotoPage("/com/example/mindharbor/Login.fxml");
        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void richiestePrenotazioni() {
        try {
            Stage homePsicologo = (Stage) richiestaPrenotazione.getScene().getWindow();
            homePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/ListaRichiesteAppuntamenti.fxml");
        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}
