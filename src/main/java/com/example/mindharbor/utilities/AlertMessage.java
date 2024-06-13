package com.example.mindharbor.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertMessage {

    public Alert Errore(String messaggio) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Messaggio d'errore");
        alert.setHeaderText("Attenzione");
        alert.setContentText(messaggio);

        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(100);
        alert.getDialogPane().setGraphic(null);

        return alert;
    }

    public Alert Informazione(String Titolo, String Header, String messaggio) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Titolo);
        alert.setHeaderText(Header);
        alert.setContentText(messaggio);

        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(100);
        alert.getDialogPane().setGraphic(null);

        return alert;
    }

    public Integer Avvertenza(String messaggio) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVVERTENZA");
        alert.setHeaderText("Tornando indietro perderai tutti i tuoi progessi");
        alert.setContentText(messaggio);

        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(100);
        alert.getDialogPane().setGraphic(null);

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        if (result == ButtonType.YES) {
            return 1;
        } else {
            return 0;
        }
    }
}
