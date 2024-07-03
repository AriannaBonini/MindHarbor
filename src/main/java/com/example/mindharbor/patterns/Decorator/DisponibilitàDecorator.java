package com.example.mindharbor.patterns.Decorator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DisponibilitàDecorator extends ImageDecorator{
    private boolean disponibilità;

    public DisponibilitàDecorator(ImageView imageView, boolean disponibilità) {
        super(imageView);
        this.disponibilità = disponibilità;
    }

    public void loadImage() {
        if(!disponibilità) {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaRossa.png")));
            imageView.setImage(image);
        }else {
            Image image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaVerde.png")));
            imageView.setImage(image);
        }
    }

}
