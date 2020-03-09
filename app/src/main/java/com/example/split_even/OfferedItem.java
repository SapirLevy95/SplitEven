package com.example.split_even;

public class OfferedItem {

    private String itemName;
    private String userEmail;

    public OfferedItem(String itemName, String userEmail) {
        this.itemName = itemName;
        this.userEmail = userEmail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
