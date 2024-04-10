package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.AppuntamentiPsicologoController;
import com.example.mindharbor.app_controllers.ListaPazientiController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListaPazientiGraphicController {

    @FXML
    private Label ScorriListasu;

    @FXML
    private Label ScorriListagiu;

    @FXML
    private VBox BoxListaPazienti;

    @FXML
    private Label NomePaziente;

    @FXML
    private Label CognomePaziente;

    @FXML
    private ImageView ImmagineProfiloUomo;

    @FXML
    private ImageView ImmagineProfiloDonna;

    @FXML
    private Label LabelNomePsicologo;

    @FXML
    private Label Home;

    @FXML
    private Text ListaVuota;

    private String nome;
    private String cognome;

    @FXML
    private VBox BoxPaziente;

    @FXML
    private ListView<Node> ListViewPazienti;






    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);


    public void initialize() {

        ListaPazientiController ListaController = new ListaPazientiController();

        HomeInfoUtenteBean infoUtenteBean = ListaController.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        PopolaLista();
    }

    public void PopolaLista() {
        try {
            List<PazientiBean> listaPazienti = ListaPazientiController.getListaPazienti();
            CreaVBoxListaPazienti(listaPazienti);

        }catch (SQLException e) {
            logger.info("Non non ci sono appuntamenti", e);
            ListaVuota.setText("Non esistono appuntamenti");
            ScorriListagiu.setDisable(true);
            ScorriListasu.setDisable(true);
        }
    }


    public void CreaVBoxListaPazienti( List<PazientiBean> listaPazienti) {
        ListViewPazienti.getItems().clear();

        BoxListaPazienti.setSpacing(20);

        ObservableList<VBox> pazienteInfo = FXCollections.observableArrayList();
        ObservableList<ImageView> pazienteIcona = FXCollections.observableArrayList();

        for (PazientiBean paz : listaPazienti) {

            VBox BoxLabel = new VBox();

            Label NomePaziente = new Label("NOME:" + " " + paz.getNome());
            Label CognomePaziente = new Label("COGNOME:" + " " + paz.getCognome());

            NomePaziente.setTextFill(Color.WHITE);
            CognomePaziente.setTextFill(Color.WHITE);

            BoxLabel.getChildren().addAll(NomePaziente, CognomePaziente);
            pazienteInfo.add(BoxLabel);

            if (paz.getGenere().equals("M")) {
                ImmagineProfiloUomo.setVisible(true);
                pazienteIcona.add(ImmagineProfiloUomo);

            } else {
                ImmagineProfiloDonna.setVisible(true);
                pazienteIcona.add(ImmagineProfiloDonna);
            }

        }

        ObservableList<Node> items = FXCollections.observableArrayList();
        for (int i = 0; i < pazienteInfo.size(); i++) {
            HBox pazienteBox = new HBox();
            pazienteBox.getChildren().addAll(pazienteIcona.get(i), pazienteInfo.get(i));
            items.add(pazienteBox);
        }

        ListViewPazienti.setFixedCellSize(70);
        ListViewPazienti.getItems().addAll(items);
    }

    public void goToHome() {
        try {
            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

            Stage ListaPazienti = (Stage) Home.getScene().getWindow();
            ListaPazienti.close();

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

}
