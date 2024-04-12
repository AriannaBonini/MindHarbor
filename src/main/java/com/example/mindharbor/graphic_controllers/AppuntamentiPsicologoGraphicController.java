package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Tab AppuntamentiInProgramma;
    @FXML
    private VBox BoxAppuntamentiInProgramma;
    @FXML
    private Label DataAppuntamentoInProgramma;
    @FXML
    private Label OraAppuntamentoInProgramma;
    @FXML
    private Label NomePsicologoInProgramma;
    @FXML
    private Label NomePazienteInProgramma;
    @FXML
    private Label LabelNomePsicologoTab1;
    @FXML
    private Text ListaVuotaInProgramma;
    @FXML
    private Label DataAppuntamentoPassati;
    @FXML
    private Label OraAppuntamentoPassati;
    @FXML
    private Label NomePsicologoPassati;
    @FXML
    private Label NomePazientePassati;
    @FXML
    private Label LabelNomePsicologoTab2;
    @FXML
    private Text ListaVuotaPassati;
    @FXML
    private ListView<Node> ListViewInProgramma;
    @FXML
    private ListView<Node> ListViewPassati;
    @FXML
    private Label HomeTab1;
    @FXML
    private Label HomeTab2;

    private String nome;
    private String cognome;


    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);


    public void initialize() {

        AppuntamentiPsicologoController appuntamentiController = new AppuntamentiPsicologoController();

        HomeInfoUtenteBean infoUtenteBean = appuntamentiController.getAppPsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologoTab1.setText(nome + " " + cognome);

        LabelNomePsicologoTab2.setText(nome + " " + cognome);


        try {
            tab1Selezionato();
        } catch (SQLException e) {
            logger.info("Problemi di connessione al database", e);
        }

    }

    @FXML
    private void tab1Selezionato() throws SQLException {
        ricercaAppuntamenti("IN PROGRAMMA",ListaVuotaInProgramma,ListViewInProgramma);
    }

    @FXML
    private void tab2Selezionato() throws SQLException {
        ricercaAppuntamenti("PASSATI",ListaVuotaPassati,ListViewPassati);
    }


    private void ricercaAppuntamenti(String selectedTabName, Text text, ListView<Node> listView) throws SQLException{
        try {
            List<AppuntamentiBean> appuntamenti = AppuntamentiPsicologoController.getAppuntamenti(selectedTabName);
            if (appuntamenti.isEmpty()) {
                text.setText("Non ci sono appuntamenti");
            }else {
                CreaVBoxAppuntamenti(appuntamenti, listView);
            }
        }catch (SQLException e) {
            logger.info("Non non ci sono appuntamenti", e);
        }
    }

    private void CreaVBoxAppuntamenti(List<AppuntamentiBean> appuntamenti, ListView<Node> listView) {
        listView.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

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
            items.add(VBox);
        }

        listView.setFixedCellSize(100);
        listView.getItems().addAll(items);
    }

    @FXML
    public void goToHomeFromTab1() {
        goToHome(HomeTab1);
    }

    @FXML
    public void goToHomeFromTab2() {
        goToHome(HomeTab2);
    }

    @FXML
    public void goToHome(Label label) {
        try {
            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

            Stage Appuntamenti = (Stage) label.getScene().getWindow();
            Appuntamenti.close();

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
