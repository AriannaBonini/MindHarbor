module com.example.mindharbor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires wiremock.jre8.standalone;

    opens com.example.mindharbor to javafx.fxml;
    opens com.example.mindharbor.graphic_controllers to javafx.fxml;
    exports com.example.mindharbor;
    exports com.example.mindharbor.app_controllers;
    opens com.example.mindharbor.app_controllers to javafx.fxml;
    exports com.example.mindharbor.graphic_controllers to javafx.fxml;
    exports com.example.mindharbor.beans;
    exports com.example.mindharbor.dao;
    exports com.example.mindharbor.exceptions;
    exports com.example.mindharbor.model;
    exports com.example.mindharbor.user_type;
}