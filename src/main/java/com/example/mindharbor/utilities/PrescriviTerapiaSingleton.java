package com.example.mindharbor.utilities;

import com.example.mindharbor.controller_applicativi.psicologo.PrescriviTerapia;

public class PrescriviTerapiaSingleton {
    private static PrescriviTerapia prescriviTerapia=null;

    private PrescriviTerapiaSingleton() {}

    // Metodo sincronizzato per ottenere l'istanza unica
    public static synchronized PrescriviTerapia getInstance() {
        if (prescriviTerapia == null) {
            prescriviTerapia = new PrescriviTerapia();
        }
        return prescriviTerapia;
    }

}
