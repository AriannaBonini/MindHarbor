package com.example.mindharbor.CLI.View;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.utilities.OutputHandler;
import java.util.List;
import java.util.Scanner;

public class CLIPrescriviTerapiaView {
    private Scanner scanner = new Scanner(System.in);

    public void displayAppointments(List<Appuntamento> appuntamenti) {
        // Questa è la versione senza bean, che usa come parametro una lista di Appuntamento
        OutputHandler.print("Appuntamenti trovati:");
        for (Appuntamento app : appuntamenti) {
            OutputHandler.printSpecificAppointment(app);
        }
        OutputHandler.print("");
        OutputHandler.print("Seleziona un appuntamento oppure digita '0' per uscire: ");
    }

    public void displayAppointmentsBean(List<AppuntamentiBean> appuntamenti) {
        // Questa è la versione con bean, che usa come parametro una lista di bean di Appuntamento

        int count = 1;
        OutputHandler.print("Appuntamenti trovati:");
        for (AppuntamentiBean app : appuntamenti) {
            OutputHandler.printSpecificAppointmentBean(app, count);
            count++;
        }
        OutputHandler.print("");
        OutputHandler.print("Seleziona un appuntamento oppure digita '0' per uscire: ");
    }

    public void displaySpecificAppointmentBean(AppuntamentiBean appuntamentiBean, int x) {
        OutputHandler.printSpecificAppointmentBean(appuntamentiBean, x);
    }

    public int getUserInput() {
        return scanner.nextInt();
    }

    public void displayErrorMessage(String message) {
        OutputHandler.print(message);
    }
}
