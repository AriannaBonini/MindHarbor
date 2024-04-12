package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaPazientiController;
import com.example.mindharbor.app_controllers.SchedaPersonalePazienteController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SchedaPersonalePazienteGraphicController {
    @FXML
    private Label Home;
    @FXML
    private Label NomePaziente;
    @FXML
    private Label CognomePaziente;
    @FXML
    private Label EtàPaziente;
    @FXML
    private Text DiagnosiPaziente;
    @FXML
    private Label ScegliTest;
    @FXML
    private Label PrescriviTerapia;
    @FXML
    private ImageView ImmaginePaziente;
    @FXML
    private Label LabelNomePsicologo;

    private String nome;
    private String cognome;

    public void initialize() {
        SchedaPersonalePazienteController SchedaPersonale = new SchedaPersonalePazienteController();

        HomeInfoUtenteBean infoUtenteBean = SchedaPersonale.getPagePsiInfo();

        nome = infoUtenteBean.getNome();
        cognome = infoUtenteBean.getCognome();

        LabelNomePsicologo.setText(nome + " " + cognome);

        PopolaSchedaPersonale();
    }

    public void PopolaSchedaPersonale() {
        

    }





    public void goToHome() {

    }
}
