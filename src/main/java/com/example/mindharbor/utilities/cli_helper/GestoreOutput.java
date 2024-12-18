package com.example.mindharbor.utilities.cli_helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GestoreOutput {

    private GestoreOutput(){
        /*
            costruttore privato per evitare istanze
        */
    }
    private static final Logger logger = LoggerFactory.getLogger(GestoreOutput.class);

    public static void stampaMessaggio(String messaggio){
        logger.info(messaggio);
    }


    public static void stampaLogoLogin(){
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
        GestoreOutput.stampaMessaggio(AnsiCode.ANSI_GREEN + asciiart_mind + "\n" + AnsiCode.ANSI_BRIGHT_GREEN + asciiart_harbor + AnsiCode.ANSI_RESET);
    }


    public static void pulisciPagina(){
        for (int i = 0; i < 100; i++){
            GestoreOutput.stampaMessaggio("\b");
        }
    }
}

