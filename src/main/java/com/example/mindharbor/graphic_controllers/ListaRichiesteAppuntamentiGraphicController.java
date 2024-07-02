package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaRichiesteAppuntamentiController;
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
import javafx.scene.image.Image;
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
import java.util.Objects;


public class ListaRichiesteAppuntamentiGraphicController {
    @FXML
    private ListView<Node> ListViewPazienti;
    @FXML
    private Label LabelNomePsicologo, Home;
    @FXML
    private Text ListaVuota;

    private final ListaRichiesteAppuntamentiController ListaRichiesteController = new ListaRichiesteAppuntamentiController();
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ListaRichiesteAppuntamentiGraphicController.class);

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = ListaRichiesteController.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        PopolaLista();
    }

    private void PopolaLista() {
        try {
            List<AppuntamentiBean> listaRichieste = ListaRichiesteController.getListaRichieste();
            if(listaRichieste.isEmpty()) {
                ListaVuota.setText("Non ci sono richieste di appuntamento");
            }else {
                CreaVBoxListaRichieste(listaRichieste);
            }
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
            ListaVuota.setText("Non esistono appuntamenti");
        }
    }

    private void CreaVBoxListaRichieste(List<AppuntamentiBean> listaRichieste) {
        ListViewPazienti.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (AppuntamentiBean appBean : listaRichieste) {

            VBox BoxPaziente= new VBox();
            HBox HBoxPaziente= new HBox();
            ImageView ImmaginePaziente= new ImageView();
            Image image;

            Label NomePaziente = new Label("\n     NOME:" + " " + appBean.getNomePaziente());
            Label CognomePaziente = new Label("     COGNOME:" + " " + appBean.getCognomePaziente());

            if((appBean.getNotificaRichiesta())==1) {
                NomePaziente.setStyle("-fx-font-weight: bold;");
                CognomePaziente.setStyle("-fx-font-weight: bold;");
            }

            NomePaziente.setTextFill(Color.WHITE);
            CognomePaziente.setTextFill(Color.WHITE);

            BoxPaziente.getChildren().addAll(NomePaziente,CognomePaziente);

            if (appBean.getGenere().equals("M")) {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
                ImmaginePaziente.setImage(image);

            } else {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
                ImmaginePaziente.setImage(image);
            }

            HBoxPaziente.getChildren().addAll(ImmaginePaziente,BoxPaziente);
            items.add(HBoxPaziente);

            HBoxPaziente.setUserData(appBean);

        }

        ListViewPazienti.setFixedCellSize(100);
        ListViewPazienti.getItems().addAll(items);
    }


    @FXML
    public void goToHome() {
        try {
            Stage richiesteAppuntamenti = (Stage) Home.getScene().getWindow();
            richiesteAppuntamenti.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void NodoSelezionato() {
        try {
            Node nodo = ListViewPazienti.getSelectionModel().getSelectedItem();
            if(nodo==null) {
                return;
            }

            AppuntamentiBean appBea =(AppuntamentiBean) nodo.getUserData();
            ListaRichiesteController.setIdRichiesta(appBea.getIdAppuntamento());

            Stage ListaRichieste = (Stage) ListViewPazienti.getScene().getWindow();
            ListaRichieste.close();

            navigator.gotoPage("/com/example/mindharbor/VerificaDisponibilità.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
