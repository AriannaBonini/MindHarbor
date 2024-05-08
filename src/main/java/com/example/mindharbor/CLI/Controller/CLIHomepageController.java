package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIHomepageView;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;
import com.example.mindharbor.utilities.setInfoUtente;

public class CLIHomepageController {
    private SessionManager sessionManager;
    private CLIHomepageView view;
    private InputHandler inputHandler;
    private CLIVisualizzaAppuntamentiPsicologoController cliVisualizzaAppuntamentiPsicologoController;
    private CLINavigator cliNavigator;

    public CLIHomepageController(SessionManager sessionManager, CLINavigator cliNavigator) {
        this.sessionManager = sessionManager;
        this.view = new CLIHomepageView();
        this.inputHandler = new InputHandler();
        this.cliVisualizzaAppuntamentiPsicologoController = new CLIVisualizzaAppuntamentiPsicologoController(sessionManager, cliNavigator);
        this.cliNavigator = cliNavigator;
    }

    public void mostraHomepage() {
        boolean isRunning = true;
        Utente utente = sessionManager.getCurrentUser();

        if (utente == null) {
            view.displayErrorMessage("Errore di sessione: Nessun utente loggato.");
            return;
        }
        while (isRunning) {
            switch (utente.getUserType()) {
                case PSICOLOGO:
                    isRunning = mostraHomepagePsicologo(utente);
                    break;
                case PAZIENTE:
                    isRunning = mostraHomepagePaziente(utente);
                    break;
                default:
                    view.displayErrorMessage("Tipo di utente non supportato.");
                    return;
            }
        }
    }

    private boolean mostraHomepagePsicologo(Utente utente) {
        HomeInfoUtenteBean userInfo = new setInfoUtente().getInfo();
        // Qui inserisci la logica specifica per mostrare la homepage per psicologi
        view.displayWelcomeMessage(userInfo);
        view.displayOptions(new String[]{"Visualizza appuntamenti", "Prescrivi terapia", "Logout"});
        return handleUserInputPsicologo(utente);
    }

    private boolean mostraHomepagePaziente(Utente utente) {
        HomeInfoUtenteBean userInfo = new setInfoUtente().getInfo();
        // Qui inserisci la logica specifica per mostrare la homepage per pazienti
        view.displayWelcomeMessage(userInfo);
        view.displayOptions(new String[]{"Prenota appuntamento", "Visualizza la mia terapia", "Logout"});
        return handleUserInputPaziente(utente);
    }

    private boolean handleUserInputPsicologo(Utente utente) {
        int choice = view.getUserInput(utente);
        switch (choice) {
            case 1:
                cliNavigator.showVisualizzaAppuntamentiPsicologo();
                break;
            case 2:
                cliNavigator.showPrescriviTerapia();
                break;
            case 3:
                sessionManager.logout();
                view.displayLogoutMessage();
                cliNavigator.showLogin();
                return false;
            default:
                view.displayInvalidOptionMessage();
                break;
        }
        return true;
    }

    private boolean handleUserInputPaziente(Utente utente) {
        int choice = view.getUserInput(utente);
        switch (choice) {
            case 1:
                // ci si sposterà tramite il CLINavigator

                /*
                Qui ci sarà questa porzione di codice una volta implementato
                sia la view che il relativo controller

                cliNavigator.showPrenotaAppuntamento();
                 */

                break;
            case 2:
                // ci si sposterà tramite il CLINavigator

                 /*
                Qui ci sarà questa porzione di codice una volta implementato
                sia la view che il relativo controller

                cliNavigator.showVisualizzaMiaTerapia();
                 */

                break;
            case 3:
                sessionManager.logout();
                view.displayLogoutMessage();
                cliNavigator.showLogin();
                return false;
            default:
                view.displayInvalidOptionMessage();
                break;
        }
        return true;
    }
}

