package com.example.mindharbor.mockapi;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockBancaTestPsicologiciAPI {
    private static final WireMockServer wireMockServer = new WireMockServer(8080);
    /* public static void mockTestPiscologiciAPI() {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
    }
     */
}