package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaPazientiController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImageDecorator;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class ListaPazientiGraphicController {
    @FXML
    private Label labelNomePsicologo;
    @FXML
    private Label home;
    @FXML
    private Text listaVuota;
    @FXML
    private ListView<Node> listViewPazienti;
    private final ListaPazientiController listaPazientiController = new ListaPazientiController();
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ListaPazientiGraphicController.class);

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = listaPazientiController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        popolaLista();
    }

    private void popolaLista() {
        try {
            List<PazientiNumTestBean> listaPazienti = listaPazientiController.getListaPazienti();
            creaVBoxListaPazienti(listaPazienti);
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
            listaVuota.setText("Non esistono appuntamenti");
        }
    }

    private void creaVBoxListaPazienti(List<PazientiNumTestBean> listaPazienti) {
        listViewPazienti.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (PazientiNumTestBean paz : listaPazienti) {

            VBox boxPaziente = new VBox();
            HBox hBoxPaziente = new HBox();
            ImageView immaginePaziente = new ImageView();

            Label nomePaziente = new Label("\n     NOME:" + " " + paz.getNome());
            Label cognomePaziente = new Label("     COGNOME:" + " " + paz.getCognome());

            if(paz.getNumTest()>0) {
                nomePaziente.setStyle("-fx-font-weight: bold;");
                cognomePaziente.setStyle("-fx-font-weight: bold;");

            }

            nomePaziente.setTextFill(Color.WHITE);
            cognomePaziente.setTextFill(Color.WHITE);

            boxPaziente.getChildren().addAll(nomePaziente, cognomePaziente);

            ImageDecorator imageDecorator= new GenereDecorator(immaginePaziente,paz.getGenere());
            imageDecorator.loadImage();

            hBoxPaziente.getChildren().addAll(immaginePaziente, boxPaziente);
            items.add(hBoxPaziente);

            hBoxPaziente.setUserData(paz);

        }

        listViewPazienti.setFixedCellSize(100);
        listViewPazienti.getItems().addAll(items);
    }
    @FXML
    public void goToHome() {
        try {
            Stage listaPazienti = (Stage) home.getScene().getWindow();
            listaPazienti.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
    @FXML
    public void nodoSelezionato() {
        try {
            Node nodo = listViewPazienti.getSelectionModel().getSelectedItem();
            if(nodo==null) {
                return;
            }

            PazientiNumTestBean paziente =(PazientiNumTestBean) nodo.getUserData();
            String username=paziente.getUsername();

            Stage listaPazienti = (Stage) listViewPazienti.getScene().getWindow();
            listaPazienti.close();

            listaPazientiController.setPaziente(username);

            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

}
