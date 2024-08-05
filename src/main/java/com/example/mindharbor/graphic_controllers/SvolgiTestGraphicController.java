package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.paziente.prescrivi_terapia.SvolgiTestController;
import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.beans.TestResultBean;
import com.example.mindharbor.constants.Constants;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvolgiTestGraphicController {
    @FXML
    private Label domanda1;
    @FXML
    private Label domanda2;
    @FXML
    private Label domanda3;
    @FXML
    private Label domanda4;
    @FXML
    private Label domanda5;
    @FXML
    private Label domanda6;
    @FXML
    private CheckBox felice1;
    @FXML
    private CheckBox felice2;
    @FXML
    private CheckBox felice3;
    @FXML
    private CheckBox felice4;
    @FXML
    private CheckBox felice5;
    @FXML
    private CheckBox felice6;

    @FXML
    private CheckBox triste1;
    @FXML
    private CheckBox triste2;
    @FXML
    private CheckBox triste3;
    @FXML
    private CheckBox triste4;
    @FXML
    private CheckBox triste5;
    @FXML
    private CheckBox triste6;

    @FXML
    private CheckBox arrabbiata1;
    @FXML
    private CheckBox arrabbiata2;
    @FXML
    private CheckBox arrabbiata3;
    @FXML
    private CheckBox arrabbiata4;
    @FXML
    private CheckBox arrabbiata5;
    @FXML
    private CheckBox arrabbiata6;

    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label home;

    @FXML
    private ImageView tornaIndietro;

    private final SvolgiTestController svolgiTestController = new SvolgiTestController();
    private DomandeTestBean domandeTestBean;
    private  Label[] labels;
    private CheckBox[][] risposteTest;

    private static final Logger logger = LoggerFactory.getLogger(SvolgiTestGraphicController.class);
    private final  NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    private TestBean testSelezionato;

    public void initialize() {
        InfoUtenteBean infoUtenteBean = svolgiTestController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        testSelezionato= svolgiTestController.getTestSelezionato();

        labels= new Label[]{domanda1, domanda2, domanda3, domanda4, domanda5, domanda6};
        risposteTest= new CheckBox[][]{{felice1, triste1, arrabbiata1}, {felice2, triste2, arrabbiata2},{felice3, triste3, arrabbiata3},
                {felice4, triste4, arrabbiata4}, {felice5, triste5, arrabbiata5}, {felice6, triste6, arrabbiata6}};

        aggiungiDomande();

    }

    private void aggiungiDomande() {
        domandeTestBean = svolgiTestController.cercaDomande(testSelezionato);

        int numLabels = Math.min(domandeTestBean.getDomande().size(), labels.length);
        for (int i = 0; i < numLabels; i++) {
            labels[i].setText(domandeTestBean.getDomande().get(i));
            labels[i].setWrapText(true);
            labels[i].setVisible(true);
            risposteTest[i][0].setVisible(true);
            risposteTest[i][1].setVisible(true);
            risposteTest[i][2].setVisible(true);

        }
    }


   @FXML
    public void clickLabelHome() {
        try {
            Integer risposta= new AlertMessage().Avvertenza("Sei sicuro di voler tornare alla Home?");
            if (risposta!=0) {
                svolgiTestController.eliminaTestSelezionato();

                Stage svolgiTest = (Stage) home.getScene().getWindow();
                svolgiTest.close();
                navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");
            }
        } catch (IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }
    }

    @FXML
    public void clickLabelTornaIndietro() {
        try {
            Integer risposta= new AlertMessage().Avvertenza("Sei sicuro di voler tornare indietro?");
            if (risposta!=0) {
                svolgiTestController.eliminaTestSelezionato();

                Stage svolgiTest = (Stage) tornaIndietro.getScene().getWindow();
                svolgiTest.close();

                navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");
            }
        }catch(IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }

    }

    @FXML
    public void concludiTest()  {
        int numCheckBoxs = Math.min(domandeTestBean.getDomande().size(), labels.length);
        int count;
        DomandeTestBean punteggiBean=new DomandeTestBean();
        List<Integer> punteggi=new ArrayList<>();

        for(int i = 0; i< numCheckBoxs; i++) {
            count=0;
            for(int j=0; j<3; j++) {
                if(risposteTest[i][j].isSelected()) {
                    count++;
                    punteggi.add(domandeTestBean.getPunteggi().get(j));
                }
                punteggiBean.setPunteggi(punteggi);
            }

            if (count!=1) {
                Alert alert= new AlertMessage().Errore("Selezionare una sola risposta per ogni domanda");
                alert.show();

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
                timeline.play();
                return;
            }
        }

        try {
            TestResultBean risultatoTest = svolgiTestController.calcolaRisultato(punteggiBean,testSelezionato);

            if (risultatoTest.getRisultatoTestPrecedente() == null) {
                notificaProgresso("Risultato test: " + risultatoTest.getRisultatoUltimoTest(), "Complimenti! Hai svolto il tuo primo test");

            } else {
                if (risultatoTest.getRisultatoTestPrecedente() > 0) {
                    notificaProgresso("Progresso: " + risultatoTest.getRisultatoTestPrecedente() + "%", "Complimenti!");
                } else {
                    notificaProgresso("Regresso: " + risultatoTest.getRisultatoTestPrecedente() + "%", "Mi dispiace!");
                }
            }
        }catch (DAOException e) {
            logger.info("Errore nel calcolo del risultato del test ", e);
        }
    }

    private void notificaProgresso(String messaggio, String header) {
        Alert alert= new AlertMessage().Informazione("Test Concluso", header,messaggio);

        alert.getButtonTypes().setAll(ButtonType.OK);
        ButtonType risultato = alert.showAndWait().orElse(ButtonType.OK);
        if (risultato ==ButtonType.OK) {
            alert.close();
            testConclusoTornaAllaListaTest();
        }
    }

    private void testConclusoTornaAllaListaTest() {
        Stage svolgiTest = (Stage) tornaIndietro.getScene().getWindow();
        svolgiTest.close();
        try {
            svolgiTestController.eliminaTestSelezionato();

            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");
        } catch (IOException e) {
            logger.info(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }
    }
}

