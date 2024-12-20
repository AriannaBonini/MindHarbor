package com.example.mindharbor.controller_grafici_cli;

import com.example.mindharbor.beans.PazienteBean;
import com.example.mindharbor.controller_applicativi.paziente.HomePazienteController;
import com.example.mindharbor.eccezioni.EccezioneDAO;
import com.example.mindharbor.eccezioni.EccezioneFormatoNonValido;
import com.example.mindharbor.utilities.cli_helper.CodiciAnsi;
import com.example.mindharbor.utilities.cli_helper.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerGraficoHomePazienteCLI extends AbsGestoreInput{

    private static final Logger logger = LoggerFactory.getLogger(ControllerGraficoHomePazienteCLI.class);
    private final HomePazienteController homePazienteController = new HomePazienteController();
    private PazienteBean pazienteBean;
    private boolean notificaTest;
    private boolean notificaTerapie;
    private boolean notificaAppuntamenti;


    @Override
    public void start() {

        GestoreOutput.pulisciPagina();
        if(homePazienteController.getPsicologo()) {
            notificaNuoviTest();
            notificaNuoveTerapie();
            notificaNuoviAppuntamenti();
        }

        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> System.out.println("Prenota Appuntamento");
                    case 2 -> listaAppuntamenti();
                    case 3 -> terapia();
                    case 4 -> System.out.println("Test");
                    case 5 -> esci = true;
                    default -> throw new EccezioneFormatoNonValido("Scelta non valida");
                }
            } catch (EccezioneFormatoNonValido e) {
                logger.info("Scelta non valida ", e);
            }
        }
        homePazienteController.logout();
        new ControllerGraficoLoginCLI().start();
    }

    private void notificaNuoviTest() {
        try {
            pazienteBean =homePazienteController.cercaNuoviTestDaSvolgere();
            notificaTest= pazienteBean.getNumNotifiche() > 0;
        } catch (EccezioneDAO e) {
            logger.info("Errore nella ricerca dei nuovi test assegnati al paziente ", e);
        }
    }

    private void notificaNuoveTerapie() {
        try {
            pazienteBean = homePazienteController.cercaNuoveTerapie();
            notificaTerapie= pazienteBean.getNumNotifiche() > 0;
        } catch (EccezioneDAO e) {
            logger.info("Errore nella ricerca delle nuove terapie assegnate al paziente ", e);
        }
    }

    private void notificaNuoviAppuntamenti() {
        try {
            pazienteBean = homePazienteController.cercaNuoviAppuntamenti();
            notificaAppuntamenti= pazienteBean.getNumNotifiche() > 0;

        } catch (EccezioneDAO e) {
            logger.info("Errore nella ricerca dei nuovi appuntamenti ", e);
        }
    }


    @Override
    public int mostraMenu() {
        GestoreOutput.stampaMessaggio(CodiciAnsi.ANSI_GRASSETTO + "HOME PAGE\n" + CodiciAnsi.ANSI_RIPRISTINA_GRASSETTO);
        GestoreOutput.stampaMessaggio("1) Prenota Appuntamento");
        GestoreOutput.stampaMessaggio("2) " + GestoreOutput.stampaMessaggioConNotifica("Lista Appuntamenti",notificaAppuntamenti));
        GestoreOutput.stampaMessaggio("3) " + GestoreOutput.stampaMessaggioConNotifica("Terapia",notificaTerapie));
        GestoreOutput.stampaMessaggio("4) " + GestoreOutput.stampaMessaggioConNotifica("Test",notificaTest));
        GestoreOutput.stampaMessaggio("5) Logout");

        return opzioneScelta(1,5);
    }

    private void listaAppuntamenti() {new ControllerGraficoAppuntamentiPazienteCLI().start();}
    private void terapia() {new ControllerGraficoTerapiaPazienteCLI().start();}
}