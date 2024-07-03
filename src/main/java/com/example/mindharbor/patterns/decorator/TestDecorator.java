package com.example.mindharbor.patterns.decorator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class TestDecorator extends ImageDecorator{
    private Integer testSvolto;

    public TestDecorator(ImageView imageView, Integer testSvolto) {
        super(imageView);
        this.testSvolto = testSvolto;
    }

    public void loadImage() {
        if (testSvolto==0) {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/NonCompletato.png")));
            imageView.setImage(image);
        } else {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/Completato.png")));
            imageView.setImage(image);
        }
    }
}
