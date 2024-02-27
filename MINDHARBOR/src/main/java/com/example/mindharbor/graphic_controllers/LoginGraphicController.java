package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.mindharbor.app_controllers.LoginController;

import java.sql.SQLException;

public class LoginGraphicController {

    @FXML
    private Label msgLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button accediButton;

    private LoginController Credential;


    public void initialize() {
            accediButton.setOnAction(event -> {
                try {
                    onLoginClick(event);
                } catch (DAOException e) {
                    msgLbl.setText("Credenziali errate");
                } catch (SQLException e) {
                    msgLbl.setText("Problema di connessione al DB");
                }
            });
    }


    public void onLoginClick(ActionEvent event) throws DAOException, SQLException {
        String username = usernameTextField.getText();
        String password = enterPasswordField.getText();

        // Controllo se i campi sono vuoti
        if (username.isEmpty() || password.isEmpty()) {
            msgLbl.setText("Inserisci username e password");
            return;
        }
        try {
            LoginCredentialBean credenziali = new LoginCredentialBean(username, password);
            LoginController.login(credenziali);
            //home page
            msgLbl.setText("Login effettuato");

        } catch (DAOException e){
            msgLbl.setText("Credenziali errate");
        }
        catch (SQLException e) {
            msgLbl.setText("Problema di connessione al database");
            System.out.println("Errore di connessione: " + e.getMessage());
        }

    }
}
