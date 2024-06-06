package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AppuntamentiPazienteGraphicController {
    @FXML
    private Tab AppuntamentiInProgramma;
    @FXML
    private VBox BoxAppuntamentiInProgramma;
    @FXML
    private Label DataAppuntamentoInProgramma;
    @FXML
    private Label OraAppuntamentoInProgramma;
    @FXML
    private Label NomePsicologoInProgramma;
    @FXML
    private Label NomePazienteInProgramma;
    @FXML
    private Label LabelNomePsicologoTab1;
    @FXML
    private Text ListaVuotaInProgramma;
    @FXML
    private Label DataAppuntamentoPassati;
    @FXML
    private Label OraAppuntamentoPassati;
    @FXML
    private Label NomePsicologoPassati;
    @FXML
    private Label NomePazientePassati;
    @FXML
    private Label LabelNomePsicologoTab2;
    @FXML
    private Text ListaVuotaPassati;
    @FXML
    private ListView<Node> ListViewInProgramma;
    @FXML
    private ListView<Node> ListViewPassati;
    @FXML
    private Label HomeTab1;
    @FXML
    private Label HomeTab2;

    private static final Logger logger = LoggerFactory.getLogger(AppuntamentiPsicologoGraphicController.class);

    public void initialize() {

    }


    public void tab1Selezionato(Event event) {
    }

    public void tab2Selezionato(Event event) {
    }

    @FXML
    public void goToHomeFromTab1() { goToHome(HomeTab1);}
    @FXML
    public void goToHomeFromTab2() { goToHome(HomeTab1);}

    @FXML
    public void goToHome(Label label) {
        try {
            Stage Appuntamenti = (Stage) label.getScene().getWindow();
            Appuntamenti.close();


            NavigatorSingleton navigator= NavigatorSingleton.getInstance();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }




}
