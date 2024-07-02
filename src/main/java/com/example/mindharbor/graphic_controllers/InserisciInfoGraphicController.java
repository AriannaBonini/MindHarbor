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
    private TextField CampoNome, CampoCognome, CampoAnni;
    @FXML
    private Label LabelNomePaziente,Home, Conferma, Info;
    @FXML
    private ImageView TornaIndietro;

    private static final Logger logger = LoggerFactory.getLogger(InserisciInfoGraphicController.class);
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private final InserisciInfoController inserisciInfoController= new InserisciInfoController();
    private AppuntamentiBean appuntamento;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = inserisciInfoController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        appuntamento=inserisciInfoController.CheckAppuntamento();

        if(appuntamento.getNomePaziente()!=null && appuntamento.getCognomePaziente()!=null && appuntamento.getEtà()!=null) {
            CampoNome.setText(appuntamento.getNomePaziente());
            CampoCognome.setText(appuntamento.getCognomePaziente());
            CampoAnni.setText(String.valueOf(appuntamento.getEtà()));
        }
    }
    @FXML
    public void ClickConferma() {
        if(CampoNome.getText().isEmpty() || CampoCognome.getText().isEmpty() || CampoAnni.getText().isEmpty()) {
            new LabelDuration().Duration(Info,"Compila tutti i campi");
        } else {
            try {
                if (inserisciInfoController.CheckDati(new PazientiBean(CampoNome.getText(), CampoCognome.getText(), null, Integer.valueOf(CampoAnni.getText()), "", ""))) {
                    goToListaPsicologi();
                }else {
                    new LabelDuration().Duration(Info,"Dati errati");
                }
            }catch (DAOException e) {
                logger.info("Errore nel controllo dei dati del paziente");
            }
        }
    }
    private void goToListaPsicologi() {
        try {
            appuntamento.setNomePaziente(CampoNome.getText());
            appuntamento.setCognomePaziente(CampoCognome.getText());
            appuntamento.setEtà(Integer.valueOf(CampoAnni.getText()));

            inserisciInfoController.setAppuntamento(appuntamento);

            Stage inserisciInfo = (Stage) Conferma.getScene().getWindow();
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
            Stage inserisciInfo = (Stage) Home.getScene().getWindow();
            inserisciInfo.close();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void TornaIndietro() {
        try {
            Stage inserisciInfo = (Stage) TornaIndietro.getScene().getWindow();
            inserisciInfo.close();
            navigator.gotoPage("/com/example/mindharbor/SelezionaDataEOra.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
