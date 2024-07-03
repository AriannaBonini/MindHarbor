package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.InserisciInfoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.LabelDuration;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class InserisciInfoGraphicController {
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCognome;
    @FXML
    private TextField campoAnni;
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label home;
    @FXML
    private Label conferma;
    @FXML
    private Label info;
    @FXML
    private ImageView tornaIndietro;

    private static final Logger logger = LoggerFactory.getLogger(InserisciInfoGraphicController.class);
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private final InserisciInfoController inserisciInfoController= new InserisciInfoController();
    private AppuntamentiBean appuntamento;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = inserisciInfoController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        appuntamento=inserisciInfoController.checkAppuntamento();

        if(appuntamento.getNomePaziente()!=null && appuntamento.getCognomePaziente()!=null && appuntamento.getAnni()!=null) {
            campoNome.setText(appuntamento.getNomePaziente());
            campoCognome.setText(appuntamento.getCognomePaziente());
            campoAnni.setText(String.valueOf(appuntamento.getAnni()));
        }
    }
    @FXML
    public void clickConferma() {
        if(campoNome.getText().isEmpty() || campoCognome.getText().isEmpty() || campoAnni.getText().isEmpty()) {
            new LabelDuration().Duration(info,"Compila tutti i campi");
        } else {
            try {
                if (inserisciInfoController.checkDati(new PazientiBean(campoNome.getText(), campoCognome.getText(), null, Integer.valueOf(campoAnni.getText()), "", ""))) {
                    goToListaPsicologi();
                }else {
                    new LabelDuration().Duration(info,"Dati errati");
                }
            }catch (DAOException e) {
                logger.info("Errore nel controllo dei dati del paziente");
            }
        }
    }
    private void goToListaPsicologi() {
        try {
            appuntamento.setNomePaziente(campoNome.getText());
            appuntamento.setCognomePaziente(campoCognome.getText());
            appuntamento.setAnni(Integer.valueOf(campoAnni.getText()));

            inserisciInfoController.setAppuntamento(appuntamento);

            Stage inserisciInfo = (Stage) conferma.getScene().getWindow();
            inserisciInfo.close();

            navigator.gotoPage("/com/example/mindharbor/ListaPsicologi.fxml");
        }catch (IOException e) {
            logger.info("Impossibile caricare l'interfaccia ", e);
        }
    }

    @FXML
    public void goToHome() {
        try {
            inserisciInfoController.deleteAppuntamento();
            Stage inserisciInfo = (Stage) home.getScene().getWindow();
            inserisciInfo.close();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage inserisciInfo = (Stage) tornaIndietro.getScene().getWindow();
            inserisciInfo.close();
            navigator.gotoPage("/com/example/mindharbor/SelezionaDataEOra.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
