package com.example.mindharbor.mockapi;

import com.example.mindharbor.model.DomandeTest;
import com.example.mindharbor.utilities.HttpUtil;
import wiremock.net.minidev.json.JSONArray;
import wiremock.net.minidev.json.JSONObject;
import wiremock.net.minidev.json.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TransferQueue;

public class BoundaryMockAPI {

    private static final String BASE_URL = "http://localhost:8080"; // Indirizzo del server WireMock

    public static List<String> TestPiscologici() {

        MockBancaTestPsicologiciAPI.mockTestPiscologiciAPI();

        List<String> testNames = new ArrayList<>();

        String jsonResponse = HttpUtil.makeHttpRequest(BASE_URL + "/test", "GET");

        try {

            /* Qui inizia la connessione Http

            // Crea un'istanza di URL per la richiesta HTTP

            URL url = new URL(BASE_URL+ "/test"); // Sostituisci con l'URL corretto della tua API fittizia

            // Apre una connessione HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Legge la risposta dalla connessione
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            // qui finisce la connessione Http */

            JSONParser parser = new JSONParser();

            JSONArray jsonArray = (JSONArray) parser.parse(jsonResponse); // al posto di response.toString() ci andrà il return della classe HttpUtile
            for (Object obj : jsonArray) {
                String testName = (String) obj;
                testNames.add(testName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return testNames;
    }

    public static List<DomandeTest> DomandeTest(String nomeTest) {

        MockBancaTestPsicologiciAPI.mockTestPiscologiciAPI();

        List<DomandeTest> domande=new ArrayList<>();
        String jsonResponse = HttpUtil.makeHttpRequest(BASE_URL + "/api/test-urls", "GET");
        String urlTest=null;

        //List<String> testNames= new ArrayList<>();

        try {
             /* Inizio connessione
            URL url = new URL(BASE_URL+ "/api/test-urls");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();
         Fine connessione */

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            for (Object key : jsonObject.keySet()) {
                String testName = (String) key;
                if (testName==nomeTest) {
                    urlTest = (String) jsonObject.get(key);
                    break;
                    //testNames.add(testName+ ": " + urlTest);
                }
            }
            domande=TrovaDomande(urlTest);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return domande;
        //jkhil
    }

    public static List<DomandeTest> TrovaDomande(String urlTest) {

        String jsonResponse = HttpUtil.makeHttpRequest(urlTest, "GET");
        List<String> domande=null;
        try {

            /* inizio connessione

            URL url = new URL(urlTest);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            fine connessione */

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            JSONArray domandeArray = (JSONArray) jsonObject.get("domande");
            for (Object domanda : domandeArray) {
                domande.add((String) domanda);
            }

            return domande;

        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}

