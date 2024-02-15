module com.example.demomindharbor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.demomindharbor to javafx.fxml;
    exports com.example.demomindharbor.logic;
}