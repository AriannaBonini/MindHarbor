package com.example.mindharbor.utilities;

import com.example.mindharbor.Main;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.graphic_controllers.AppuntamentiPsicologoGraphicController;
import com.example.mindharbor.graphic_controllers.SchedaPersonalePazienteGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;

    protected Stage stg;

    private String parametro;

    private AppuntamentiBean app;

    private Date data;

    public Stage getStg() {
        return this.stg;
    }

    protected NavigatorSingleton(Stage stg) {
        this.stg = stg;
    }

    public static synchronized NavigatorSingleton getInstance(Stage stg){
        if(NavigatorSingleton.instance == null)
            NavigatorSingleton.instance = new NavigatorSingleton(stg);

        return instance;
    }

    public static synchronized NavigatorSingleton getInstance() {
        return instance;
    }

    public void gotoPage(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Mind Harbor");
        stage.setResizable(false);
        stage.show();
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro() {
        return parametro;
    }

    public void setData(Date data) {this.data=data;}
    public Date getData() {return data;}

    public void setAppuntamentoBean(AppuntamentiBean app) {this.app=app;}
    public AppuntamentiBean getAppuntamentoBean() {return app;}
    public void deleteAppuntamentoBean() {this.app=null;}

}
