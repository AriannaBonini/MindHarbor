package com.example.mindharbor.utilities.cli_helper;

import java.util.Scanner;

public class GestoreInput {
    private final Scanner scanner;
    public GestoreInput(Scanner scanner){
        this.scanner=scanner;
    }
    public Integer ottieniInput(int min, int max, String messaggio){
        while (true) {
            GestoreOutput.stampaMessaggio(messaggio + " (min " + min + ", max " + max + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    GestoreOutput.stampaMessaggio("Input non valido, riprova: ");
                }
            } else {
                GestoreOutput.stampaMessaggio("Input non valido, riprova: ");
                scanner.next();
            }
        }
    }

    public Integer ottieniInputZeroOption(String messaggio){
        while (true) {

            GestoreOutput.stampaMessaggio(messaggio);
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 0) {
                    return choice;
                } else {
                    GestoreOutput.stampaMessaggio(messaggio);
                }
            } else {
                GestoreOutput.stampaMessaggio("Errore: inserisci un numero valido.");
                scanner.next(); // Consuma l'input non valido
            }
        }
    }


}
