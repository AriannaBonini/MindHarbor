package com.example.mindharbor.controller_grafici_cli;


import com.example.mindharbor.beans.CredenzialiLoginBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.controller_applicativi.LoginController;
import com.example.mindharbor.eccezioni.EccezioneDAO;
import com.example.mindharbor.eccezioni.EccezioneFormatoNonValido;
import com.example.mindharbor.eccezioni.EccezioneSessioneUtente;
import com.example.mindharbor.tipo_utente.UserType;
import com.example.mindharbor.utilities.cli_helper.CodiciAnsi;
import com.example.mindharbor.utilities.cli_helper.GestoreOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControllerGraficoLoginCLI extends AbsGestoreInput {

    private static final Logger logger = LoggerFactory.getLogger(ControllerGraficoLoginCLI.class);

    @Override
    public void start() {
        GestoreOutput.pulisciPagina();
        GestoreOutput.stampaLogoLogin();
        boolean esci = false;
        while(!esci) {
            int opzione;
            try {
                opzione = mostraMenu();
                switch(opzione) {
                    case 1 -> login();
                    case 2 -> esci=true;
                    default -> throw new EccezioneFormatoNonValido("Scelta non valida");
                }
            } catch (EccezioneFormatoNonValido e) {
                logger.info("Scelta non valida ", e);
            }
        }
        System.exit(0);
    }


    @Override
    public int mostraMenu() {
        GestoreOutput.stampaMessaggio(CodiciAnsi.ANSI_GRASSETTO + "*** BENVENUTO! ***\n" + CodiciAnsi.ANSI_RIPRISTINA_GRASSETTO);
        GestoreOutput.stampaMessaggio("1) Login");
        GestoreOutput.stampaMessaggio("2) Esci");

        return opzioneScelta(1,2);
    }

    public void login() {
        LoginController loginController = new LoginController();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            GestoreOutput.stampaMessaggio("username: ");
            String username = reader.readLine();
            GestoreOutput.stampaMessaggio("password: ");
            String password = reader.readLine();
            CredenzialiLoginBean credenzialiLoginBean = new CredenzialiLoginBean(username,password);
            InfoUtenteBean infoUtenteBean = loginController.login(credenzialiLoginBean);

            if(infoUtenteBean == null) {
                GestoreOutput.stampaMessaggio("Credenziali Errate");
                login();
            } else if (infoUtenteBean.getUserType().equals(UserType.PSICOLOGO)) {
                new ControllerGraficoHomePsicologoCLI().start();
            } else {
                new ControllerGraficoHomePazienteCLI().start();
            }
        }
        catch (EccezioneDAO | IOException e) {
            logger.error(e.getMessage());
        } catch (EccezioneSessioneUtente e) {
            logger.info(e.getMessage());
        }
    }

}
