package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPazienteController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AppuntamentiPazienteGraphicController {
    @FXML
    private Text ListaVuotaInProgramma, ListaVuotaPassati;
    @FXML
    private Label LabelNomePazienteTab2, LabelNomePazienteTab1;
    @FXML
    private ListView<Node> ListViewInProgramma, ListViewPassati;
    @FXML
    private Label HomeTab1, HomeTab2;

    private final AppuntamentiPazienteController controllerAppuntamenti= new AppuntamentiPazienteController();

    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPazienteGraphicController.class);

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = controllerAppuntamenti.getInfoPaziente();
        LabelNomePazienteTab1.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        LabelNomePazienteTab2.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        ModificaStatoNotifica();

        try {
            tab1Selezionato();
        } catch (SQLException e) {
            logger.info("Problemi di connessione al database", e);
        }

    }

    private void ModificaStatoNotifica() {
        try {
            controllerAppuntamenti.modificaStatoNotificaAppuntamenti();
        } catch (DAOException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }


    @FXML
    public void tab1Selezionato() throws SQLException {ricercaAppuntamentiPaziente("IN PROGRAMMA",ListaVuotaInProgramma,ListViewInProgramma);}
    @FXML
    public void tab2Selezionato() {
        ricercaAppuntamentiPaziente("PASSATI",ListaVuotaPassati,ListViewPassati);
    }

    private void ricercaAppuntamentiPaziente(String selectedTabName, Text text, ListView<Node> listView) {
        try {
            List<AppuntamentiBean> appuntamenti = controllerAppuntamenti.getAppuntamentiPaziente(selectedTabName);
            if (appuntamenti.isEmpty()) {
                text.setText("Non ci sono appuntamenti");
            }else {
                CreaVBoxAppuntamentiPaziente(appuntamenti, listView);
            }
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
        }
    }

    private void CreaVBoxAppuntamentiPaziente(List<AppuntamentiBean> appuntamenti, ListView<Node> listView) {
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
    public void goToHomeFromTab1() { goToHome(HomeTab1);}
    @FXML
    public void goToHomeFromTab2() { goToHome(HomeTab2);}

    private void goToHome(Label label) {
        try {
            Stage Appuntamenti = (Stage) label.getScene().getWindow();
            Appuntamenti.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

}
