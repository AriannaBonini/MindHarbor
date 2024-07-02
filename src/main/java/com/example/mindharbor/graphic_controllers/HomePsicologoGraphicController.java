package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
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
    private Label logout, PrescriviTerapia, RichiestaPrenotazione, VisualizzaAppuntamenti, LabelNomePsicologo, NotificaTest, NotificaRichieste;
    private static final Logger logger = LoggerFactory.getLogger(HomePsicologoGraphicController.class);
    private final HomePsicologoController homeController= new HomePsicologoController();
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = homeController.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        notificaTestEffettuati();
        notificaRichiesteAppuntamenti();
    }

    private void notificaRichiesteAppuntamenti() {
        try {
            int count = homeController.cercaRichiesteAppuntamenti();
            if (count > 0) {
                NotificaRichieste.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }

    }

    private void notificaTestEffettuati() {
        try {
            int count = homeController.cercaNuoviTestSvolti();
            if (count > 0) {
                NotificaTest.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }

    }

    @FXML
    public void onVisualAppuntamentiClick() {
        try {
            Stage HomePsicologo = (Stage) VisualizzaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamenti2.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    public void onPrescriviTerapiaClick() {
        try {
            Stage HomePsicologo = (Stage) PrescriviTerapia.getScene().getWindow();
            HomePsicologo.close();
            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void Logout() {
        try {
            Stage HomePsicologo= (Stage) logout.getScene().getWindow();
            HomePsicologo.close();
            homeController.logout();
            navigator.gotoPage("/com/example/mindharbor/Login.fxml");
        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void RichiestePrenotazioni() {
        try {
            Stage HomePsicologo= (Stage) RichiestaPrenotazione.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/ListaRichiesteAppuntamenti.fxml");
        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}
