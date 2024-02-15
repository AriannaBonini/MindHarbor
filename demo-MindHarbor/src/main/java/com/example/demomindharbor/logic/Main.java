package com.example.demomindharbor.logic;

import com.example.demomindharbor.logic.utilities.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        String firstPage = "/com/example/demomindharbor/Login.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(firstPage));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        NavigatorSingleton n = NavigatorSingleton.getInstance(primaryStage);

        n.getStg().initStyle(StageStyle.TRANSPARENT);
        n.getStg().setTitle("MindHarbor");
        n.getStg().setResizable(false);
        n.getStg().setScene(scene);
        n.getStg().show();
    }

    public static void main(String[] args) {
        launch();
    }
}