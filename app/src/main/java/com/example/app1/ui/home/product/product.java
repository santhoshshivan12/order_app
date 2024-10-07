package com.example.app1.ui.home.product;
import com.example.app1.ui.home.category.category;
public class product {
    private String name;
    private double price;
    private category category;
    private int imageId;

    public product(String name, double price, com.example.app1.ui.home.category.category category, int imageId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public int getImageId() {
        return imageId;
    }

    public String getCategory() {

        return category.getText();
    }

}
