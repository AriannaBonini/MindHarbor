package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.SvolgiTestController;
import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestResultBean;
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
import java.util.Date;
import java.util.List;

public class SvolgiTestGraphicController {
    @FXML
    private Label Domanda1, Domanda2, Domanda3, Domanda4, Domanda5, Domanda6;

    @FXML
    private CheckBox Felice1, Felice2, Felice3,Felice4,Felice5,Felice6;

    @FXML
    private CheckBox Triste1, Triste2, Triste3, Triste4, Triste5, Triste6;
    @FXML
    private CheckBox Arrabbiata1, Arrabbiata2, Arrabbiata3, Arrabbiata4, Arrabbiata5, Arrabbiata6;

    @FXML
    private Label LabelNomePaziente, Home;

    @FXML
    private ImageView TornaIndietro;
    private final SvolgiTestController controller= new SvolgiTestController();
    private DomandeTestBean domandeBean;
    private String nomeTest;

    private  Label[] labels;
    private Date dataTest;

    private CheckBox[][] risposteTest;

    private static final Logger logger = LoggerFactory.getLogger(SvolgiTestGraphicController.class);
    private final  NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = controller.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        nomeTest=controller.getNomeTest();
        dataTest=controller.getData();

        labels= new Label[]{Domanda1, Domanda2, Domanda3, Domanda4, Domanda5, Domanda6};
        risposteTest= new CheckBox[][]{{Felice1,Triste1,Arrabbiata1}, {Felice2,Triste2,Arrabbiata2},{Felice3,Triste3,Arrabbiata3},
                {Felice4,Triste4,Arrabbiata4}, {Felice5,Triste5,Arrabbiata5}, {Felice6,Triste6,Arrabbiata6}};

        AggiungiDomande(nomeTest);

    }

    private void AggiungiDomande(String nomeTest) {
        domandeBean= controller.CercaDomande(nomeTest);

        int numLabels = Math.min(domandeBean.getDomande().size(), labels.length);
        for (int i = 0; i < numLabels; i++) {
            labels[i].setText(domandeBean.getDomande().get(i));
            labels[i].setWrapText(true);
            labels[i].setVisible(true);
            risposteTest[i][0].setVisible(true);
            risposteTest[i][1].setVisible(true);
            risposteTest[i][2].setVisible(true);

        }
    }


   @FXML
    public void goToHome() {
        try {
            Integer risposta= new AlertMessage().Avvertenza("Sei sicuro di voler tornare indietro?");
            if (risposta==0) {
                return;
            }
            Stage SvolgiTest = (Stage) Home.getScene().getWindow();
            SvolgiTest.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void TornaIndietro() {
        try {
            Integer risposta= new AlertMessage().Avvertenza("Sei sicuro di voler tornare indietro?");
            if (risposta==0) {
                return;
            }
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    public void ConcludiTest()  {
        int numCheckBoxes = Math.min(domandeBean.getDomande().size(), labels.length);
        int count;
        List<Integer> punteggio=new ArrayList<>();

        for(int i=0;i<numCheckBoxes;i++) {
            count=0;
            for(int j=0; j<3; j++) {
                if(risposteTest[i][j].isSelected()) {
                    count++;
                    punteggio.add(domandeBean.getPunteggi().get(j));
                }
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

            TestResultBean testResult = controller.calcolaRisultato(punteggio, dataTest, nomeTest);

            if (testResult.getRisultatoTestPrecedente() == null) {
                notificaProgresso("Risultato test: " + testResult.getRisultatoUltimoTest(), "Complimenti! Hai svolto il tuo primo test");

            } else {
                if (testResult.getRisultatoTestPrecedente() > 0) {
                    notificaProgresso("Progresso: " + testResult.getRisultatoTestPrecedente() + "%", "Complimenti!");
                } else {
                    notificaProgresso("Regresso: " + testResult.getRisultatoTestPrecedente() + "%", "Mi dispiace!");
                }
            }
        }catch (DAOException e) {
            logger.info("Errore nel calcolo del risultato del test ", e);


        }
    }

    private void notificaProgresso(String messaggio, String Header ) {
        Alert alert= new AlertMessage().Informazione("Test Concluso", Header,messaggio);

        alert.getButtonTypes().setAll(ButtonType.OK);
        ButtonType result = alert.showAndWait().orElse(ButtonType.OK);
        if (result==ButtonType.OK) {
            alert.close();
            indirizzaAllaListaTest();
        }
    }

    private void indirizzaAllaListaTest() {
        Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
        SchedaPersonale.close();

        try {
            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");
        } catch (IOException e) {
            logger.info("Impossibile caricare l'interfaccia ", e);
        }
    }
}

