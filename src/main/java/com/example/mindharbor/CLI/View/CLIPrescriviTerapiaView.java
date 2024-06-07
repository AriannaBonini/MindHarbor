package com.example.mindharbor.CLI.View;

import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CLIPrescriviTerapiaView {
    private Scanner scanner = new Scanner(System.in);

    public void displayPatientsBean(List<PazientiBean> pazientiBeans) {
        // Questa è la versione con bean, che usa come parametro una lista di bean di Appuntamento

        int count = 1;
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Lista Pazienti:" + ANSI_CODE.ANSI_RESET_BOLD);
        for (PazientiBean app : pazientiBeans) {
            OutputHandler.printSpecificPatientBean(app, count);
            count++;
        }
        OutputHandler.print("");
        OutputHandler.print("(Seleziona un paziente oppure digita '0' per uscire)");
    }

    public void displaySpecificPatientBean(PazientiBean pazientiBean, int x) {
        OutputHandler.printSpecificPatientBean(pazientiBean, x);
    }

    public int getUserInput(InputHandler inputHandler, int x, int y) {
        return inputHandler.getIntInput(x,y);
    }

    public void displayErrorMessagePatient() {
        OutputHandler.print("Non ci sono appuntamenti");
    }
    public void displayErrorMessageForm(SQLException e) {
        OutputHandler.print("Errore durante la ricerca degli appuntamenti" + e.getMessage());
    }

    public void clean(){
        OutputHandler.clean_page();
    }
}
