package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ScegliTestController;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImageDecorator;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class ScegliTestGraphicController {

    @FXML
    private Label nomePaziente;
    @FXML
    private Label cognomePaziente;
    @FXML
    private Label anniPaziente;
    @FXML
    private Label labelNomePsicologo;
    @FXML
    private Label home;
    @FXML
    private ImageView immaginePaziente;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private CheckBox test1;
    @FXML
    private CheckBox test2;
    @FXML
    private CheckBox test3;
    @FXML
    private CheckBox test4;

    private PazientiBean pazienteSelezionato;
    private static final Logger logger = LoggerFactory.getLogger(ScegliTestGraphicController.class);
    private List<TestBean> listaTestPsicologiciBean;
    private final  ScegliTestController scegliTestController = new ScegliTestController();
    private final  NavigatorSingleton navigator= NavigatorSingleton.getInstance();


    public void initialize() {
        InfoUtenteBean infoUtenteBean = scegliTestController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        pazienteSelezionato= scegliTestController.getPazienteSelezionato();

        aggiungiInformazioni();
        getTest();


    }

    private void aggiungiInformazioni() {
        nomePaziente.setText(pazienteSelezionato.getNome());
        cognomePaziente.setText(pazienteSelezionato.getCognome());

        anniPaziente.setText(pazienteSelezionato.getAnni()+ " anni");

        ImageDecorator imageDecorator= new GenereDecorator(immaginePaziente,pazienteSelezionato.getGenere());
        imageDecorator.caricaImmagine();

    }
    @FXML
    public void clickLabelHome() {
        try {
            Stage scegliTest = (Stage) home.getScene().getWindow();
            scegliTest.close();

            scegliTestController.eliminaPazienteSelezionato();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia Home dello psicologo", e);
        }

    }

    @FXML
    public void clickLabelTornaIndietro() {
        try {
            Stage scegliTest = (Stage) tornaIndietro.getScene().getWindow();
            scegliTest.close();

            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia della scheda personale del paziente", e);
        }

    }

    @FXML
    public void getTest() {
         listaTestPsicologiciBean = scegliTestController.getListaTest();

        if (listaTestPsicologiciBean != null) {
            CheckBox[] checkBoxes = {test1, test2, test3, test4};
            int numCheckBoxes = Math.min(listaTestPsicologiciBean.size(), checkBoxes.length);
            for (int i = 0; i < numCheckBoxes; i++) {
                checkBoxes[i].setText(String.valueOf(listaTestPsicologiciBean.get(i).getNomeTest()));
                checkBoxes[i].setVisible(true);
            }
            for (int i = numCheckBoxes; i < checkBoxes.length; i++) {
                checkBoxes[i].setVisible(false);
            }
        }
    }

    @FXML
    public void assegnaTest() {
        CheckBox[] checkBoxes = {test1, test2, test3, test4};
        int numCheckBoxes = Math.min(listaTestPsicologiciBean.size(), checkBoxes.length);
        int contatore = 0;
        String nomeTest = null;

        for (int i = 0; i < numCheckBoxes; i++) {
            if (checkBoxes[i].isSelected()) {
                contatore++;
                nomeTest = checkBoxes[i].getText();
            }
        }
        try {
            if(!scegliTestController.controlloNumTest(contatore,nomeTest)){
                controlloFallito();
            }else {
                controlloSuperato();
            }
        } catch (DAOException e) {
            logger.error("Errore nell'assegnazione del test", e);
        }

    }

    public void controlloFallito(){
        Alert alert = new AlertMessage().Errore("Selezionare uno ed un solo test");
        alert.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
        timeline.play();
    }

    public void controlloSuperato() {
        Alert alert = new AlertMessage().Informazione("Operazione Completata", "Esito Positivo", "Test assegnato con successo");
        alert.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            alert.close(); // Chiude l'alert
            clickLabelTornaIndietro(); // Chiama il metodo dopo la chiusura dell'alert
        }));
        timeline.play();
    }
}

