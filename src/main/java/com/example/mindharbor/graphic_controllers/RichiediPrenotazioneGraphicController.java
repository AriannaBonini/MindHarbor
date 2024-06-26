package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.RichiediPrenotazioneController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RichiediPrenotazioneGraphicController {

    @FXML
    private ImageView ImmaginePsicologo;
    @FXML
    private Label NomePsicologo, CognomePsicologo, CostoOrario, LabelNomePaziente;

    NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ListaPsicologiGraphicController.class);
    private RichiediPrenotazioneController prenotazioneController= new RichiediPrenotazioneController();
    private String nome, cognome;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = prenotazioneController.getPageRichPrenInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePaziente.setText(nome + " " + cognome);

        PopolaSchedaPsicologo();
    }

    private void PopolaSchedaPsicologo() {}






    @FXML
    private void goToHome() {
    }

    @FXML
    private void TornaIndietro() {
    }

    @FXML
    private void RichiediPrenotazione() {
    }
}
