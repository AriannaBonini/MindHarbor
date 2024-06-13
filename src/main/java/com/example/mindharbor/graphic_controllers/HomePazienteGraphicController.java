package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;

public class HomePazienteGraphicController {
    @FXML
    private Label ListaAppuntamenti, Terapia, Test, PrenotaAppuntamento, LabelNomePaziente, NotificaTest, logout;
    private String nome, cognome;
    HomePazienteController homeController= new HomePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(HomePazienteGraphicController.class);

    public void initialize() throws SQLException {
        HomeInfoUtenteBean infoUtenteBean = homeController.getHomepageInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePaziente.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();

        Notifica();
    }

    @FXML
    private void onVisualAppuntamentiClick() {
        try {
            Stage HomePaziente = (Stage) ListaAppuntamenti.getScene().getWindow();
            HomePaziente.close();

            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
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

            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }


    @FXML
    private void clickPrenotaAppuntamento() {

    }


    private void Notifica() {
        try {
            int count= homeController.cercaNuoviTestDaSvolgere();
            if (count>0) {
                NotificaTest.setText(String.valueOf(count));
            }

        } catch (SQLException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }
    }

    @FXML
    private void Logout() {
        NavigatorSingleton navigator = NavigatorSingleton.getInstance();
        try {
            homeController.Logout();

            Stage HomePsicologo= (Stage) logout.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}

