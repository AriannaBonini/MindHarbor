package com.example.mindharbor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mindharbor/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Mind Harbor");
        primaryStage.setResizable(false);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}