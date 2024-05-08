package com.example.mindharbor.CLI.View;

import com.example.mindharbor.utilities.OutputHandler;
import com.example.mindharbor.utilities.ANSI_CODE;
import java.util.Scanner;

public class CLILoginView {
    private Scanner scanner;

    public CLILoginView() {
        this.scanner = new Scanner(System.in);
    }

    public void printWelcomeMessage() {
        OutputHandler.print_asciiart();
        OutputHandler.print("");
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Benvenuto in MindHarbor" + ANSI_CODE.ANSI_RESET_BOLD);
    }

    public String getUsername() {
        OutputHandler.printf("Inserisci il tuo username: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        OutputHandler.printf("Inserisci la tua password: ");
        return scanner.nextLine();
    }

    public void printLoginError(int x, String str) {
        /*
        if x = 0 =>  MESSAGGIO PREDEFINITO
        else if = 1 => PRINT STR
         */
        if (x == 0){
            OutputHandler.clean_page();
            OutputHandler.print(ANSI_CODE.ANSI_RED + "CREDENZIALI ERRATE" + ANSI_CODE.ANSI_RESET);
        } else if (x == 1){
            OutputHandler.clean_page();
            OutputHandler.print(ANSI_CODE.ANSI_RED + str + ANSI_CODE.ANSI_RESET);
        }
    }
    public void clean(){
        OutputHandler.clean_page();
    }
    public String getInput(){
        return scanner.nextLine();
    }

    public void printDatabaseError() {
        OutputHandler.print("Problema di connessione al database.");
    }
}
