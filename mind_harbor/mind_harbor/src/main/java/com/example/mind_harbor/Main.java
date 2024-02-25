package com.example.mind_harbor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carica il layout dell'interfaccia utente dal file FXML
        String firstPage = "/com/example/mind_harbor/FXML/Login.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(firstPage));
        // Imposta il titolo della finestra
        primaryStage.setTitle("Login");

        // Imposta la scena nel palcoscenico e mostra la finestra
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
