package com.example.mindharbor.CLI.Navigator;

import com.example.mindharbor.CLI.Controller.CLIHomepageController;
import com.example.mindharbor.CLI.Controller.CLILoginController;
import com.example.mindharbor.CLI.Controller.CLIPrescriviTerapiaController;
import com.example.mindharbor.CLI.Controller.CLIVisualizzaAppuntamentiPsicologoController;
import com.example.mindharbor.session.SessionManager;

public class CLINavigator {
    private static  CLINavigator instance;
    private SessionManager sessionManager;
    private CLILoginController cliLoginController;
    private  CLIHomepageController cliHomepageController;
    private CLIVisualizzaAppuntamentiPsicologoController cliVisualizzaAppuntamentiPsicologoController;
    private CLIPrescriviTerapiaController cliPrescriviTerapiaController;

    public CLINavigator(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
    }

    public void showLogin() {
        CLILoginController cliLoginController = new CLILoginController(sessionManager, this);
        cliLoginController.esegui();
    }

    public void showHomepage() {
        CLIHomepageController cliHomepageController = new CLIHomepageController(sessionManager, this);
        cliHomepageController.mostraHomepage();
    }

    public void showVisualizzaAppuntamentiPsicologo(){
        CLIVisualizzaAppuntamentiPsicologoController visualizzaAppuntamentiPsicologoController = new CLIVisualizzaAppuntamentiPsicologoController(sessionManager, this);
        visualizzaAppuntamentiPsicologoController.visualizza();

    }
    public void showPrescriviTerapia() {
        CLIPrescriviTerapiaController prescriviTerapiaController = new CLIPrescriviTerapiaController(sessionManager, this);
        prescriviTerapiaController.start();
    }
}
