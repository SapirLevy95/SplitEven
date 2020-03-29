package com.example.split_even;

public class PurchasedItem {

    private String itemName;
    private double price;
    private String userEmail;

    public PurchasedItem() { }

    public PurchasedItem(String itemName, double price, String userEmail) {
        this.itemName = itemName;
        this.price = price;
        this.userEmail = userEmail;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
