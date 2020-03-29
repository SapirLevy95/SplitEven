package com.example.split_even;

public class UserExpenses {

    private String userName;
    private double expenses;

    public UserExpenses(String userName) {
        this.userName = userName;
        this.expenses = 0;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
