package com.example.electroxchange;
public class Product {
    private int productId;
    private String productName;
    private String category;
    private String description;
    private int quantity;
    private double price;

    // Constructor
    public Product(String productName, String category, String description, int quantity, double price) {
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters for product properties
    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
