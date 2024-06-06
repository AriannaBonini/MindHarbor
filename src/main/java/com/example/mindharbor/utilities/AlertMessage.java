package com.example.mindharbor.utilities;

import javafx.scene.control.Alert;

public class AlertMessage {

    public Alert Errore(String messaggio) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Messaggio d'errore");
        alert.setHeaderText("Attenzione");
        alert.setContentText(messaggio);

        alert.getDialogPane().setPrefWidth(250);
        alert.getDialogPane().setPrefHeight(70);
        alert.getDialogPane().setGraphic(null);

        return alert;
    }

    public Alert Informazione(String messaggio) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione Completata ");
        alert.setHeaderText("Esito Positivo");
        alert.setContentText(messaggio);

        alert.getDialogPane().setPrefWidth(250);
        alert.getDialogPane().setPrefHeight(70);
        alert.getDialogPane().setGraphic(null);

        return alert;
    }

}
