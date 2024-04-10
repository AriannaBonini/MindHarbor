package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePazienteController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.app_controllers.AppuntamentiPazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.model.Utente;
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

    private String nome;
    private String cognome;

    private static final Logger logger = LoggerFactory.getLogger(HomePazienteGraphicController.class);

    public void initialize() {
        HomePazienteController homeController = new HomePazienteController();

        HomeInfoUtenteBean infoUtenteBean = homeController.getHomepageInfo();

        nome=infoUtenteBean.getNome();
        cognome=infoUtenteBean.getCognome();

        LabelNomePaziente.setText(nome +" " +cognome);

    }

    public void onVisualAppuntamentiClick() {
       /* try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mindharbor/ListaAppuntamenti2.fxml"));
            //loader.setController(new AppuntamentiPazienteController());
            Parent root = null;
            root = loader.load();

            // ora imposto la scena
            Stage stage = new Stage();
            stage.setTitle("Lista Appuntamenti");
            stage.setScene(new Scene(root));
            stage.show();

            Stage HomePsicologo = (Stage) ListaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();


        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        } */

 }




    public void clickPrenotaAppuntamento() {

    }

}
