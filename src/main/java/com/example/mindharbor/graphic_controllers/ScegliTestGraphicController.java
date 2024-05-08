package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ScegliTestController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class ScegliTestGraphicController {

    @FXML
    private Label NomePaziente;
    @FXML
    private Label CognomePaziente;
    @FXML
    private Label EtàPaziente;
    @FXML
    private Label LabelNomePsicologo;
    @FXML
    private ImageView ImmaginePaziente;
    @FXML
    private CheckBox Test1;
    @FXML
    private CheckBox Test2;
    @FXML
    private CheckBox Test3;
    @FXML
    private CheckBox Test4;
    @FXML
    private Label Home;
    @FXML
    private ImageView TornaIndietro;

    private String nome;
    private String cognome;
    private String username;
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);



    public void initialize() {
        ScegliTestController scegliTest= new ScegliTestController();

        HomeInfoUtenteBean infoUtenteBean = scegliTest.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();
        username=navigator.getParametro();

        navigator.eliminaParametro();

        getInfoPaziente();

        getTest();


    }

    public void getInfoPaziente() {
        try {
            PazientiBean paziente= ScegliTestController.getInfoPaziente(username);
            AggiungiInformazioni(paziente);

        } catch (SQLException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }
    }

    public void AggiungiInformazioni(PazientiBean paziente) {
        Image image;

        NomePaziente.setText(paziente.getNome());
        CognomePaziente.setText(paziente.getCognome());

        EtàPaziente.setText(Integer.toString(paziente.getEtà())+ " anni");

        if (paziente.getGenere().equals("M")) {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png"));
            ImmaginePaziente.setImage(image);

        } else {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png"));
            ImmaginePaziente.setImage(image);
        }

    }

    public void goToHome() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    public void TornaIndietro() {
        try {
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.setParametro(username);
            navigator.gotoPage("/com/example/mindharbor/SchedaPersonalePaziente.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    public void getTest() {



    }

}
