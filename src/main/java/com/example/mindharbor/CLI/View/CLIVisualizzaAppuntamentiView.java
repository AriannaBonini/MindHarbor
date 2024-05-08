package com.example.mindharbor.CLI.View;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.utilities.OutputHandler;

import java.util.List;
import java.util.Scanner;

public class CLIVisualizzaAppuntamentiView {
    private Scanner scanner = new Scanner(System.in);

    public void displayAppointments(List<Appuntamento> appuntamenti) {
        // Questa è la versione che utilizza come parametro una lista di model appointment

        OutputHandler.print("Appuntamenti trovati:");
        OutputHandler.printAppointments(appuntamenti);
        OutputHandler.print("'exit' per tornare alla homepage");
    }
    public void displayAppointmentsBean(List<AppuntamentiBean> appuntamenti) {
        // Questa è la versione che utilizza come parametro una lista di beans appointment

        OutputHandler.print("Appuntamenti trovati:");
        OutputHandler.printAppointmentsBean(appuntamenti);
        OutputHandler.print("'exit' per tornare alla homepage");
    }
    public void displayNoAppointments() {
        OutputHandler.print("Non ci sono appuntamenti");
    }

    public void displayErrorMessage(String message) {
        OutputHandler.print(message);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void cleanPage() {
        OutputHandler.clean_page();
    }

}
