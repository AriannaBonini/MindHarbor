package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.beans.LoginCredentialBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.exceptions.SessionUserException;
import com.example.mindharbor.patterns.ClassObserver;
import com.example.mindharbor.patterns.Observer;
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
import java.sql.SQLException;

public class LoginGraphicController implements Observer{

    @FXML
    private Label msgLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;
    @FXML
    private Button accediButton;

    private final LoginController loginController= new LoginController();
    private static final Logger logger = LoggerFactory.getLogger(LoginGraphicController.class);
    private final ClassObserver observer= new ClassObserver();


    public void initialize() {
       //possiamo aggiungere la registrazione
        observer.addObserver(this);
        msgLbl.setText("Benvenuto");
    }



    @FXML
    public void onLoginClick()  {
        String username = usernameTextField.getText();
        String password = enterPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            msgLbl.setText("Inserisci username e password");
            return;
        }
        try {
            LoginCredentialBean credenziali = new LoginCredentialBean(username, password);
            loginController.login(credenziali);


        } catch (DAOException e){
            logger.info("Credenziali errate per l'utente" + username, e);
            new LabelDuration().Duration(msgLbl,"Credenziali errate");
        }
        catch (SQLException e) {
            logger.info("Problemi di connessione al database", e);
            new LabelDuration().Duration(msgLbl,"Problema di connessione al database");
        }
        catch(SessionUserException e) {
            logger.info(username + "già loggato", e);
            new LabelDuration().Duration(msgLbl,"Utente già loggato");
        }

    }

    @Override
    public void updateUserStatus(UserType userType) {

        NavigatorSingleton navigator= NavigatorSingleton.getInstance();
        try {
            if (userType == UserType.PAZIENTE) {

                Stage loginstage = (Stage) accediButton.getScene().getWindow();
                loginstage.close();

                navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

            } else if (userType == UserType.PSICOLOGO) {

                Stage loginstage = (Stage) accediButton.getScene().getWindow();
                loginstage.close();

                navigator.gotoPage("/com/example/mindharbor/HomePsicologo.fxml");
            }
        }catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

}
