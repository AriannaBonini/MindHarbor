package com.example.mind_harbor.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
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
                Platform.runLater(() -> {
                    msgLbl.setText("Benvenuto " + role + ": " + username);
                    logger.info("Login riuscito per l'utente: {}", username);
                    // Gestisci la navigazione in base al ruolo
                    try {
                        if ("Paziente".equals(role)){
                            // l'utente è un paziente
                            // carico l'interfaccia grafica della home del paziente
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mind_harbor/FXML/HomePaziente_NoTest.fxml"));
                            Parent root = loader.load();

                            // ora imposto la scena
                            Stage stage = new Stage();
                            stage.setTitle("Home Page Paziente");
                            stage.setScene(new Scene(root));
                            stage.show();

                            // ora chiudiamo la finestra di login
                            Stage currentStage = (Stage) msgLbl.getScene().getWindow();
                            currentStage.close();
                        } else if ("Psicologo".equals(role)) {
                            // qui si fa la stessa cosa fatta sopra ma con la home dello psicologo

                        }
                    }catch (Exception e){
                        logger.error("Errore durante il caricamento della home page", e);
                    }
                });
                logger.info("Login riuscito per l'utente: {}", username);
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
