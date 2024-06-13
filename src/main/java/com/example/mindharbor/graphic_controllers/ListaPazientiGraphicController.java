package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.app_controllers.ListaPazientiController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListaPazientiGraphicController {

    @FXML
    private HBox HBoxPaziente;
    @FXML
    private Label NomePaziente, CognomePaziente, LabelNomePsicologo, Home;
    @FXML
    private ImageView ImmaginePaziente;
    @FXML
    private Text ListaVuota;
    @FXML
    private VBox BoxPaziente;
    @FXML
    private ListView<Node> ListViewPazienti;

    private String nome, cognome;
    ListaPazientiController ListaPazientiController = new ListaPazientiController();
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);


    public void initialize() {

        HomeInfoUtenteBean infoUtenteBean = ListaPazientiController.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        PopolaLista();
    }


    private void PopolaLista() {
        try {
            List<PazientiNumTestBean> listaPazienti = ListaPazientiController.getListaPazienti();

            CreaVBoxListaPazienti(listaPazienti);

        }catch (SQLException e) {
            logger.info("Non non ci sono appuntamenti", e);
            ListaVuota.setText("Non esistono appuntamenti");
        }
    }


    private void CreaVBoxListaPazienti( List<PazientiNumTestBean> listaPazienti) {
        ListViewPazienti.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (PazientiNumTestBean paz : listaPazienti) {

            VBox BoxPaziente= new VBox();
            HBox HBoxPaziente= new HBox();
            ImageView ImmaginePaziente= new ImageView();
            Image image;

            Label NomePaziente = new Label("\n     NOME:" + " " + paz.getNome());
            Label CognomePaziente = new Label("     COGNOME:" + " " + paz.getCognome());

            if(paz.getNumTest()>0) {
                NomePaziente.setStyle("-fx-font-weight: bold;");
                CognomePaziente.setStyle("-fx-font-weight: bold;");

            }

            NomePaziente.setTextFill(Color.WHITE);
            CognomePaziente.setTextFill(Color.WHITE);

            BoxPaziente.getChildren().addAll(NomePaziente,CognomePaziente);

            if (paz.getGenere().equals("M")) {
                image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png"));
                ImmaginePaziente.setImage(image);

            } else {
                image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png"));
                ImmaginePaziente.setImage(image);
            }

            HBoxPaziente.getChildren().addAll(ImmaginePaziente,BoxPaziente);
            items.add(HBoxPaziente);

            HBoxPaziente.setUserData(paz);

        }

        ListViewPazienti.setFixedCellSize(100);
        ListViewPazienti.getItems().addAll(items);
    }


    @FXML
    private void goToHome() {
        try {
            Stage ListaPazienti = (Stage) Home.getScene().getWindow();
            ListaPazienti.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    private void NodoSelezionato() {
        try {
            Node nodo = ListViewPazienti.getSelectionModel().getSelectedItem();


            if(nodo==null) {
                return;
            }
            PazientiNumTestBean paziente =(PazientiNumTestBean) nodo.getUserData();
            String username=paziente.getUsername();


            Stage ListaPazienti = (Stage) ListViewPazienti.getScene().getWindow();
            ListaPazienti.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.setParametro(username);

            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");



        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

}
