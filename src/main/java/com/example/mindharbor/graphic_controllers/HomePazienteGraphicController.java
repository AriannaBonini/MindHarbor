package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.app_controllers.AppuntamentiPazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.patterns.Observer;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.github.tomakehurst.wiremock.common.Notifier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;
import com.example.mindharbor.session.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class HomePazienteGraphicController {
    @FXML
    private Label ListaAppuntamenti;

    @FXML
    private Label Terapia;

    @FXML
    private Label Test;

    @FXML
    private Label PrenotaAppuntamento;

    @FXML
    private Label LabelNomePaziente;

    @FXML
    private Label NotificaTest;

    private String nome;
    private String cognome;
    private String username;

    HomePazienteController homeController;
    private static final Logger logger = LoggerFactory.getLogger(HomePazienteGraphicController.class);

    public void initialize() throws SQLException {
        homeController = new HomePazienteController();

        HomeInfoUtenteBean infoUtenteBean = homeController.getHomepageInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePaziente.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();
        username=navigator.getParametro();

        navigator.eliminaParametro();

        Notifica(username);
    }

    public void onVisualAppuntamentiClick() {
        try {
            Stage HomePaziente = (Stage) ListaAppuntamenti.getScene().getWindow();
            HomePaziente.close();

            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamentiPaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    public void clickPrenotaAppuntamento() {

    }


    public void Notifica(String username) {
        try {
            int count= homeController.cercaNuoviTest(username);
            if (count!=0) {
                NotificaTest.setText(String.valueOf(count));
            } else{
                NotificaTest.setDisable(true);
            }

        } catch (SQLException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }
    }

}
