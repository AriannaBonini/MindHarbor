package com.example.mindharbor.utilities;

import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImmagineDecorator;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ListaGraphicControllerHelper {

    private ListaGraphicControllerHelper() {//costruttore privato//
    }

    public static HBox createPersonBox(ImageView imageView, String nome, String cognome, String genere) {
        Label labelNome = new Label("\n     NOME: " + nome);
        Label labelCognome = new Label("     COGNOME: " + cognome);
        labelNome.setTextFill(Color.WHITE);
        labelCognome.setTextFill(Color.WHITE);

        VBox box = new VBox(labelNome, labelCognome);

        ImmagineDecorator immagineDecorator = new GenereDecorator(imageView, genere);
        immagineDecorator.caricaImmagine();

        return new HBox(imageView, box);
    }
}
