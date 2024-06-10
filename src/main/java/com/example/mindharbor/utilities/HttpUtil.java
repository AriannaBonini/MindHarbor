package com.example.mindharbor.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    //aelihjb

    /*
    Il metodo makeHttpRequest è progettato per eseguire richieste HTTP.
    Prende due parametri: urlPath, che è l'URL a cui fare la richiesta,
    e requestMethod, che indica il metodo HTTP da usare (come "GET" o "POST").
     */
    public static String makeHttpRequest(String urlPath, String requestMethod) {

        StringBuilder response = new StringBuilder();
        /*
        Il tipo di dato StringBuilder permette di aggiungere (append) o modificare (insert e/o delete)
        stringhe senza creare ogni volta una nuova istanza.
        Questo rende StringBuilder molto più efficiente per operazioni che coinvolgono
        frequenti modifiche alle stringhe.
         */
        try{
            URL url = new URL(urlPath);
            /*
            Crea un nuovo oggetto URL dall'URL fornito.
            Questo è necessario per stabilire una connessione
             */

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            /*
            Apre una connessione verso l'URL specificato. openConnection() ritorna un oggetto URLConnection
            che viene castato a HttpURLConnection per poter utilizzare funzionalità specifiche per HTTP.
             */

            connection.setRequestMethod(requestMethod);
            /*
            Imposta il metodo HTTP (come "GET" o "POST") per la connessione.
             */

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            /*
            Crea un BufferedReader che aiuta a leggere il testo dall'input stream
            fornito dalla connessione.
             */

            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
                /*
                Legge ogni linea della risposta dal server fino a quando
                non ci sono più linee da leggere.
                Ogni linea viene aggiunta a StringBuilder per costruire la risposta completa.
                 */
            }
            reader.close();
            /*
            Chiude il BufferedReader per liberare le risorse.
             */
            connection.disconnect();
            /*
            Chiude la connessione HTTP per liberare le risorse di rete.
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
        /*
        Converte il StringBuilder in una stringa e la restituisce.
        Questo è il contenuto della risposta del server alla tua richiesta HTTP.
         */
    }
}
