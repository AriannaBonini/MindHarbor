package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.Observer;
import com.example.mindharbor.session.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.mindharbor.app_controllers.LoginController;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class LoginGraphicController  implements Observer {

    @FXML
    private Label msgLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button accediButton;

    private LoginController loginController;

    private static final Logger logger = LoggerFactory.getLogger(LoginGraphicController.class);


    public void initialize() {
       loginController= new LoginController();
       //possiamo aggiungere la registrazione
        msgLbl.setText("Benvenuto");
    }

    @FXML
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
            Stage loginstage = (Stage) accediButton.getScene().getWindow();
            loginstage.close();

        } catch (DAOException e){
            msgLbl.setText("Credenziali errate");
        }
        catch (SQLException e) {
            msgLbl.setText("Problema di connessione al database");
        }

    }

    @Override
    public void updateUserStatus(UserType userType) {
        try {
            if (userType == UserType.PAZIENTE) {

                // carico l'interfaccia grafica della home del paziente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mindharbor/HomePaziente.fxml"));
                Parent root = loader.load();

                // ora imposto la scena
                Stage stage = new Stage();
                stage.setTitle("Home Page Paziente");
                stage.setScene(new Scene(root));
                stage.show();

            } else if (userType == UserType.PSICOLOGO) {
                // Cambia la vista per lo psicologo
                System.out.println("Sono uno psicologo");
            }
        }catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }
}
