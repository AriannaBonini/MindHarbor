package com.example.mindharbor.patterns.decorator;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GenereDecorator extends ImageDecorator {
    private String genere;

    public GenereDecorator(ImageView imageView, String genere) {
        super(imageView);
        this.genere = genere;
    }

    public void loadImage() {
        if (genere.equalsIgnoreCase("M")) {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
            imageView.setImage(image);

        } else {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
            imageView.setImage(image);
        }
    }
}
