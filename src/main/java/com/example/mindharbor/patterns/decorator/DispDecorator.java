package com.example.mindharbor.patterns.decorator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DispDecorator extends ImageDecorator{
    private final boolean disp;

    public DispDecorator(ImageView imageView, boolean disp) {
        super(imageView);
        this.disp = disp;
    }

    public void loadImage() {
        Image image;
        if(!disp) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaRossa.png")));
        }else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/casellaVerde.png")));
        }
        imageView.setImage(image);
    }

}
