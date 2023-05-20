package edu.cis.Model;

import java.util.ArrayList;

public class CISUser {

    private String userID;
    private String name;
    private String yearLevel;
    private ArrayList<Order> orders;
    private double money;

    public CISUser(String userID, String name, String yearLevel) {
        this.userID = userID;
        this.name = name;
        this.yearLevel = yearLevel;
    }

    public CISUser() {
        this.userID = "";
        this.name = "";
        this.yearLevel = "";
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString(){
        return userID + " " + name + " " + yearLevel + " " + orders + " " + money;
    }

    public void addOrder(Order order) {
    }

    public void deleteOrder(Order order) {
    }
}