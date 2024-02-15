package com.example.demomindharbor.logic.graphic_controllers;

import com.example.demomindharbor.logic.app_controllers.loginController;
import com.example.demomindharbor.logic.beans.LoginCredentialBean;
import com.example.demomindharbor.logic.exceptions.DAOException;
import com.example.demomindharbor.logic.exceptions.InvalidFormatException;
import com.example.demomindharbor.logic.exceptions.SessionUserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.logging.Level;

public class LoginGraphicController extends absGraphicController {
    @FXML
    private Label msgLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button accediButton;

    private loginController loginController;

    @Override
    public void initialize() {
        super.initialize();

        loginController = new loginController();
        accediButton.setOnAction(this::userLogin);
    }

    public void userLogin(ActionEvent event) {
        if (usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()) {
            msgLbl.setText("Inserire username e password");
        } else {
            try {
                // Prepara le credenziali di login
                LoginCredentialBean credentialsBean = new LoginCredentialBean(usernameTextField.getText(), enterPasswordField.getText());

                // Tentativo di login
                loginController.login(credentialsBean);

                // Se nessuna eccezione è stata lanciata, il login è riuscito
                msgLbl.setText("Login riuscito");
                // Vai alla pagina HOME o mostra un messaggio di successo
                //goToPage(HOME);
            } catch (InvalidFormatException e) {
                msgLbl.setText("Formato non valido");
            } catch (DAOException | SQLException e) {
                msgLbl.setText("Credenziali errate o problema di connessione al database");
            } catch (SessionUserException e) {
                msgLbl.setText("Utente già loggato");
                // Vai alla pagina HOME o gestisci come preferisci
            }
        }
    }

}

