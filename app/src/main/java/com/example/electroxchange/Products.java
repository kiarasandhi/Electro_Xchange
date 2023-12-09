package com.example.electroxchange;
public class Products {
    private int productId;
    private String productName;
    private String category;
    private String description;
    private int quantity;
    private double price;

    // Constructor
    public Products(int productID, String productName, String category, String description, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    public int getProductId() {
        return productId;
    }
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