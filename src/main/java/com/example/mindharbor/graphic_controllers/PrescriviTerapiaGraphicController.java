package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.PrescriviTerapiaController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TerapiaBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PrescriviTerapiaGraphicController {
    @FXML
    private Label campoNome, campoCognome, campoData, Home, LabelNomePsicologo;
    @FXML
    private TextArea campoPrescrizione;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private ListView<Node> ListViewTest;
    @FXML
    private Text campoInfoTest;


    private static final Logger logger = LoggerFactory.getLogger(PrescriviTerapiaGraphicController.class);
    private final   NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private String username;
    private Utente utentePaziente;
    private TestBean testbean;
    private final PrescriviTerapiaController prescriviController= new PrescriviTerapiaController();


    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = prescriviController.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        username=prescriviController.getUsername();

        ModificaStatoNotifica();
        TrovaPaziente();
        PopolaListaTestSvoltiSenzaPrescrizione();

    }

    private void TrovaPaziente() {
        try{
            utentePaziente=prescriviController.ricercaNomeCognomePaziente(username);
        }catch (DAOException e) {
            logger.info("Errore nella ricerca del nome e cognome del paziente ", e);
        }
    }

    private void PopolaListaTestSvoltiSenzaPrescrizione() {
        try {
            List<TestBean> testSvoltiBean = prescriviController.getTestSvoltiSenzaPrescrizione(username);
            PopolaAreaTest(testSvoltiBean);
        } catch (DAOException e) {
            logger.info("Errore nella ricerca dei test svolti senza ancora prescrizioni ", e);
        }
    }

    private void PopolaAreaTest(List<TestBean> testSvoltiBean) {
        ListViewTest.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();
        for (TestBean testBean : testSvoltiBean) {
            VBox AreaTest= new VBox();

            Label dataTest= new Label("DATA: " + testBean.getData());
            Label nomeTest= new Label("TEST: " + testBean.getNomeTest());
            Label risultatoTest= new Label("RISULTATO: "+ testBean.getRisultato());

            AreaTest.getChildren().addAll(dataTest,nomeTest,risultatoTest);
            AreaTest.setSpacing(5);

            items.add(AreaTest);

            AreaTest.setUserData(testBean);
        }
        ListViewTest.setFixedCellSize(100);
        ListViewTest.getItems().addAll(items);

    }


    private void ModificaStatoNotifica() {
        try {
            prescriviController.modificaStatoTestSvolto(username);
        } catch (DAOException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }

    @FXML
    public void goToHome() {
        try {
            Stage prescriviTerapia = (Stage) Home.getScene().getWindow();
            prescriviTerapia.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage prescriviTerapia = (Stage) tornaIndietro.getScene().getWindow();
            prescriviTerapia.close();

            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void NodoSelezionato() {
        Node nodo= ListViewTest.getSelectionModel().getSelectedItem();
        if (nodo == null) {
            return;
        }
        testbean=(TestBean) nodo.getUserData();
        campoNome.setText(utentePaziente.getNome());
        campoCognome.setText(utentePaziente.getCognome());
        campoData.setText(String.valueOf(LocalDate.now()));
        campoInfoTest.setText("Risultato Test: " + testbean.getRisultato());
    }

    @FXML
    public void Prescrivi() {
        String testoInserito= campoPrescrizione.getText();
        if (testoInserito.isEmpty()) {
            Alert alert= new AlertMessage().Errore("Inserisci la prescrizione");
            alert.show();

            new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
        } else {
            try {
            Date currentDate= new Date();
            prescriviController.aggiungiTerapia(new TerapiaBean(SessionManager.getInstance().getCurrentUser().getUsername(),username,testoInserito,currentDate,testbean.getData()));
            Alert alert= new AlertMessage().Informazione("ESITO POSITIVO", "Operazione completata", "Terapia assegnata con successo");
            new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));

            } catch (DAOException e) {
            logger.info("Errore durante il salvataggio della terapia ", e );
                Alert alert= new AlertMessage().Errore("Una sola prescrizione per test");
                alert.show();

                new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            }
        }

    }
}

