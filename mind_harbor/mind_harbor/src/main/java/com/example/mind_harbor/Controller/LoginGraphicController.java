package com.example.mind_harbor.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginGraphicController {
    private static final Logger logger = LoggerFactory.getLogger(LoginGraphicController.class);

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    private Label msgLbl;
    private LoginController loginController;
    public LoginGraphicController(){
        this.loginController = new LoginController();
    }
    @FXML
    protected void userLogin(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = enterPasswordField.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            msgLbl.setText("Inserire username e password");
            logger.info("Tentativo di login senza inserimento di username o password");
        }else {
        loginController.attemptLogin(username, password, new LoginController.LoginResulHendler() {
            @Override
            public void onLoginSuccess(String role) {
                Platform.runLater(() -> msgLbl.setText("Benvenuto " + role + ": " + username));
                logger.info("Login riuscito per l'utente: {}", username);
                // Gestisci la navigazione in base al ruolo
            }

            @Override
            public void onLoginFailed(String message) {
                Platform.runLater(()->msgLbl.setText(message));
                logger.warn("Login fallito per l'utente {}", username);
            }
        });
        }
    }
}
