package com.example.mindharbor.patterns.decorator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TestDecorator extends ImageDecorator{
    private final Integer testSvolto;

    public TestDecorator(ImageView imageView, Integer testSvolto) {
        super(imageView);
        this.testSvolto = testSvolto;
    }

    public void caricaImmagine() {
        if (testSvolto==0) {
            Image immagine = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/NonCompletato.png")));
            imageView.setImage(immagine);
        } else {
            Image immagine = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/Completato.png")));
            imageView.setImage(immagine);
        }
    }
}
