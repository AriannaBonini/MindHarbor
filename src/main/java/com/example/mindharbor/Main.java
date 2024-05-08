package com.example.mindharbor;
import com.example.mindharbor.CLI.Controller.CLILoginController;
import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.app_controllers.LoginController;
import com.example.mindharbor.graphic_controllers.LoginGraphicController;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        NavigatorSingleton navigator = NavigatorSingleton.getInstance(primaryStage);
        navigator.gotoPage("/com/example/mindharbor/Login.fxml");

        LoginController loginController = new LoginController();
        LoginGraphicController graphicController = new LoginGraphicController();

        loginController.addObserver(graphicController);
    }

    public static void main(String[] args) {

        /*
            Test: CLI
         */
        SessionManager sessionManager = SessionManager.getInstance();
        CLINavigator cliNavigator = new CLINavigator(sessionManager);
        cliNavigator.showLogin();
        /*
            Fine Test: CLI
         */

        launch();
    }
}