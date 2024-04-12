package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.SessionUserException;
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

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);
    }

    @FXML
    public void onVisualAppuntamentiClick() {

        try {

            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamenti2.fxml");

            Stage HomePsicologo = (Stage) VisualizzaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();


        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    public void onPrescriviTerapiaClick() {

        NavigatorSingleton navigator = NavigatorSingleton.getInstance();

        try {
            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");

            Stage HomePsicologo = (Stage) PrescriviTerapia.getScene().getWindow();
            HomePsicologo.close();

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    public void Logout() {

        NavigatorSingleton navigator = NavigatorSingleton.getInstance();

        try {

            new HomePsicologoController().logout();

            Stage HomePsicologo= (Stage) logout.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        } catch (SessionUserException e) {
            logger.error("Errore", e);

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
