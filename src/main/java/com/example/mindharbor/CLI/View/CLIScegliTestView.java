package com.example.mindharbor.CLI.View;

import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;

import java.util.List;
import java.util.Scanner;

public class CLIScegliTestView {
    private Scanner scanner;

    private InputHandler inputHandler = new InputHandler();

    public CLIScegliTestView() {

    }

    public void displayTests(List<String> testNames) {
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "\nElenco dei Test Psicologici disponibili:" + ANSI_CODE.ANSI_RESET_BOLD);
        for (int i = 0; i < testNames.size(); i++) {
            OutputHandler.print(String.format("%d. %s", i + 1, testNames.get(i)));
        }
        OutputHandler.print("");
        OutputHandler.print("0. Torna alla homepage");
    }

    public int getUserInput(List<String> testNames) {
        return inputHandler.getIntInputHome(0, testNames.size());
    }

    public void displayErrorMessage(String message) {
        OutputHandler.print("Errore: " + message);
    }

    public void displaySuccess(String nomeTest) {
        OutputHandler.print("Test \"" + nomeTest + "\" assegnato con successo!");
    }
}
