package edu.cis.Controller;
import java.util.ArrayList;

public class CISUser {
    String userID;
    String name;
    String yearLevel;
    double money;
    ArrayList<Order> orders;
    public CISUser(){
        orders = new ArrayList<Order>();
    }
    public CISUser (String userID, String name, String yearLevel) {
        this.userID = userID;
        this.name = name;
        this.yearLevel = yearLevel;
        money = 50.0;
        this.orders = new ArrayList<Order>();
    }
    public String getUserID(){
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getYearLevel() {
        return yearLevel;
    }
    public double getMoney() {
        return money;
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    public void addOrder(Order order){
        orders.add(order);
    }

    public void deleteOrder(Order order){
        orders.remove(order);
    }
    @Override
    public String toString() {
        return "CISUser{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", yearLevel='" + yearLevel + '\'' +
                ", money=" + money +
                ", orders=" + orders +
                '}';
    }
}