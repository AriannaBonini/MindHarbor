package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;

public class HomePazienteGraphicController {
    @FXML
    private Label ListaAppuntamenti, Terapia, PrenotaAppuntamento, LabelNomePaziente, NotificaTest, logout, NotificaTerapie, NotificaAppuntamenti;
    private final HomePazienteController homeController= new HomePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(HomePazienteGraphicController.class);
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void initialize() throws SQLException {
        HomeInfoUtenteBean infoUtenteBean = homeController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        NotificaNuoviTest();
        NotificaNuoveTerapie();
        NotificaNuoviAppuntamenti();
    }

    private void NotificaNuoviAppuntamenti() {
        try {
            int count= homeController.cercaNuoviAppuntamenti();
            if (count>0) {
                NotificaAppuntamenti.setVisible(true);
                NotificaAppuntamenti.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei nuovi appuntamenti ", e);
        }
    }

    private void NotificaNuoveTerapie() {
        try {
            int count= homeController.cercaNuoveTerapie();
            if (count>0) {
                NotificaTerapie.setVisible(true);
                NotificaTerapie.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca delle nuove terapie assegnate al paziente ", e);
        }

    }

    @FXML
    private void onVisualAppuntamentiClick() {
        try {
            Stage HomePaziente = (Stage) ListaAppuntamenti.getScene().getWindow();
            HomePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamentiPaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    private void onVisualTestClick() {
        try {
            Stage HomePaziente = (Stage) ListaAppuntamenti.getScene().getWindow();
            HomePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }


    @FXML
    private void clickPrenotaAppuntamento() {
        try {
            Stage HomePaziente = (Stage) PrenotaAppuntamento.getScene().getWindow();
            HomePaziente.close();
            navigator.gotoPage("/com/example/mindharbor/SelezionaDataEOra.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    private void NotificaNuoviTest() {
        try {
            Integer count= homeController.cercaNuoviTestDaSvolgere();
            if (count>0) {
                NotificaTest.setText(String.valueOf(count));
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei nuovi test assegnati al paziente ", e);
        }
    }

    @FXML
    private void Logout() {
        try {
            homeController.Logout();

            Stage HomePsicologo= (Stage) logout.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void Terapia() {
        try {
            Stage HomePsicologo= (Stage) Terapia.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/PrescrizioniPaziente.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

}


