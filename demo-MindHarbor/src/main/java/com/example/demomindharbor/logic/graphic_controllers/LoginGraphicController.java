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
    public void initialize(){
        super.initialize();

        loginController = new loginController();
    }

    public void userLogin(ActionEvent event) {
        try{
            LoginCredentialBean credentialsBean = new LoginCredentialBean(
            usernameTextField.getText(), enterPasswordField.getText());

            loginController.login(credentialsBean);

            goToPage(HOME);
        }
        catch (InvalidFormatException e){
            logger.log(Level.INFO, e.getMessage());
            msgLbl.setText("Empty Fields");
        }
        catch (DAOException e){
            logger.log(Level.INFO, e.getMessage());
            msgLbl.setText("Wrong credentials");
        }
        catch (SQLException e){
            logger.log(Level.INFO, e.getMessage());
            msgLbl.setText("Database not working");
        }
        catch (SessionUserException e){
            logger.log(Level.INFO, e.getMessage());
            msgLbl.setText("User already logged in");
            goToPage(HOME);
        }
    }
}
