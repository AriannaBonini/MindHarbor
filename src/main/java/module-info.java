module com.example.mindharbor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires wiremock.jre8.standalone;
    requires com.opencsv;

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
    exports com.example.mindharbor.app_controllers.paziente;
    opens com.example.mindharbor.app_controllers.paziente to javafx.fxml;
    exports com.example.mindharbor.app_controllers.psicologo;
    opens com.example.mindharbor.app_controllers.psicologo to javafx.fxml;
    exports com.example.mindharbor.app_controllers.paziente.prescrivi_terapia;
    opens com.example.mindharbor.app_controllers.paziente.prescrivi_terapia to javafx.fxml;
    exports com.example.mindharbor.app_controllers.paziente.prenota_appuntamento;
    opens com.example.mindharbor.app_controllers.paziente.prenota_appuntamento to javafx.fxml;
    exports com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia;
    opens com.example.mindharbor.app_controllers.psicologo.prescrivi_terapia to javafx.fxml;
    exports com.example.mindharbor.app_controllers.psicologo.prenota_appuntamento;
    opens com.example.mindharbor.app_controllers.psicologo.prenota_appuntamento to javafx.fxml;
    exports com.example.mindharbor.dao.mysql;
}