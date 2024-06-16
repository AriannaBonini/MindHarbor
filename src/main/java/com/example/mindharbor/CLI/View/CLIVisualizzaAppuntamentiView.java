package com.example.mindharbor.CLI.View;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;
import wiremock.org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Scanner;

public class CLIVisualizzaAppuntamentiView {
    private Scanner scanner = new Scanner(System.in);
    private InputHandler inputHandler = new InputHandler();

    public void displayAppointmentsBean(List<AppuntamentiBean> appuntamenti) {
        // Questa è la versione che utilizza come parametro una lista di beans appointment

        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Appuntamenti trovati:" + ANSI_CODE.ANSI_RESET);
        OutputHandler.printAppointmentsBean(appuntamenti);
    }
    public void displayNoAppointments() {
        OutputHandler.print("Non ci sono appuntamenti");
    }

    public void displayErrorMessage(String message) {
        OutputHandler.print(message);
    }

    public Integer getUserInput() {
        return inputHandler.getIntInputWithZeroOption();
    }

    public void cleanPage() {
        OutputHandler.clean_page();
    }

}
