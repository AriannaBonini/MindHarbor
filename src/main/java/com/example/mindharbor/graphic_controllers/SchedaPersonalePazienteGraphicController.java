package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia.SchedaPersonalePazienteController;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.constants.Constants;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImageDecorator;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class SchedaPersonalePazienteGraphicController {
    @FXML
    private Label home;
    @FXML
    private Label nomePaziente;
    @FXML
    private Label cognomePaziente;
    @FXML
    private Label anniPaziente;
    @FXML
    private Label prescriviTerapia;
    @FXML
    private Label labelNomePsicologo;
    @FXML
    private Label notificaTest;
    @FXML
    private Text diagnosiPaziente;
    @FXML
    private ImageView immaginePaziente;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private Label scegliTest;

    private PazienteBean pazienteSelezionato;
    private final SchedaPersonalePazienteController schedaPersonaleController = new SchedaPersonalePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(SchedaPersonalePazienteGraphicController.class);
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void initialize() {
        InfoUtenteBean infoUtenteBean = schedaPersonaleController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        pazienteSelezionato = schedaPersonaleController.getPazienteSelezionato();
        notificaStatoTest();
        abilitaPrescriviTerapia();
        abilitaAssegnaTest();

        if (pazienteSelezionato.getAnni() == null && pazienteSelezionato.getDiagnosi() == null) {
            // Questo if controlla se c'è già stato un accesso alla DAO precedentemente.
            // Ci permette di gestire la possibilità che lo psicologo, abbia selezionato il "Torna Indietro" dall'interfaccia immediatamente successiva a questa.
            popolaSchedaPersonale();
        } else {
            creaSchedaPersonale();
        }
    }

    private void abilitaPrescriviTerapia() {
        try {
            if (schedaPersonaleController.numTestSvoltiSenzaPrescrizione(pazienteSelezionato) > 0) {
                prescriviTerapia.setDisable(false);
            }
        }catch (DAOException e) {
            logger.info("Errore nella ricerca dei test svolti dal paziente senza prescrizione" , e);
        }
    }

    private void notificaStatoTest() {
        if (pazienteSelezionato.getNumTestSvolti()>0) {
            notificaTest.setText(String.valueOf(pazienteSelezionato.getNumTestSvolti()));
        }else {
            notificaTest.setVisible(false);
        }
    }

    private void abilitaAssegnaTest() {
        try {
            if (schedaPersonaleController.getNumTestOdiernoAssegnato(pazienteSelezionato)>0) {
                scegliTest.setDisable(true);
            }
        }catch (DAOException e) {
            logger.info("Errore durante il controllo della presenza di un test già assegnato al paziente nella giornata odierna" , e);

        }
    }


    private void popolaSchedaPersonale()  {
        try {
            pazienteSelezionato = schedaPersonaleController.getSchedaPersonale(pazienteSelezionato);
            creaSchedaPersonale();

        } catch (DAOException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }

    }


    private void creaSchedaPersonale() {

        nomePaziente.setText(pazienteSelezionato.getNome());
        cognomePaziente.setText(pazienteSelezionato.getCognome());

        anniPaziente.setText(pazienteSelezionato.getAnni()+ " anni");

        if(pazienteSelezionato.getDiagnosi()==null || pazienteSelezionato.getDiagnosi().isEmpty()) {
            diagnosiPaziente.setText("Diagnosi Sconosciuta");
        } else {
            diagnosiPaziente.setText(pazienteSelezionato.getDiagnosi());
        }

        ImageDecorator imageDecorator= new GenereDecorator(immaginePaziente,pazienteSelezionato.getGenere());
        imageDecorator.caricaImmagine();
    }

    @FXML
    public void goToHome() {
        try {
            Stage schedaPersonale = (Stage) home.getScene().getWindow();
            schedaPersonale.close();

            schedaPersonaleController.deletePazienteSelezionato();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }

    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage schedaPersonale = (Stage) tornaIndietro.getScene().getWindow();
            schedaPersonale.close();
            schedaPersonaleController.deletePazienteSelezionato();
            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");
        }catch(IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }

    }


    @FXML
    public void scegliTest() {
        try {
            Stage schedaPersonale = (Stage) home.getScene().getWindow();
            schedaPersonale.close();
            schedaPersonaleController.setPazienteSelezionato(pazienteSelezionato);
            navigator.gotoPage("/com/example/mindharbor/ScegliTest.fxml");
        }catch(IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }
    }
    @FXML
    public void prescriviTerapia() {
        try {
            Stage schedaPersonale = (Stage) prescriviTerapia.getScene().getWindow();
            schedaPersonale.close();
            schedaPersonaleController.setPazienteSelezionato(pazienteSelezionato);
            navigator.gotoPage("/com/example/mindharbor/PrescrizioneTerapia.fxml");
        }catch(IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }

    }

}


