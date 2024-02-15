package com.example.demomindharbor.logic.graphic_controllers;

import com.example.demomindharbor.logic.utilities.absDialogNavigatorController;

import com.example.demomindharbor.logic.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.logging.Logger;

public abstract class absGraphicController extends absDialogNavigatorController {
    @FXML
    private Pane titlePane; // Value injected by FXMLLoader
    protected static final String LOGIN = "Login.fxml";
    protected static final String SIGN_UP = "SignUp.fxml";


    private double x;
    private double y;

    protected Logger logger = Logger.getAnonymousLogger();

    @Override
    public void initialize(){
        super.initialize();
        assert titlePane != null : "fx:id=\"TitlePane\" was not injected: check your FXML file.";


        titlePane.setOnMouseDragged(mouseEvent -> {
            NavigatorSingleton n = NavigatorSingleton.getInstance();
            n.getStg().setX(mouseEvent.getScreenX() - x);
            n.getStg().setY(mouseEvent.getScreenY() - y);
        });

        titlePane.setOnMousePressed(mouseEvent -> {
            NavigatorSingleton n = NavigatorSingleton.getInstance();
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
    }
}