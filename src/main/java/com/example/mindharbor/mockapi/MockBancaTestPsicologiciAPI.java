package com.example.mindharbor.mockapi;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockBancaTestPsicologiciAPI {
    private static final WireMockServer wireMockServer = new WireMockServer(8080);
    public static void mockTestPiscologiciAPI() {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\"Test di personalità\", \"Test di Ansia\",\"Test di memoria\"]")));

        stubFor(get(urlEqualTo("/api/test-urls"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withStatus(200)
                    .withBody("{\"Test di Personalità\": \"http://localhost:8080/api/contenuti/test1\", " +
                          "\"Test di Ansia\": \"http://localhost:8080/api/contenuti/test2\", " +
                          "\"Test di Memoria\": \"http://localhost:8080/api/contenuti/test3\"}")));


        stubFor(get(urlEqualTo("/api/contenuti/test1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"nome\": \"Test di Personalità\", \"domande\": [\"Domanda 1\", \"Domanda 2\"]}")));

        stubFor(get(urlEqualTo("/api/contenuti/test2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"nome\": \"Test di Ansia\", \"domande\": [\"Domanda 1\", \"Domanda 2\"]}")));

        stubFor(get(urlEqualTo("/api/contenuti/test3"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"nome\": \"Test di Memoria\", \"domande\": [\"Domanda 1\", \"Domanda 2\"]}")));

    }

}
