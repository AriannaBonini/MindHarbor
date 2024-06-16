package com.example.mindharbor.utilities;

import java.util.Scanner;

public class InputHandler {

    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getIntInputHome(int min, int max) {
        while (true) {
            OutputHandler.print("Inserisci un'opzione (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    OutputHandler.print("Errore: inserisci un numero tra " + min + " e " + max + ".");
                }
            } else {
                OutputHandler.print("Errore: inserisci un numero valido.");
                scanner.next(); // Consuma l'input non desiderato
            }
        }
    }

    public int getIntInput(int min, int max) {
        while (true) {
            OutputHandler.print("Seleziona un'opzione (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    OutputHandler.print("Errore: inserisci un numero tra " + min + " e " + max + ".");
                }
            } else {
                OutputHandler.print("Errore: inserisci un numero valido.");
                scanner.next(); // Consuma l'input non valido
            }
        }
    }

    public int getIntInputWithZeroOption() {
        while (true) {
            OutputHandler.print("Inserisci '0' per tornare alla homepage: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 0) {
                    return choice;
                } else {
                    OutputHandler.print("Errore: inserisci '0' per tornare alla homepage.");
                }
            } else {
                OutputHandler.print("Errore: inserisci un numero valido.");
                scanner.next(); // Consuma l'input non valido
            }
        }
    }

    // Implementa altri metodi di input se necessario
}

