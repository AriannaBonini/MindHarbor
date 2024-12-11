package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.utilities.LabelDuration;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.mindharbor.app_controllers.LoginController;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginGraphicController {
    @FXML
    private Label msgLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button accediButton;

    private final LoginController loginController = new LoginController();
    private static final Logger logger = LoggerFactory.getLogger(LoginGraphicController.class);
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();


    public void initialize() {
        //possiamo aggiungere la registrazione
        msgLbl.setText("Benvenuto");
    }


    @FXML
    public void onLoginClick() {
        String username = usernameTextField.getText();
        String password = enterPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            msgLbl.setText("Inserisci username e/o password");
            return;
        }
        try {
            LoginCredentialBean credenziali = new LoginCredentialBean(username, password);
            InfoUtenteBean infoUtenteLoggato = loginController.login(credenziali);
            if (infoUtenteLoggato == null) {
                new LabelDuration().duration(msgLbl, "Credenziali errate");
            } else {
                if (infoUtenteLoggato.getUserType().equals(UserType.PAZIENTE)) {
                    homePaziente();
                } else {
                    homePsicologo();
                }
            }
        } catch (DAOException e) {
            logger.error("Errore durante la ricerca dell'utente {}", username, e);
            new LabelDuration().duration(msgLbl, "Credenziali errate");
        } catch (SessionUserException e) {
            logger.info("{} già loggato", username, e);
            new LabelDuration().duration(msgLbl, "Utente già loggato");
        }

    }

    public void homePaziente() {
        try {
            Stage loginstage = (Stage) accediButton.getScene().getWindow();
            loginstage.close();
            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare la Home del paziente", e);
        }

    }

    public void homePsicologo() {
        try {
            Stage loginstage = (Stage) accediButton.getScene().getWindow();
            loginstage.close();
            navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare la Home dello psicologo", e);
        }

    }

}
