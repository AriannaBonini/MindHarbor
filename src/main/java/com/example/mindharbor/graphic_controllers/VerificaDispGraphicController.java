package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.VerificaDispController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.constants.Constants;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.decorator.DispDecorator;
import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImageDecorator;
import com.example.mindharbor.utilities.AlertMessage;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class VerificaDispGraphicController {
    @FXML
    private Label labelData;
    @FXML
    private Label labelOra;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelCognome;
    @FXML
    private Label labelNomePsicologo;
    @FXML
    private Label home;
    @FXML
    private Label accetta;
    @FXML
    private Label rifiuta;
    @FXML
    private Label verificaDisp;

    @FXML
    private ImageView immaginePaziente;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private ImageView immagineDisp;

    @FXML
    private Text disp;

    private final VerificaDispController verificaDispController = new VerificaDispController();
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(VerificaDispGraphicController.class);
    private Integer idRichiesta;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = verificaDispController.getInfoPsicologo();
        labelNomePsicologo.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        idRichiesta= verificaDispController.getRichiesta();

        modificaStatoNotifica();
        schedaAppuntamento();
    }

    private void modificaStatoNotifica() {
        try {
            verificaDispController.modificaStatoNotifica(idRichiesta);
        }catch (DAOException e) {
            logger.info("Errore nella modifica dello stato della notifica della richiesta ", e);
        }
    }

    private void schedaAppuntamento() {
        try {
            AppuntamentiBean richiestaBean = verificaDispController.getRichiestaAppuntamento(idRichiesta);
            popolaScheda(richiestaBean);
        }catch (DAOException e) {
            logger.info("Errore nella ricerca delle informazioni della richiesta ", e);
        }
    }

    private void popolaScheda(AppuntamentiBean richiestaBean) {
        labelNome.setText(richiestaBean.getPaziente().getNome());
        labelCognome.setText(richiestaBean.getPaziente().getCognome());
        labelData.setText(richiestaBean.getData());
        labelOra.setText(richiestaBean.getOra());

        ImageDecorator imageDecorator= new GenereDecorator(immaginePaziente,richiestaBean.getPaziente().getGenere());
        imageDecorator.loadImage();
    }


    @FXML
    public void goToHome() {
        try {
            Stage verificaDispStage = (Stage) home.getScene().getWindow();
            verificaDispStage.close();

            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }
    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage verificaDispStage = (Stage) tornaIndietro.getScene().getWindow();
            verificaDispStage.close();

            navigator.gotoPage("/com/example/mindharbor/ListaRichiesteAppuntamenti.fxml");
        }catch(IOException e) {
            logger.error(Constants.INTERFACE_LOAD_ERROR, e);
        }
    }


    @FXML
    public void verificaDisp() {
        verificaDisp.setVisible(false);
        verificaDisp.setDisable(true);

        accetta.setVisible(true);
        rifiuta.setVisible(true);
        disp.setVisible(true);
        immagineDisp.setVisible(true);

        rifiuta.setDisable(false);

        try {
            if(!verificaDispController.verificaDisp(idRichiesta)) {
                ImageDecorator imageDecorator= new DispDecorator(immagineDisp,false);
                imageDecorator.loadImage();

            } else {
                ImageDecorator imageDecorator= new DispDecorator(immagineDisp,true);
                imageDecorator.loadImage();
                accetta.setDisable(false);
            }

        } catch (DAOException e) {
            logger.info("Errore nella ricerca delle disponibilità dello psicologo ", e);
        }
    }


    @FXML
    public void richiestaAccettata() {
        try {
            verificaDispController.richiestaAccettata();
            Alert alert=new AlertMessage().Informazione("SUCCESSO", "Richiesta accettata","Hai un nuovo appuntamento");

            new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            alert.showAndWait();

            tornaIndietro();
        } catch (DAOException e) {
            logger.info(Constants.DELETE_REQUEST, e);
        }
    }

    @FXML
    public void richiestaRifiutata() {
        try {
            verificaDispController.richiestaRifiutata();
            Alert alert=new AlertMessage().Informazione("SUCCESSO", "Richiesta rifiutata","appuntamento rifiutato");

            new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            alert.showAndWait();
            tornaIndietro();
        } catch (DAOException e) {
            logger.info(Constants.DELETE_REQUEST, e);
        }
    }
}
