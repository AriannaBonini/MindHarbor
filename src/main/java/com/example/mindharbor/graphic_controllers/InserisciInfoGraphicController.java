package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.InserisciInfoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
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
    private AppuntamentiBean appuntamentoBean;
    private PazientiBean pazienteBean;

    public void initialize() {
        InfoUtenteBean infoUtenteBean = inserisciInfoController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        appuntamentoBean =inserisciInfoController.getAppuntamentoSelezionato();

        if(appuntamentoBean.getPaziente()!=null && appuntamentoBean.getPaziente().getNome()!=null && appuntamentoBean.getPaziente().getCognome()!=null && appuntamentoBean.getPaziente().getAnni()!=null) {
            campoNome.setText(appuntamentoBean.getPaziente().getNome());
            campoCognome.setText(appuntamentoBean.getPaziente().getCognome());
            campoAnni.setText(String.valueOf(appuntamentoBean.getPaziente().getAnni()));
        }
    }

    @FXML
    public void clickConferma() {
        if(campoNome.getText().isEmpty() || campoCognome.getText().isEmpty() || campoAnni.getText().isEmpty()) {
            new LabelDuration().Duration(info,"Compila tutti i campi");
        } else {
            try {
                pazienteBean=new PazientiBean(campoNome.getText(), campoCognome.getText(), null, Integer.valueOf(campoAnni.getText()));
                if (inserisciInfoController.controllaInformazioniPaziente(pazienteBean)) {
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
            appuntamentoBean.setPaziente(pazienteBean);
            inserisciInfoController.setAppuntamento(appuntamentoBean);

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
