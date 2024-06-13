package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaPazientiController;
import com.example.mindharbor.app_controllers.SchedaPersonalePazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class SchedaPersonalePazienteGraphicController {
    @FXML
    private Label Home, NomePaziente, CognomePaziente, EtàPaziente, ScegliTest, PrescriviTerapia, LabelNomePsicologo, NotificaTest;
    @FXML
    private Text DiagnosiPaziente;
    @FXML
    private ImageView ImmaginePaziente, TornaIndietro;

    private String nome, cognome, username;
    SchedaPersonalePazienteController SchedaPersonaleController = new SchedaPersonalePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);

    public void initialize() {

        HomeInfoUtenteBean infoUtenteBean = SchedaPersonaleController.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        NavigatorSingleton navigator=NavigatorSingleton.getInstance();
        username=navigator.getParametro();

        navigator.eliminaParametro();
        NotificaStatoTest();

        PopolaSchedaPersonale();
    }

    private void NotificaStatoTest() {
        try {
            int count = SchedaPersonaleController.cercaNuoviTestSvoltiPaziente(username);
            if (count>0) {
                NotificaTest.setText(String.valueOf(count));
            }
        } catch (SQLException e) {
            logger.info("Errore durante la ricerca dei nuovi test svolti dal paziente " , e);
        }

    }

    private void ModificaStatoNotifica() {
        try {
            SchedaPersonaleController.modificaStatoTestSvolto(username);
        } catch (SQLException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }



    private void PopolaSchedaPersonale()  {
        try {
            PazientiBean paziente= SchedaPersonaleController.getSchedaPersonale(username);
            CreaSchedaPersonale(paziente);

        } catch (SQLException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }

    }


    private void CreaSchedaPersonale(PazientiBean paziente) {

        Image image;

        NomePaziente.setText(paziente.getNome());
        CognomePaziente.setText(paziente.getCognome());

        EtàPaziente.setText(Integer.toString(paziente.getEtà())+ " anni");

        if(paziente.getDiagnosi()==null || paziente.getDiagnosi().isEmpty()) {
            DiagnosiPaziente.setText("Diagnosi Sconosciuta");
        } else {
            DiagnosiPaziente.setText(paziente.getDiagnosi());
        }

        if (paziente.getGenere().equals("M")) {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png"));
            ImmaginePaziente.setImage(image);

        } else {
            image= new Image(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png"));
            ImmaginePaziente.setImage(image);
        }

    }

    @FXML
    private void goToHome() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    private void TornaIndietro() {
        try {
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    @FXML
    private void ScegliTest() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.setParametro(username);


            navigator.gotoPage("/com/example/mindharbor/ScegliTest.fxml");


        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
    @FXML
    public void PrescriviTerapia() {
        ModificaStatoNotifica();
    }
}

