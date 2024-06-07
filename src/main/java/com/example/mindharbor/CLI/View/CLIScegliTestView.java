package com.example.mindharbor.CLI.View;

import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;

import java.util.List;
import java.util.Scanner;

public class CLIScegliTestView {
    private Scanner scanner;
    private OutputHandler outputHandler;

    private InputHandler inputHandler = new InputHandler();

    public CLIScegliTestView() {

    }

    public void displayTests(List<String> testNames) {
        outputHandler.print("\nElenco dei Test Psicologici disponibili:");
        for (int i = 0; i < testNames.size(); i++) {
            outputHandler.print(String.format("%d. %s", i + 1, testNames.get(i)));
        }
        outputHandler.print("0. Torna alla homepage");
        outputHandler.print("Seleziona il numero del test da assegnare:");
    }

    public int getUserInput(List<String> testNames) {
        return inputHandler.getIntInputHome(0, testNames.size());
    }

    public void displayErrorMessage(String message) {
        outputHandler.print("Errore: " + message);
    }

    public void displaySuccess(String nomeTest) {
        outputHandler.print("Test \"" + nomeTest + "\" assegnato con successo!");
    }
}
