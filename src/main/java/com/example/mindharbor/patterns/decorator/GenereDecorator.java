package com.example.mindharbor.patterns.decorator;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GenereDecorator extends ImageDecorator {
    private final String genere;

    public GenereDecorator(ImageView imageView, String genere) {
        super(imageView);
        this.genere = genere;
    }

    public void caricaImmagine() {
        if (genere.equalsIgnoreCase("M")) {
            Image immagine= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
            imageView.setImage(immagine);

        } else {
            Image immagine= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
            imageView.setImage(immagine);
        }
    }
}
