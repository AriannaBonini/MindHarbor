package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ScegliTestController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.Decorator.GenereDecorator;
import com.example.mindharbor.patterns.Decorator.ImageDecorator;
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
    private Label NomePaziente, CognomePaziente, EtàPaziente, LabelNomePsicologo, Home;
    @FXML
    private ImageView ImmaginePaziente, TornaIndietro;
    @FXML
    private CheckBox Test1, Test2, Test3, Test4;
    private String username;
    private static final Logger logger = LoggerFactory.getLogger(ScegliTestGraphicController.class);
    private List<String> listaTestPsicologici;
    private final  ScegliTestController scegliTest= new ScegliTestController();
    private final  NavigatorSingleton navigator= NavigatorSingleton.getInstance();


    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = scegliTest.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        username=scegliTest.getUsername();

        getInfoPaziente();
        getTest();


    }

    private void getInfoPaziente() {
        try {
            PazientiBean paziente= scegliTest.getInfoPaziente(username);
            AggiungiInformazioni(paziente);
        } catch (DAOException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }
    }

    private void AggiungiInformazioni(PazientiBean paziente) {

        NomePaziente.setText(paziente.getNome());
        CognomePaziente.setText(paziente.getCognome());

        EtàPaziente.setText(paziente.getAnni()+ " anni");

        ImageDecorator imageDecorator= new GenereDecorator(ImmaginePaziente,paziente.getGenere());
        imageDecorator.loadImage();

    }
    @FXML
    public void goToHome() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    public void TornaIndietro() {
        try {
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            scegliTest.setUsername(username);
            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    public void getTest() {
         listaTestPsicologici=scegliTest.getListaTest();

        if (listaTestPsicologici != null) {
            CheckBox[] checkBoxes = {Test1, Test2, Test3, Test4};
            int numCheckBoxes = Math.min(listaTestPsicologici.size(), checkBoxes.length);
            for (int i = 0; i < numCheckBoxes; i++) {
                checkBoxes[i].setText(listaTestPsicologici.get(i));
                checkBoxes[i].setVisible(true);
            }
            for (int i = numCheckBoxes; i < checkBoxes.length; i++) {
                checkBoxes[i].setVisible(false);
            }
        }
    }

    @FXML
    public void AssegnaTest() {
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

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));

            timeline.play();
        } else {
            try {
                scegliTest.notificaTest(username, nomeTest);

                Alert alert= new AlertMessage().Informazione("Operazione Completata","Esito Positivo", "Test assegnato con successo");
                alert.show();

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));

                timeline.play();
            } catch (DAOException e) {
                logger.info("Errore assegnazione Test");
                Alert alert= new AlertMessage().Errore("Limite test raggiunto per questo paziente");
                alert.show();

                 Timeline timeline= new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
                 timeline.play();
            }

        }
    }
}
