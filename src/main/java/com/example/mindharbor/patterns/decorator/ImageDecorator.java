package com.example.mindharbor.patterns.decorator;

import javafx.scene.image.ImageView;

public abstract class ImageDecorator {
     protected ImageView imageView;

     public ImageDecorator(ImageView imageView) {
         this.imageView = imageView;
     }

     public abstract void loadImage();
}

