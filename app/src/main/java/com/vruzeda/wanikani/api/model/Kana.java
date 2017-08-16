package com.vruzeda.wanikani.api.model;

import java.io.Serializable;

public class Kana implements Serializable {

    private String character;
    private String image;
    private String meaning;

    public Kana(String character, String image, String meaning) {
        this.character = character;
        this.image = image;
        this.meaning = meaning;
    }

    public String getCharacter() {
        return character;
    }

    public String getImage() {
        return image;
    }

    public String getMeaning() {
        return meaning;
    }
}
