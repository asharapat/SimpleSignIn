package com.example.seydazimovnurbol.registerfirebase;

/**
 * Created by root on 11/27/17.
 */

public class Fighter {
    private String name;
    private String imageUrl;

    Fighter(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
