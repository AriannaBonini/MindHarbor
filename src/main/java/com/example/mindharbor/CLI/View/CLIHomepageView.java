package com.example.mindharbor.CLI.View;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;
import java.util.Scanner;

public class CLIHomepageView {
    private Scanner scanner = new Scanner(System.in);
    private InputHandler inputHandler = new InputHandler();
    public void displayWelcomeMessage(HomeInfoUtenteBean userInfo) {
        OutputHandler.clean_page();
        String welcome = "Benvenuto ";
        // Calcola la lunghezza della stringa di benvenuto basata sul nome e cognome forniti dalla bean
        OutputHandler.separetor(welcome.length() + userInfo.getNome().length() + userInfo.getCognome().length() + 1);
        // Visualizza il messaggio di benvenuto con nome e cognome in grassetto
        OutputHandler.print(welcome + ANSI_CODE.ANSI_BOLD + userInfo.getNome() + " " + userInfo.getCognome() + ANSI_CODE.ANSI_RESET_BOLD);
        OutputHandler.separetor(welcome.length() + userInfo.getNome().length() + userInfo.getCognome().length() + 1);
        OutputHandler.print("");
    }

    public void displayOptions(String[] options) {
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Seleziona un'opzione:" + ANSI_CODE.ANSI_RESET_BOLD);
        for (int i = 0; i < options.length; i++) {
            OutputHandler.print((i + 1) + " - " + options[i]);
        }
    }

    public int getUserInput(Utente utente) {
        switch (utente.getUserType()){
            case PSICOLOGO:
                return inputHandler.getIntInputHome(1,3);
            case PAZIENTE:
                return inputHandler.getIntInputHome(1,3);
            default:
                return -1;
        }
    }

    public void displayInvalidOptionMessage() {
        OutputHandler.clean_page();
        OutputHandler.print(ANSI_CODE.ANSI_RED + "Scelta non valida, riprova." + ANSI_CODE.ANSI_RESET);
    }

    public void displayLogoutMessage() {
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Arrivederci!" + ANSI_CODE.ANSI_RESET_BOLD);
        OutputHandler.clean_page();
    }

    public void displayErrorMessage(String str){
        OutputHandler.print(str);
    }
    public void closeScanner() {
        scanner.close();
    }
}
