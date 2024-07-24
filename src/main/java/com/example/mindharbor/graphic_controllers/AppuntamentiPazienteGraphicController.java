package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
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
import java.util.List;

public class AppuntamentiPazienteGraphicController {
    @FXML
    private Text listaVuotaInProgramma;
    @FXML
    private Text listaVuotaPassati;
    @FXML
    private Label labelNomePazienteTab2;
    @FXML
    private Label labelNomePazienteTab1;
    @FXML
    private ListView<Node> listViewInProgramma;
    @FXML
    private ListView<Node> listViewPassati;
    @FXML
    private Label homeTab1;
    @FXML
    private Label homeTab2;

    private final AppuntamentiController controllerAppuntamenti= new AppuntamentiController();

    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPazienteGraphicController.class);

    public void initialize() {
        InfoUtenteBean infoUtenteBean = controllerAppuntamenti.getInfoUtente();
        labelNomePazienteTab1.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        labelNomePazienteTab2.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        modificaStatoNotifica();

        tab1Selezionato();

    }

    private void modificaStatoNotifica() {
        try {
            controllerAppuntamenti.modificaStatoNotificaAppuntamenti();
        } catch (DAOException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }


    @FXML
    public void tab1Selezionato(){ricercaAppuntamentiPaziente("IN PROGRAMMA", listaVuotaInProgramma, listViewInProgramma);}
    @FXML
    public void tab2Selezionato() {
        ricercaAppuntamentiPaziente("PASSATI", listaVuotaPassati, listViewPassati);
    }

    private void ricercaAppuntamentiPaziente(String selectedTabName, Text text, ListView<Node> listView) {
        try {
            List<AppuntamentiBean> appuntamenti = controllerAppuntamenti.getAppuntamenti(selectedTabName);
            if (appuntamenti.isEmpty()) {
                text.setText("Non ci sono appuntamenti");
            }else {
                creaVBoxAppuntamentiPaziente(appuntamenti, listView);
            }
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
        }
    }

    private void creaVBoxAppuntamentiPaziente(List<AppuntamentiBean> appuntamenti, ListView<Node> listView) {
        listView.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (AppuntamentiBean app : appuntamenti) {
            VBox vBox = new VBox();

            Label dataAppuntamento = new Label("DATA:" + " " + app.getData());
            Label oraAppuntamento = new Label("ORA:" + " " + app.getOra());
            Label nomePsicologo = new Label("PSICOLOGO:" + " " + app.getPsicologo().getNome() + " " + app.getPsicologo().getCognome());
            Label nomePaziente = new Label("PAZIENTE:" + " " + app.getPaziente().getNome() + " " + app.getPaziente().getCognome());

            dataAppuntamento.setTextFill(Color.WHITE);
            oraAppuntamento.setTextFill(Color.WHITE);
            nomePsicologo.setTextFill(Color.WHITE);
            nomePaziente.setTextFill(Color.WHITE);

            vBox.getChildren().addAll(dataAppuntamento, oraAppuntamento, nomePsicologo, nomePaziente);
            items.add(vBox);
        }

        listView.setFixedCellSize(100);
        listView.getItems().addAll(items);
    }

    @FXML
    public void goToHomeFromTab1() { goToHome(homeTab1);}
    @FXML
    public void goToHomeFromTab2() { goToHome(homeTab2);}

    private void goToHome(Label label) {
        try {
            Stage appuntamenti = (Stage) label.getScene().getWindow();
            appuntamenti.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

}
