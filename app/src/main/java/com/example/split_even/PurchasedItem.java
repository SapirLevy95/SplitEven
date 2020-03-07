package com.example.split_even;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PurchasedItem {

    private String itemName;
    private double price;
    private String date;
    private String userEmail;

    public PurchasedItem(String itemName, double price, String userEmail) {
        this.itemName = itemName;
        this.price = price;
        this.userEmail = userEmail;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        date = formatter.format(calendar.getTime());
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
