package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLILoginView;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.dao.UtenteDao;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

public class CLILoginController {
    private static final Logger logger = LoggerFactory.getLogger(CLILoginController.class);
    private UtenteDao utenteDao;
    private CLILoginView loginView;
    private SessionManager sessionManager;
    private CLINavigator cliNavigator;

    public CLILoginController(SessionManager sessionManager, CLINavigator cliNavigator) {
        this.utenteDao = new UtenteDao();
        this.loginView = new CLILoginView();
        this.sessionManager = sessionManager;
        this.cliNavigator = cliNavigator;
    }

    public void esegui() {

        LoginCredentialBean credentialBean = null;

        while(true) {
            loginView.printWelcomeMessage();
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            credentialBean = new LoginCredentialBean(username, password);

            if (!validateCredentials(credentialBean)){
                loginView.printLoginError(1, "Credenziali non valide. Digita '1' per riprovare oppure 'exit' per logout.");
                String decision = loginView.getInput();
                if ("exit".equalsIgnoreCase(decision)) {
                    sessionManager.logout();
                    loginView.clean();
                    cliNavigator.showLogin();
                    return;
                }
            } else {
                break; // esce dal ciclo se le credenziali sono valide
            }
        }

        try {
            Utente utente = utenteDao.TrovaUtente(credentialBean.getUsername(), credentialBean.getPassword());
            if (utente != null) {
                sessionManager.login(utente);  // Questo metodo è void e potrebbe lanciare un'eccezione se non riesce
                // Se non viene lanciata un'eccezione, assumiamo che il login sia riuscito
                cliNavigator.showHomepage(); // Usiamo il CLINavigator per spostarci ad altre view
            } else {
                loginView.printLoginError(1, "Credenziali errate. Riprova.");
            }
        } catch (SQLException e) {
            loginView.printDatabaseError();
            logger.error("Problemi di connessione al database", e);
        } catch (DAOException e) {
            loginView.printLoginError(1, "Errore nel recupero dell'utente dal database");
            logger.error("Errore nel recupero dell'utente dal database", e);
        } catch (SessionUserException e) {
            loginView.printLoginError(1, "Errore di login per l'utente");
            logger.error("Errore di login per l'utente: {}", credentialBean.getUsername(), e);
        }
    }

    private boolean validateCredentials(LoginCredentialBean credentials) {
        if (credentials.getUsername() == null || credentials.getUsername().trim().isEmpty()) {
            logger.error("Username non può essere vuoto");
            return false;
        }
        if (credentials.getPassword() == null || credentials.getPassword().trim().isEmpty()) {
            logger.error("Password non può essere vuota");
            return false;
        }
        return true;
    }
}
