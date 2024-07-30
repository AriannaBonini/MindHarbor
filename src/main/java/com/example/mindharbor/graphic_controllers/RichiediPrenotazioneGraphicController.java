package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.RichiediPrenotazioneController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.exceptions.DAOException;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class RichiediPrenotazioneGraphicController {

    @FXML
    private ImageView immaginePsicologo;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private Label nomePsicologo;
    @FXML
    private Label cognomePsicologo;
    @FXML
    private Label costoOrario;
    @FXML
    private Label nomeStudio;
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label home;

    NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(RichiediPrenotazioneGraphicController.class);
    private final RichiediPrenotazioneController prenotazioneController= new RichiediPrenotazioneController();
    private PsicologoBean psicologoSelezionato;

    public void initialize() {
        InfoUtenteBean infoUtenteBean = prenotazioneController.getPageRichPrenInfo();

        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        psicologoSelezionato=prenotazioneController.getPsicologoSelezionato();

        popolaSchedaPsicologo();
    }

    private void popolaSchedaPsicologo() {
        try {
            psicologoSelezionato = prenotazioneController.getInfoPsicologo(psicologoSelezionato);
            creaSchedaPsicologo();

        }catch (DAOException e) {
            logger.info("Non esistono ulteriori informazioni relative allo psicologo " ,e);
        }

    }

    private void creaSchedaPsicologo() {
        nomePsicologo.setText(psicologoSelezionato.getNome());
        cognomePsicologo.setText(psicologoSelezionato.getCognome());
        costoOrario.setText(psicologoSelezionato.getCostoOrario() + " €/h");
        nomeStudio.setText(psicologoSelezionato.getNomeStudio());

        ImageDecorator imageDecorator= new GenereDecorator(immaginePsicologo,psicologoSelezionato.getGenere());
        imageDecorator.caricaImmagine();

    }

    @FXML
    public void clickLabelHome() {
        try {
            Integer risposta= new AlertMessage().Avvertenza("Sei sicuro di voler tornare indietro?");
            if(risposta!=0) {

                prenotazioneController.deleteAppuntamentoSelezionato();

                Stage richiediPrenotazione = (Stage) home.getScene().getWindow();
                richiediPrenotazione.close();

                navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");
            }

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia Home del paziente", e);
        }
    }

    @FXML
    public void clickLabelTornaIndietro() {
        try {
            Stage richiediPrenotazione = (Stage) tornaIndietro.getScene().getWindow();
            richiediPrenotazione.close();

            prenotazioneController.eliminaPsicologoSelezionato();

            navigator.gotoPage("/com/example/mindharbor/ListaPsicologi.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia della lista degli psicolog", e);
        }
    }

    @FXML
    public void richiediPrenotazione() {
        try {
            AppuntamentiBean appuntamentoBean = prenotazioneController.getAppuntamentoSelezionato();
            appuntamentoBean.setPsicologo(psicologoSelezionato);

            prenotazioneController.salvaRichiestaAppuntamento(appuntamentoBean);

            Alert alert=new AlertMessage().Informazione("OPERAZIONE COMPLETATA","SUCCESSO", "Richiesta inviata");

            new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.close()));
            alert.showAndWait();

            Stage richiediPrenotazione = (Stage) home.getScene().getWindow();
            richiediPrenotazione.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch (DAOException e) {
            logger.info("Errore nel salvataggio della richiesta di appuntamento", e);

        }catch (IOException e) {
            logger.info("Errore nel caricamento dell'interfaccia");
        }
    }
}
