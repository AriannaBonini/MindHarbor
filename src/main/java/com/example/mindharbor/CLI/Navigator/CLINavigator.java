package com.example.mindharbor.CLI.Navigator;

import com.example.mindharbor.CLI.Controller.*;
import com.example.mindharbor.session.SessionManager;

import java.sql.SQLException;

public class CLINavigator {
    private static  CLINavigator instance;
    private SessionManager sessionManager;
    private CLILoginController cliLoginController;
    private  CLIHomepageController cliHomepageController;
    private CLIVisualizzaAppuntamentiPsicologoController cliVisualizzaAppuntamentiPsicologoController;
    private CLIPrescriviTerapiaController cliPrescriviTerapiaController;
    private CLISchedaPersonaleController cliSchedaPersonaleController;

    public CLINavigator(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void showLogin() {
        CLILoginController cliLoginController = new CLILoginController(sessionManager, this);
        cliLoginController.esegui();
    }

    public void showHomepage() throws SQLException {
        CLIHomepageController cliHomepageController = new CLIHomepageController(sessionManager, this);
        cliHomepageController.mostraHomepage();
    }

    public void showVisualizzaAppuntamentiPsicologo() {
        CLIVisualizzaAppuntamentiPsicologoController visualizzaAppuntamentiPsicologoController = new CLIVisualizzaAppuntamentiPsicologoController(sessionManager, this);
        visualizzaAppuntamentiPsicologoController.visualizza();

    }
    public void showPrescriviTerapia() {
        CLIPrescriviTerapiaController prescriviTerapiaController = new CLIPrescriviTerapiaController(sessionManager, this);
        prescriviTerapiaController.start();
    }
    public void showSchedaPersonale(String username) {
        CLISchedaPersonaleController cliSchedaPersonaleController = new CLISchedaPersonaleController(sessionManager, this);
        cliSchedaPersonaleController.visualizzaSchedaPersonale(username);
    }
    public void selezionaTest(String usernamePaziente){
        CLIScegliTestController cliScegliTestController = new CLIScegliTestController(sessionManager, this, usernamePaziente);
        cliScegliTestController.selezionaTest();
    }
}
