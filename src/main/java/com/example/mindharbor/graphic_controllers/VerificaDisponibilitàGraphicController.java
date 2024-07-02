package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.VerificaDisponibilitàController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class VerificaDisponibilitàGraphicController {
    @FXML
    private Label LabelData, LabelOra, LabelNome, LabelCognome, LabelNomePsicologo, Home, Accetta, Rifiuta, VerificaDisp;
    @FXML
    private ImageView ImmaginePaziente, TornaIndietro, ImmagineDisponibilità;
    @FXML
    private Text Disponibilità;

    private final VerificaDisponibilitàController verificaDisponibilitàController = new VerificaDisponibilitàController();
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(VerificaDisponibilitàGraphicController.class);
    private Integer idRichiesta;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = verificaDisponibilitàController.getInfoPsicologo();
        LabelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        idRichiesta= verificaDisponibilitàController.getRichiesta();

        ModificaStatoNotifica();
        SchedaAppuntamento();
    }

    private void ModificaStatoNotifica() {
        try {
            verificaDisponibilitàController.modificaStatoNotifica(idRichiesta);
        }catch (DAOException e) {
            logger.info("Errore nella modifica dello stato della notifica della richiesta ", e);
        }
    }

    private void SchedaAppuntamento() {
        try {
            AppuntamentiBean richiestaBean = verificaDisponibilitàController.getRichiestaAppuntamento(idRichiesta);
            PopolaScheda(richiestaBean);
        }catch (DAOException e) {
            logger.info("Errore nella ricerca delle informazioni della richiesta ", e);
        }
    }

    private void PopolaScheda(AppuntamentiBean richiestaBean) {
        LabelNome.setText(richiestaBean.getNomePaziente());
        LabelCognome.setText(richiestaBean.getCognomePaziente());
        LabelData.setText(richiestaBean.getData());
        LabelOra.setText(richiestaBean.getOra());
        Image image;

        if((richiestaBean.getGenere()).equals("F")) {
            image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
            ImmaginePaziente.setImage(image);
        }else {
            image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
            ImmaginePaziente.setImage(image);
        }
    }


    @FXML
    public void goToHome() {
        try {
            Stage verificaDisponibilità = (Stage) Home.getScene().getWindow();
            verificaDisponibilità.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void TornaIndietro() {
        try {
            Stage verificaDisponibilità = (Stage) TornaIndietro.getScene().getWindow();
            verificaDisponibilità.close();

            navigator.gotoPage("/com/example/mindharbor/ListaRichiesteAppuntamenti.fxml");
        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }


    @FXML
    public void VerificaDisponibilità() {
        Image image;
        try {
            if(!verificaDisponibilitàController.verificaDisponibilità(idRichiesta)) {
                VerificaDisp.setVisible(false);
                VerificaDisp.setDisable(true);

                Accetta.setVisible(true);
                Rifiuta.setVisible(true);
                Disponibilità.setVisible(true);
                Rifiuta.setDisable(false);

                ImmagineDisponibilità.setVisible(true);
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaRossa.png")));
                ImmagineDisponibilità.setImage(image);
            } else {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaVerde.png")));
                ImmagineDisponibilità.setImage(image);
                ImmagineDisponibilità.setVisible(true);

                VerificaDisp.setVisible(false);
                VerificaDisp.setDisable(true);

                Accetta.setVisible(true);
                Rifiuta.setVisible(true);
                Disponibilità.setVisible(true);


                Accetta.setDisable(false);
                Rifiuta.setDisable(false);
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca delle disponibilità dello psicologo ", e);
        }
    }


    @FXML
    public void RichiestaAccettata() {
        try {
            verificaDisponibilitàController.richiestaAccettata(idRichiesta);
            Alert alert=new AlertMessage().Informazione("SUCCESSO", "Richiesta accettata","Hai un nuovo appuntamento");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            alert.showAndWait();

            TornaIndietro();
        } catch (DAOException e) {
            logger.info("Errore nell'aggiornamento della richiesta di appuntamento ", e);
        }
    }

    @FXML
    public void RichiestaRifiutata() {
        try {
            verificaDisponibilitàController.richiestaRifiutata(idRichiesta);
            Alert alert=new AlertMessage().Informazione("SUCCESSO", "Richiesta rifiutata","appuntamento rifiutato");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            alert.showAndWait();
            TornaIndietro();
        } catch (DAOException e) {
            logger.info("Errore nell'eliminazione della richiesta di appuntamento ", e);
        }
    }
}
