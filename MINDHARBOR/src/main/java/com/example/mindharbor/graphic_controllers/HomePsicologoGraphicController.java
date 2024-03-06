package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.app_controllers.ListaAppuntamentiPsicologoController;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
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

public class HomePsicologoGraphicController {
    @FXML
    private Label PrescriviTerapia;

    @FXML
    private Label RichiestaPrenotazione;

    @FXML
    private Label VisualizzaAppuntamenti;

    @FXML
    private Label LabelNomePsicologo;

    private HomePsicologoController homePsicologoController;

    private static final Logger logger = LoggerFactory.getLogger(HomePsicologoGraphicController.class);

    public void initialize() {
        homePsicologoController= new HomePsicologoController();
        Utente utenteCorrente = SessionManager.getInstance().getCurrentUser();

        LabelNomePsicologo.setText(utenteCorrente.getNome() +" " + utenteCorrente.getCognome());
    }

    @FXML
    public void onVisualAppuntamentiClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mindharbor/ListaAppuntamenti.fxml"));
            loader.setController(new ListaAppuntamentiPsicologoController());
            Parent root = null;
            root = loader.load();

            // ora imposto la scena
            Stage stage = new Stage();
            stage.setTitle("Lista Appuntamenti");
            stage.setScene(new Scene(root));
            stage.show();

            Stage HomePsicologo = (Stage) VisualizzaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();


        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }





}
