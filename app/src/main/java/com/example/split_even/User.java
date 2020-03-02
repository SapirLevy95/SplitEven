package com.example.split_even;

public class User {

    public String userID;
    public String email;
    public String name;
    public boolean isAdmin;

    public User(String userID, String email, String name, boolean isAdmin) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
