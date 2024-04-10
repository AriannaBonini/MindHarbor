package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomePsicologoGraphicController {
    @FXML
    private Label PrescriviTerapia;

    @FXML
    private Label RichiestaPrenotazione;

    @FXML
    private Label VisualizzaAppuntamenti;

    @FXML
    private Label LabelNomePsicologo;

    private static final Logger logger = LoggerFactory.getLogger(HomePsicologoGraphicController.class);

    private String nome;
    private String cognome;

    public void initialize() {

        HomePsicologoController homeController = new HomePsicologoController();

        HomeInfoUtenteBean infoUtenteBean = homeController.getHomepageInfo();

        nome=infoUtenteBean.getNome();
        cognome=infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome +" " +cognome);
    }

    @FXML
    public void onVisualAppuntamentiClick() {

        try {

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamenti2.fxml");

            Stage HomePsicologo = (Stage) VisualizzaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();


        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    public void onPrescriviTerapiaClick() {

        NavigatorSingleton navigator= NavigatorSingleton.getInstance();

        try {
            navigator.gotoPage("/com/example/mindharbor/ProvaListaPazienti.fxml");

            Stage HomePsicologo = (Stage) PrescriviTerapia.getScene().getWindow();
            HomePsicologo.close();

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}

