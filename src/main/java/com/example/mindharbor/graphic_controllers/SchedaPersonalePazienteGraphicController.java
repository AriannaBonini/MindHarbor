package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.SchedaPersonalePazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
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

    private String username;
    private final SchedaPersonalePazienteController schedaPersonaleController = new SchedaPersonalePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(SchedaPersonalePazienteGraphicController.class);
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = schedaPersonaleController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        username= schedaPersonaleController.getUsername();

        notificaStatoTest();
        popolaSchedaPersonale();
        abilitaPrescriviTerapia();
    }

    private void abilitaPrescriviTerapia() {
        try {
            if (schedaPersonaleController.NumTestSvoltiSenzaPrescrizione(username) > 0) {
                prescriviTerapia.setDisable(false);
            }
        }catch (DAOException e) {
            logger.info("Errore nella ricerca dei test svolti dal paziente senza prescrizione" , e);
        }
    }

    private void notificaStatoTest() {
        try {
            int count = schedaPersonaleController.cercaNuoviTestSvoltiPazienteDaNotificare(username);
            if (count>0) {
                notificaTest.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore durante la ricerca dei nuovi test svolti dal paziente " , e);
        }

    }


    private void popolaSchedaPersonale()  {
        try {
            PazientiBean paziente= schedaPersonaleController.getSchedaPersonale(username);
            creaSchedaPersonale(paziente);

        } catch (DAOException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }

    }


    private void creaSchedaPersonale(PazientiBean paziente) {

        nomePaziente.setText(paziente.getNome());
        cognomePaziente.setText(paziente.getCognome());

        anniPaziente.setText(paziente.getAnni()+ " anni");

        if(paziente.getDiagnosi()==null || paziente.getDiagnosi().isEmpty()) {
            diagnosiPaziente.setText("Diagnosi Sconosciuta");
        } else {
            diagnosiPaziente.setText(paziente.getDiagnosi());
        }

        ImageDecorator imageDecorator= new GenereDecorator(immaginePaziente,paziente.getGenere());
        imageDecorator.loadImage();
    }

    @FXML
    public void goToHome() {
        try {
            Stage schedaPersonale = (Stage) home.getScene().getWindow();
            schedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }

    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage schedaPersonale = (Stage) tornaIndietro.getScene().getWindow();
            schedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }

    }


    @FXML
    public void scegliTest() {
        try {
            Stage schedaPersonale = (Stage) home.getScene().getWindow();
            schedaPersonale.close();

            schedaPersonaleController.setUsername(username);

            navigator.gotoPage("/com/example/mindharbor/ScegliTest.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }
    }
    @FXML
    public void prescriviTerapia() {
        try {
            Stage schedaPersonale = (Stage) prescriviTerapia.getScene().getWindow();
            schedaPersonale.close();

            schedaPersonaleController.setUsername(username);
            navigator.gotoPage("/com/example/mindharbor/PrescrizioneTerapia.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }

    }

}


