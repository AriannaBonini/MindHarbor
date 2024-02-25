module com.example.mind_harbor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens com.example.mind_harbor to javafx.fxml;
    exports com.example.mind_harbor;
    exports com.example.mind_harbor.Controller;
    opens com.example.mind_harbor.Controller to javafx.fxml;
}