package com.example.mindharbor.utilities;

import com.example.mindharbor.controller_applicativi.paziente.PrenotaAppuntamento;

public class PrenotaAppuntamentoSingleton {
    private static PrenotaAppuntamento prenotaAppuntamento=null;

    private PrenotaAppuntamentoSingleton() {}

    // Metodo sincronizzato per ottenere l'istanza unica
    public static synchronized PrenotaAppuntamento getInstance() {
        if (prenotaAppuntamento == null) {
            prenotaAppuntamento = new PrenotaAppuntamento();
        }
        return prenotaAppuntamento;
    }
}
