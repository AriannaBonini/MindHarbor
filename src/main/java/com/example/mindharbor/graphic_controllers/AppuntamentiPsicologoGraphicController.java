package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AppuntamentiPsicologoGraphicController {

    @FXML
    private Tab AppuntamentiAttivi;

    @FXML
    private Label ScorriAppuntamenti2;

    @FXML
    private VBox BoxAppuntamenti;

    @FXML
    private Label DataAppuntamento;

    @FXML
    private Label OraAppuntamento;

    @FXML
    private Label NomePsicologo;

    @FXML
    private Label NomePaziente;

    @FXML
    private Label ScorriAppuntamenti1;

    @FXML
    private Label LabelNomePsicologo;

    @FXML
    private Text ListaVuota;

    @FXML
    private Label ScorriAppuntamentiPassati2;

    @FXML
    private Label DataAppuntamentoPassati;

    @FXML
    private Label OraAppuntamentoPassati;

    @FXML
    private Label NomePsicologoPassati;

    @FXML
    private Label NomePazientePassati;

    @FXML
    private Label ScorriAppuntamentiPassati1;

    @FXML
    private Label LabelNomePsicologo1;

    @FXML
    private Text ListaVuotaPassati;

    @FXML
    private Label Home;

    private String nome;
    private String cognome;


    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);


    public void initialize() {

        AppuntamentiPsicologoController appuntamentiController = new AppuntamentiPsicologoController();

        HomeInfoUtenteBean infoUtenteBean = appuntamentiController.getAppPsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);


        try {
            tabSelezionato("IN PROGRAMMA");
        } catch (SQLException e) {
            logger.info("Problemi di connessione al database", e);
        }

    }

    private void tabSelezionato(String selectedTabName) throws SQLException {
        try {
            if (selectedTabName.equals("IN PROGRAMMA")) {
                List<AppuntamentiBean> appuntamenti = AppuntamentiPsicologoController.getAppuntamenti(selectedTabName);
                CreaVBoxAppuntamenti(appuntamenti);

            } else if (selectedTabName.equals("PASSATI")) {
                List<AppuntamentiBean> appuntamenti = AppuntamentiPsicologoController.getAppuntamenti(selectedTabName);
            }

        } catch (SQLException e) {
            logger.info("Non non ci sono appuntamenti", e);
            ListaVuota.setText("Non esistono appuntamenti");

        }

    }

    private void CreaVBoxAppuntamenti(List<AppuntamentiBean> appuntamenti) {
        BoxAppuntamenti.getChildren().clear();

        BoxAppuntamenti.setSpacing(10);

        for (AppuntamentiBean app : appuntamenti) {
            VBox VBox = new VBox();

            Label DataAppuntamento = new Label("DATA:" + " " + app.getData());
            Label OraAppuntamento = new Label("ORA:" + " " + app.getOra());
            Label NomePsicologo = new Label("PSICOLOGO:" + " " + app.getNomePsicologo() + " " + app.getCognomePsicologo());
            Label NomePaziente = new Label("PAZIENTE:" + " " + app.getNomePaziente() + " " + app.getCognomePaziente());

            DataAppuntamento.setTextFill(Color.WHITE);
            OraAppuntamento.setTextFill(Color.WHITE);
            NomePsicologo.setTextFill(Color.WHITE);
            NomePaziente.setTextFill(Color.WHITE);

            VBox.getChildren().addAll(DataAppuntamento, OraAppuntamento, NomePsicologo, NomePaziente);

            BoxAppuntamenti.getChildren().add(VBox);
        }
    }

    @FXML
    public void goToHome() {
        try {
            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

            Stage Appuntamenti = (Stage) Home.getScene().getWindow();
            Appuntamenti.close();

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
