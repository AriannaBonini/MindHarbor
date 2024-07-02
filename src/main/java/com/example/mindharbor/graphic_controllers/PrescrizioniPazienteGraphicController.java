package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.PrescrizioniTerapiaPazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
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
    public Label ListaVuota, LabelNomePaziente, Prescrizione, DataPrescrizione, LabelNomePsicologo, Home;
    @FXML
    public ListView<Node> ListViewTerapia;

    private static final Logger logger = LoggerFactory.getLogger(PrescrizioniPazienteGraphicController.class);
    private final PrescrizioniTerapiaPazienteController prescriviTerapiaController= new PrescrizioniTerapiaPazienteController();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = prescriviTerapiaController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        getListaTerapie();
    }

    private void getListaTerapie() {
        try {
            List<TerapiaBean> terapieBean= prescriviTerapiaController.trovaTerapie();
            if (terapieBean.isEmpty()) {
                ListaVuota.setText("Non ci sono terapie");
            } else {
                PopolaListaTerapia(terapieBean);
            }
        }catch (DAOException e) {
            logger.info("Errore nel caricamento della Lista delle Terapie ", e );
        }

    }

    private void PopolaListaTerapia(List<TerapiaBean> terapieBean) {
        ListViewTerapia.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (TerapiaBean terapia : terapieBean) {
            VBox boxTerapia= new VBox();

            Label DataPrescrizione= new Label("DATA: " + terapia.getDataTerapia());
            Label Prescrizione= new Label("PRESCRIZIONE : \n " + terapia.getTerapia());


            boxTerapia.getChildren().addAll(DataPrescrizione,Prescrizione);
            boxTerapia.setSpacing(5);

            items.add(boxTerapia);


            boxTerapia.setUserData(terapia);
        }
        ListViewTerapia.setFixedCellSize(150);
        ListViewTerapia.getItems().addAll(items);
    }

    @FXML
    public void goToHome() {
        try {
            Stage TerapiaPaziente = (Stage) Home.getScene().getWindow();
            TerapiaPaziente.close();
            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
