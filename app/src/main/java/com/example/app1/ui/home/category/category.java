package com.example.app1.ui.home.category;

public class category {


    private String text;
    private int imageResId;

    public category(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
