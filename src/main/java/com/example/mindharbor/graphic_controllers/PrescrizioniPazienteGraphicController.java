package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.PrescrizioniTerapiaPazienteController;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class PrescrizioniPazienteGraphicController {
    @FXML
    public Label listaVuota;
    @FXML
    public Label labelNomePaziente;
    @FXML
    public Label prescrizione;
    @FXML
    public Label dataPrescrizione;
    @FXML
    public Label labelNomePsicologo;
    @FXML
    public Label home;
    @FXML
    public ListView<Node> listViewTerapia;

    private static final Logger logger = LoggerFactory.getLogger(PrescrizioniPazienteGraphicController.class);
    private final PrescrizioniTerapiaPazienteController prescriviTerapiaController= new PrescrizioniTerapiaPazienteController();

    public void initialize() {
        InfoUtenteBean infoUtenteBean = prescriviTerapiaController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        getListaTerapie();
    }

    private void getListaTerapie() {
        try {
            List<TerapiaBean> terapieBean= prescriviTerapiaController.trovaTerapie();
            if (terapieBean.isEmpty()) {
                listaVuota.setText("Non ci sono terapie");
            } else {
                popolaListaTerapia(terapieBean);
            }
        }catch (DAOException e) {
            logger.info("Errore nel caricamento della Lista delle Terapie ", e );
        }

    }

    private void popolaListaTerapia(List<TerapiaBean> terapieBean) {
        listViewTerapia.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (TerapiaBean terapia : terapieBean) {
            VBox boxTerapia= new VBox();

            Label labelDataPrescrizione = new Label("DATA: " + terapia.getDataTerapia());
            Label labelPrescrizione = new Label("PRESCRIZIONE : \n " + terapia.getTerapia());


            boxTerapia.getChildren().addAll(labelDataPrescrizione, labelPrescrizione);
            boxTerapia.setSpacing(5);

            items.add(boxTerapia);


            boxTerapia.setUserData(terapia);
        }
        listViewTerapia.setFixedCellSize(150);
        listViewTerapia.getItems().addAll(items);
    }

    @FXML
    public void goToHome() {
        try {
            Stage terapiaPaziente = (Stage) home.getScene().getWindow();
            terapiaPaziente.close();
            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
