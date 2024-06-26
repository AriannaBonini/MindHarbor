package com.example.mindharbor;

import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        NavigatorSingleton navigator = NavigatorSingleton.getInstance(primaryStage);
        navigator.gotoPage("/com/example/mindharbor/Login.fxml");

    }

    public static void main(String[] args) {launch();}
}