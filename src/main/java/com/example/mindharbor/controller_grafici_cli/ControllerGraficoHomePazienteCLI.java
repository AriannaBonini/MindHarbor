package com.example.mindharbor.controller_grafici_cli;

import com.example.mindharbor.controller_applicativi.paziente.HomePazienteController;
import com.example.mindharbor.eccezioni.EccezioneFormatoNonValido;
import com.example.mindharbor.utilities.cli_helper.CodiciAnsi;
import com.example.mindharbor.utilities.cli_helper.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerGraficoHomePazienteCLI extends AbsGestoreInput{

    private static final Logger logger = LoggerFactory.getLogger(ControllerGraficoHomePazienteCLI.class);
    private final HomePazienteController homePazienteController = new HomePazienteController();

    @Override
    public void start() {

        GestoreOutput.pulisciPagina();
        boolean esci = false;

        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> System.out.println("Prenota Appuntamento");
                    case 2 -> listaAppuntamenti();
                    case 3 -> System.out.println("Terapia");
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

    @Override
    public int mostraMenu() {
        GestoreOutput.stampaMessaggio(CodiciAnsi.ANSI_GRASSETTO + "HOME PAGE\n" + CodiciAnsi.ANSI_RIPRISTINA_GRASSETTO);
        GestoreOutput.stampaMessaggio("1) Prenota Appuntamento");
        GestoreOutput.stampaMessaggio("2) Lista Appuntamenti");
        GestoreOutput.stampaMessaggio("3) Terapia");
        GestoreOutput.stampaMessaggio("4) Test");
        GestoreOutput.stampaMessaggio("5) Logout");

        return opzioneScelta(1,5);
    }

    private void listaAppuntamenti() {new ControllerGraficoListaAppuntamentiPazienteCLI().start();}
}
