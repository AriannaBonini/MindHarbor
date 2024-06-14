package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.HomePsicologoController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class HomePsicologoGraphicController {

    @FXML
    private Label logout, PrescriviTerapia, RichiestaPrenotazione, VisualizzaAppuntamenti, LabelNomePsicologo, NotificaTest;

    private static final Logger logger = LoggerFactory.getLogger(HomePsicologoGraphicController.class);
    HomePsicologoController homeController= new HomePsicologoController();

    private String nome, cognome;

    public void initialize() {

        HomeInfoUtenteBean infoUtenteBean = homeController.getHomepageInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        notificaTestEffettuati();
    }

    private void notificaTestEffettuati() {
        //Ho aggiunto una colonna alla tabella testpsicologico che si chiama StatoPsicologo.
        //Di default sarà posta a zero, ossia "lo psicologo non deve visualizzare la notifica".
        //Non appena il paziente svolge il test, la colonna viene settata ad 1, ossia "lo psicologo deve vedere la notifica del test svolto dal paziente".
        //Non appena lo psicologo vede la notifica, questa sarà settata nuovamente a zero.
        //Quello stato non potrà più essere modificato poiché il paziente non può più svolgere il test.

        try {
            int count = homeController.cercaNuoviTestSvolti();
            if (count > 0) {
                NotificaTest.setText(String.valueOf(count));
            }
        } catch (SQLException e) {
            logger.info("Errore nella ricerca dei test ", e);
        }

    }

    @FXML
    private void onVisualAppuntamentiClick() {

        try {
            Stage HomePsicologo = (Stage) VisualizzaAppuntamenti.getScene().getWindow();
            HomePsicologo.close();

            NavigatorSingleton navigator = NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/ListaAppuntamenti2.fxml");



        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }

    @FXML
    private void onPrescriviTerapiaClick() {

        NavigatorSingleton navigator = NavigatorSingleton.getInstance();

        try {
            Stage HomePsicologo = (Stage) PrescriviTerapia.getScene().getWindow();
            HomePsicologo.close();

            navigator.gotoPage("/com/example/mindharbor/ListaPazienti.fxml");


        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    private void Logout() {
        NavigatorSingleton navigator = NavigatorSingleton.getInstance();
        try {

            Stage HomePsicologo= (Stage) logout.getScene().getWindow();
            HomePsicologo.close();

            homeController.logout();

            navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        } catch (IOException e ) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
