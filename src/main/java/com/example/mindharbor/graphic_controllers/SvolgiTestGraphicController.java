package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.SvolgiTestController;
import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class SvolgiTestGraphicController {
    @FXML
    private Label Domanda1;
    @FXML
    private Label Domanda2;
    @FXML
    private Label Domanda3;
    @FXML
    private Label Domanda4;
    @FXML
    private Label Domanda5;
    @FXML
    private Label Domanda6;
    @FXML
    private CheckBox Felice1;
    @FXML
    private Label Home;
    @FXML
    private Label LabelNomePaziente;

    private String nome;
    private String cognome;

    SvolgiTestController controller= new SvolgiTestController();

    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = controller.getPagePazInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePaziente.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();
        String nomeTest=navigator.getParametro();

        navigator.eliminaParametro();

        AggiungiDomande(nomeTest);

    }

    public void AggiungiDomande(String nomeTest) {
        List<DomandeTestBean> domandeBean= controller.CercaDomande(nomeTest);

    }


   @FXML
    public void goToHome() {
        try {

            Stage SvolgiTest = (Stage) Home.getScene().getWindow();
            SvolgiTest.close();


            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}

