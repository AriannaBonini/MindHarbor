package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private Label DataAppuntamentoInProgramma, OraAppuntamentoInProgramma, NomePsicologoInProgramma, NomePazienteInProgramma, LabelNomePsicologoTab1;
    @FXML
    private Text ListaVuotaInProgramma,  ListaVuotaPassati;
    @FXML
    private Label DataAppuntamentoPassati, OraAppuntamentoPassati, NomePsicologoPassati,  NomePazientePassati, LabelNomePsicologoTab2;
    @FXML
    private ListView<Node> ListViewInProgramma, ListViewPassati;
    @FXML
    private Label HomeTab1, HomeTab2;

    private String nome, cognome;
    AppuntamentiPsicologoController appuntamentiController = new AppuntamentiPsicologoController();


    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);


    public void initialize() {

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
            List<AppuntamentiBean> appuntamenti = appuntamentiController.getAppuntamenti(selectedTabName);
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
    private void goToHomeFromTab1() {
        goToHome(HomeTab1);
    }

    @FXML
    private void goToHomeFromTab2() {
        goToHome(HomeTab2);
    }

    private void goToHome(Label label) {
        try {
            Stage Appuntamenti = (Stage) label.getScene().getWindow();
            Appuntamenti.close();


            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
