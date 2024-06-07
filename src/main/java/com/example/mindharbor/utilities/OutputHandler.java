package com.example.mindharbor.utilities;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.model.Appuntamento;
import java.util.List;

public class OutputHandler {
    private OutputHandler(){
        // costruttore private per fare in modo che nessuno possa istanziare questa classe
        // (Considerando che è una classe appartenente alle utilities)
    }
    public static void print(String message) {
        System.out.println(message);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
    public static void print_asciiart(){
    String asciiart_mind = """
               /$$                 /$$                      
              |__/                | $$                      
 /$$$$$$/$$$$  /$$ /$$$$$$$   /$$$$$$$                      
| $$_  $$_  $$| $$| $$__  $$ /$$__  $$                      
| $$ \\ $$ \\ $$| $$| $$  \\ $$| $$  | $$                      
| $$ | $$ | $$| $$| $$  | $$| $$  | $$                      
| $$ | $$ | $$| $$| $$  | $$|  $$$$$$$                      
|__/ |__/ |__/|__/|__/  |__/ \\_______/                      
""";


    String asciiart_harbor = """
     /$$                           /$$                          
    | $$                          | $$                          
    | $$$$$$$   /$$$$$$   /$$$$$$ | $$$$$$$   /$$$$$$   /$$$$$$ 
    | $$__  $$ |____  $$ /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$
    | $$  \\ $$  /$$$$$$$| $$  \\__/| $$  \\ $$| $$  \\ $$| $$  \\__/
    | $$  | $$ /$$__  $$| $$      | $$  | $$| $$  | $$| $$      
    | $$  | $$|  $$$$$$$| $$      | $$$$$$$/|  $$$$$$/| $$      
    |__/  |__/ \\_______/|__/      |_______/  \\______/ |__/      
    """;



        OutputHandler.print(ANSI_CODE.ANSI_GREEN + asciiart_mind);
        OutputHandler.print(ANSI_CODE.ANSI_BRIGHT_GREEN + asciiart_harbor + ANSI_CODE.ANSI_RESET);
    }
    // fare un'operazione in grado di stampare un riga di cancelletti che funzioneranno da separatore

    public static void separetor(int len){
        /*
        Questa funzione ha il compito di creare un separatore per mettere in
        evidenza una certa parte della CLI.
        Per farlo, prendi in input un intero pari alla lunghezza della stringa da
        separare e considerando la sua metà crea una linea di caratteri #-
        Viene considerata la metà perchè stampando due caratteri per ogni step del for,
        per avere un lunghezza idonea con la lunghezza della stringa da separare è meglio
        considerare la metà della lunghezza totale.
         */

        if (len % 2 == 0){
            for (int i = 0; i < len/2; i++){
                OutputHandler.printf("#-");
            }
            OutputHandler.print("");
        }else {
            for (int i = 0; i < len/2+1; i++){
                OutputHandler.printf("#-");
            }
            OutputHandler.print("");
        }

    }

    public static void clean_page(){
        /*
        Questa funzione ha il compito di "pulire il terminale" ogni qual volta la si chiami.
        E' una finta clear di un terminale UNIX.
         */
        int len = 50;
        for(int i = 0; i < len; i++){
            OutputHandler.print("");
        }
    }
    public static void printAppointments(List<Appuntamento> apps){
        // Questa è la versione senza bean, con l'appuntamento sotto forma di model
        for(int i = 0; i < apps.size(); i++){
            OutputHandler.printf((i+1) + " -> " + "Data: " + apps.get(i).getData() +
                    ", " + "Ora: " + apps.get(i).getOra() + ", " + "Paziente: " +
                    apps.get(i).getNomePaziente() + " " + apps.get(i).getCognomePaziente() +
                    ", " + "Psicologo: " + apps.get(i).getNomePsicologo() + " " +
                    apps.get(i).getCognomePsicologo());
            OutputHandler.print("");
        }
    }

    public static void printAppointmentsBean(List<AppuntamentiBean> apps){
        // Questa è la versione che utilizza come parametro una lista di beans appointments

        for (int i = 0; i < apps.size(); i++){
            OutputHandler.printf((i+1) + " -> " + "Data: " + apps.get(i).getData() + ", " + "Ora: " + apps.get(i).getOra() + ", " + "Paziente: " +
                    apps.get(i).getNomePaziente() + " " + apps.get(i).getCognomePaziente() +
                    ", " + "Psicologo: " + apps.get(i).getNomePsicologo() + " " +
                    apps.get(i).getCognomePsicologo());
            OutputHandler.print("");
        }
    }
    public static void printSpecificAppointment(Appuntamento appuntamento) {
        // Questa è la versione che utilizza come parametro una model appointment

        // Implementa la logica per stampare i dettagli di un singolo appuntamento

        OutputHandler.print("Dettagli appuntamento:");
        OutputHandler.print(appuntamento.toString()); // Assicurati che Appuntamento abbia un override di toString() adeguato
    }
    public static void printSpecificPatientBean(PazientiBean pazientiBean, int x) {
        // Questa è la versione che utilizza come parametro una AppuntamentiBean

        // Implementa la logica per stampare i dettagli di un singolo appuntamento
        OutputHandler.printf(x + " -> " );
        OutputHandler.print(pazientiBean.toString());
    }
}


