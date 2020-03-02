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



}
