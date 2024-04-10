package com.example.mindharbor.CLI;

import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Utente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.mindharbor.utilities.OutputHandler;
import java.util.Scanner;
import java.sql.SQLException;
import com.example.mindharbor.utilities.ANSI_CODE;
public class CLI_LoginController {
    private static final Logger logger = LoggerFactory.getLogger(CLI_LoginController.class);
    private final UtenteDao utenteDao;
    public CLI_LoginController() {
        this.utenteDao = new UtenteDao();
    }

    public void esegui() {
        Scanner scanner = new Scanner(System.in);
        OutputHandler.print_asciiart();
        OutputHandler.print("");
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Benvenuto in MindHarbor" + ANSI_CODE.ANSI_RESET_BOLD);
        OutputHandler.printf("Inserisci il tuo username: ");
        String username = scanner.nextLine();
        OutputHandler.printf("Inserisci la tua password: ");
        String password = scanner.nextLine();

        try {
            Utente utente = utenteDao.TrovaUtente(username, password);
            OutputHandler.clean_page();
            CLI_HomepageController cli_homepageController = new CLI_HomepageController();
            cli_homepageController.mostraHomepage(utente);
        } catch (DAOException e) {
            OutputHandler.clean_page();
            OutputHandler.print(ANSI_CODE.ANSI_RED + "CREDENZIALI ERRATE" + ANSI_CODE.ANSI_RESET);
        }
        catch (SQLException e){
            logger.info("Problemi di connessione al database", e);
            OutputHandler.print("Problema di connessione al database.");
        }
        // si fa ripartire il login
        esegui();
    }
}
