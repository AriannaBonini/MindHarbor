package com.example.mindharbor.mockapi;

import com.example.mindharbor.model.DomandeTest;
import com.example.mindharbor.utilities.HttpUtil;
import wiremock.net.minidev.json.JSONArray;
import wiremock.net.minidev.json.JSONObject;
import wiremock.net.minidev.json.parser.JSONParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundaryMockAPI {

    private static final String BASE_URL = "http://localhost:8080"; // Indirizzo del server WireMock

    public static List<String> TestPiscologici() {

        MockBancaTestPsicologiciAPI.mockTestPiscologiciAPI();

        List<String> testNames = new ArrayList<>();

        String jsonResponse = HttpUtil.makeHttpRequest(BASE_URL + "/test", "GET");

        try {

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

    public static DomandeTest DomandeTest(String nomeTest) {

        MockBancaTestPsicologiciAPI.mockTestPiscologiciAPI();

        DomandeTest domande=new DomandeTest();
        String jsonResponse = HttpUtil.makeHttpRequest(BASE_URL + "/api/test-urls", "GET");
        String urlTest=null;

        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            for (Object key : jsonObject.keySet()) {
                String testName = (String) key;
                if (testName.equalsIgnoreCase(nomeTest)) {
                    urlTest = (String) jsonObject.get(key);
                    break;
                }
            }
            domande.setDomande(TrovaDomande(urlTest));
            domande.setPunteggio(TrovaPunteggio(nomeTest));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return domande;
    }

    public static List<String> TrovaDomande(String urlTest) {

        String jsonResponse = HttpUtil.makeHttpRequest(urlTest, "GET");
        List<String> domande= new ArrayList<>();

        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            JSONArray domandeArray = (JSONArray) jsonObject.get("domande");
            for (Object domanda : domandeArray) {
                domande.add((String) domanda);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return domande;
    }

    public static List<Integer> TrovaPunteggio(String nomeTest) {
        String urlPunteggi=null;
        List<Integer> punteggi= new ArrayList<>(Arrays.asList(0,0,0));

        if(nomeTest.equalsIgnoreCase("Test di Personalità")) {
            urlPunteggi= BASE_URL+"/api/contenuti/punteggitest1";
        } else if (nomeTest.equalsIgnoreCase("Test di Ansia")) {
            urlPunteggi= BASE_URL+"/api/contenuti/punteggitest2";
        }else {
            urlPunteggi= BASE_URL+"/api/contenuti/punteggitest3";
        }

        String jsonResponse = HttpUtil.makeHttpRequest(urlPunteggi, "GET");

        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            for (Object key : jsonObject.keySet()) {
                String nomeRisposta = (String) key;
                String valoreStringa = (String) jsonObject.get(key);
                Integer valoreIntero = Integer.parseInt(valoreStringa);
                if (nomeRisposta.equalsIgnoreCase("felice")) {
                    punteggi.set(0,valoreIntero);
                } else if (nomeRisposta.equalsIgnoreCase("triste")) {
                    punteggi.set(1,valoreIntero);
                }else {
                    punteggi.set(2,valoreIntero);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return punteggi;
    }
}

