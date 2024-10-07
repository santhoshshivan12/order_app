package com.example.app1.ui.oder;

public class CartItem {
    private int imageResourceId; // Resource ID for the item's image
    private String name; // Name of the item
    private double price; // Price of the item
    private int count; // Count of the item in the cart



    public CartItem(int imageResourceId, String name, double price, int count) {
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++; // Increase the count by 1
    }

    public void decrementCount() {
        if (count > 0) {
            count--; // Decrease the count by 1
        }
    }

    public boolean isCountZero() {
        return count == 0; // Return true if count is zero
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
