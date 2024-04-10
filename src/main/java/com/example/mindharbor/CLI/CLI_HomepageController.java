package com.example.mindharbor.CLI;

import com.example.mindharbor.model.Utente;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.OutputHandler;
import java.util.Scanner;

public class CLI_HomepageController {

    public void mostraHomepage(Utente utente) {
        switch (utente.getUserType()) {
            case PSICOLOGO:
                mostraHomepagePsicologo(utente);
                break;
            case PAZIENTE:
                mostraHomepagePaziente(utente);
                break;
            default:
                OutputHandler.print("Tipo di utente non riconosciuto.");
                break;
        }
    }

    private void mostraHomepagePsicologo(Utente utente) {
        String Benv = "Benvenuto ";
        Scanner scanner = new Scanner(System.in);


        while(true) {
            OutputHandler.separetor(Benv.length() + utente.getNome().length() + utente.getCognome().length()+1);
            OutputHandler.print(Benv + ANSI_CODE.ANSI_BOLD + utente.getNome() + " " + utente.getCognome() + ANSI_CODE.ANSI_RESET_BOLD);
            OutputHandler.separetor(Benv.length() + utente.getNome().length() + utente.getCognome().length()+1);
            OutputHandler.print("");
            OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Seleziona un'opzione:" + ANSI_CODE.ANSI_RESET_BOLD);
            OutputHandler.print("1 - Visualizza appuntamenti");
            OutputHandler.print("2 - Prescrivi terapia");
            OutputHandler.print("3 - Logout");
            String scelta = scanner.nextLine();


            switch (scelta) {
                case "1":
                    // Logica per visualizzare gli appuntamenti
                    // qui ci sarà il lancio di visualizzaAppuntamenti (magari con un metodo esegui) dopo aver fatto la new
                    OutputHandler.clean_page();
                    CLI_VisualizzaAppuntamentiPsicologoController cli_visualizzaAppuntamentiPsicologoController = new CLI_VisualizzaAppuntamentiPsicologoController();
                    cli_visualizzaAppuntamentiPsicologoController.visualizza(utente.getUsername());
                    break;
                case "2":
                    OutputHandler.print("Prescrivi Terapia");
                    // Logica per prescrivi terapia
                    // stessa cosa di sopra
                    return;
                case "3":
                    // Qui si deve tornare al login
                    OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Arrivederci!" + ANSI_CODE.ANSI_RESET_BOLD);
                    OutputHandler.clean_page();
                    // ora si deve chiudere la sessione dell'utente corrente e poi fare return cosi che si ritorna
                    // a chi a chiamato questa funzione (precisamente ritorneremo all'istruzione successiva)
                    return;
                default:
                    OutputHandler.clean_page();
                    OutputHandler.print(ANSI_CODE.ANSI_RED + "Scelta non valida, riprova." + ANSI_CODE.ANSI_RESET);
            }
        }
    }

    private void mostraHomepagePaziente(Utente utente) {
        String Benv = "Benvenuto ";
        Scanner scanner = new Scanner(System.in);
        while(true){
            OutputHandler.separetor(Benv.length() + utente.getNome().length() + utente.getCognome().length()+1);
            OutputHandler.print(Benv + ANSI_CODE.ANSI_BOLD + utente.getNome() + " " + utente.getCognome() + ANSI_CODE.ANSI_RESET_BOLD);
            OutputHandler.separetor(Benv.length() + utente.getNome().length() + utente.getCognome().length()+1);
            OutputHandler.print("");
            OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Seleziona un'opzione:" + ANSI_CODE.ANSI_RESET_BOLD);
            OutputHandler.print("1 - Opzione 1");
            OutputHandler.print("2 - Opzione 2");
            OutputHandler.print("3 - Opzione 3");
            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1":
                    // Logica per Opzione 1
                    break;
                case "2":
                    // Logica per Opzione 2
                    break;
                case "3":
                    // Logica per Opzione 3
                    break;
                default:
                    OutputHandler.clean_page();
                    OutputHandler.print(ANSI_CODE.ANSI_RED + "Scelta non valida, riprova." + ANSI_CODE.ANSI_RESET);
            }
        }
    }
}
