package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ScegliTestController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.patterns.Observer;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wiremock.org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScegliTestGraphicController {

    @FXML
    private Label NomePaziente;
    @FXML
    private Label CognomePaziente;
    @FXML
    private Label EtàPaziente;
    @FXML
    private Label LabelNomePsicologo;
    @FXML
    private ImageView ImmaginePaziente;
    @FXML
    private CheckBox Test1;
    @FXML
    private CheckBox Test2;
    @FXML
    private CheckBox Test3;
    @FXML
    private CheckBox Test4;
    @FXML
    private Label Home;
    @FXML
    private ImageView TornaIndietro;

    private String nome;
    private String cognome;
    private String username;
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);
    private List<String> listaTestPsicologici;

    ScegliTestController scegliTest= new ScegliTestController();


    public void initialize() {

        HomeInfoUtenteBean infoUtenteBean = scegliTest.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();
        username=navigator.getParametro();

        navigator.eliminaParametro();

        getInfoPaziente();

        getTest();


    }

    public void getInfoPaziente() {
        try {
            PazientiBean paziente= ScegliTestController.getInfoPaziente(username);
            AggiungiInformazioni(paziente);

        } catch (SQLException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }
    }

    public void AggiungiInformazioni(PazientiBean paziente) {
        Image image;

        NomePaziente.setText(paziente.getNome());
        CognomePaziente.setText(paziente.getCognome());

        EtàPaziente.setText(Integer.toString(paziente.getEtà())+ " anni");

        if (paziente.getGenere().equals("M")) {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png"));
            ImmaginePaziente.setImage(image);

        } else {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png"));
            ImmaginePaziente.setImage(image);
        }

    }

    public void goToHome() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    public void TornaIndietro() {
        try {
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.setParametro(username);
            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    public void getTest() {
         listaTestPsicologici=ScegliTestController.getListaTest();

        if (listaTestPsicologici != null) {
            CheckBox[] checkBoxes = {Test1, Test2, Test3, Test4}; // Array di CheckBox
            int numCheckBoxes = Math.min(listaTestPsicologici.size(), checkBoxes.length); // Numero di CheckBox da popolare
            for (int i = 0; i < numCheckBoxes; i++) {
                checkBoxes[i].setText(listaTestPsicologici.get(i)); // Imposta il testo del CheckBox con il nome del test corrispondente
                checkBoxes[i].setVisible(true); // Rendi il CheckBox visibile
            }
            // Nascondi i CheckBox rimanenti se ce ne sono più di quattro
            for (int i = numCheckBoxes; i < checkBoxes.length; i++) {
                checkBoxes[i].setVisible(false);
            }
        }
    }

    public void AssegnaTest(MouseEvent mouseEvent) {
        CheckBox[] checkBoxes = {Test1, Test2, Test3, Test4};
        int numCheckBoxes = Math.min(listaTestPsicologici.size(), checkBoxes.length);
        int count=0;
        String nomeTest=null;

        for(int i=0; i<numCheckBoxes;i++) {
            if(checkBoxes[i].isSelected()) {
                count++;
                nomeTest= checkBoxes[i].getText();

            }
        }
        if (count!=1) {
            Alert alert= new AlertMessage().Errore("Selezionare uno ed un solo test");
            alert.show();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                alert.close();
            }));

            timeline.play();
        } else {
            try {
                scegliTest.NotificaTest(username, nomeTest);
            } catch (SQLException e) {
                logger.info("Errore assegnazione Test");
            }

            Alert alert= new AlertMessage().Informazione("Test assegnato con successo");
            alert.show();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                alert.close();
            }));

            timeline.play();
        }
    }
}
