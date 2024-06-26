package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.SchedaPersonalePazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Objects;


public class SchedaPersonalePazienteGraphicController {
    @FXML
    private Label Home, NomePaziente, CognomePaziente, EtàPaziente, PrescriviTerapia, LabelNomePsicologo, NotificaTest;
    @FXML
    private Text DiagnosiPaziente;
    @FXML
    private ImageView ImmaginePaziente, TornaIndietro;

    private String username;
    private final SchedaPersonalePazienteController SchedaPersonaleController = new SchedaPersonalePazienteController();
    private static final Logger logger = LoggerFactory.getLogger(SchedaPersonalePazienteGraphicController.class);
    private final NavigatorSingleton navigator=NavigatorSingleton.getInstance();
    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = SchedaPersonaleController.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        username=SchedaPersonaleController.getUsername();

        NotificaStatoTest();
        PopolaSchedaPersonale();
        AbilitaPrescriviTerapia();
    }

    private void AbilitaPrescriviTerapia() {
        try {
            if (SchedaPersonaleController.NumTestSvoltiSenzaPrescrizione(username) > 0) {
                PrescriviTerapia.setDisable(false);
            }
        }catch (DAOException e) {
            logger.info("Errore nella ricerca dei test svolti dal paziente senza prescrizione" , e);
        }
    }

    private void NotificaStatoTest() {
        try {
            int count = SchedaPersonaleController.cercaNuoviTestSvoltiPazienteDaNotificare(username);
            if (count>0) {
                NotificaTest.setText(String.valueOf(count));
            }
        } catch (DAOException e) {
            logger.info("Errore durante la ricerca dei nuovi test svolti dal paziente " , e);
        }

    }


    private void PopolaSchedaPersonale()  {
        try {
            PazientiBean paziente= SchedaPersonaleController.getSchedaPersonale(username);
            CreaSchedaPersonale(paziente);

        } catch (DAOException e) {
            logger.info("Non esistono informazioni relative al paziente", e);
        }

    }


    private void CreaSchedaPersonale(PazientiBean paziente) {
        Image image;

        NomePaziente.setText(paziente.getNome());
        CognomePaziente.setText(paziente.getCognome());

        EtàPaziente.setText(paziente.getEtà()+ " anni");

        if(paziente.getDiagnosi()==null || paziente.getDiagnosi().isEmpty()) {
            DiagnosiPaziente.setText("Diagnosi Sconosciuta");
        } else {
            DiagnosiPaziente.setText(paziente.getDiagnosi());
        }

        if (paziente.getGenere().equals("M")) {
            image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
            ImmaginePaziente.setImage(image);

        } else {
            image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
            ImmaginePaziente.setImage(image);
        }

    }

    @FXML
    public void goToHome() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    public void TornaIndietro() {
        try {
            Stage SchedaPersonale = (Stage) TornaIndietro.getScene().getWindow();
            SchedaPersonale.close();

            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }


    @FXML
    public void ScegliTest() {
        try {
            Stage SchedaPersonale = (Stage) Home.getScene().getWindow();
            SchedaPersonale.close();

            SchedaPersonaleController.setUsername(username);

            navigator.gotoPage("/com/example/mindharbor/ScegliTest.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
    @FXML
    public void PrescriviTerapia() {
        try {
            Stage SchedaPersonale = (Stage) PrescriviTerapia.getScene().getWindow();
            SchedaPersonale.close();

            SchedaPersonaleController.setUsername(username);
            navigator.gotoPage("/com/example/mindharbor/PrescrizioneTerapia.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

}


