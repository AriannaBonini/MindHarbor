package com.example.mindharbor.mockapi;

import com.example.mindharbor.utilities.HttpUtil;
import wiremock.net.minidev.json.JSONObject;
import wiremock.net.minidev.json.parser.JSONParser;

import java.util.Random;

public class BoundaryMockAPICalendario {
    private static final String BASE_URL = "http://localhost:8080";

    public static boolean Calendario() {

        boolean disponibile = false;

        MockCalendarioPsicologiAPI.mockCalendarioAPI();
        double randomNumber = Math.random();
        String endpoint = (randomNumber < 0.5) ? "/disponibilita" : "/disponibilita1";

        String jsonResponse = HttpUtil.makeHttpRequest(BASE_URL + endpoint, "GET");

        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            String valore=(String)jsonObject.get("disponibile");
            if(valore.equalsIgnoreCase("si")) {
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return disponibile;
    }
}
