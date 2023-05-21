package edu.cis.Controller;

public class MenuItem {
    String name;
    String description;
    double price;
    String id;
    int amountAvailable;
    String type;
    public MenuItem(){}
    public MenuItem (String name, String description, double price, String type,String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
        this.amountAvailable = 10;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public String getId() {
        return id;
    }
    public int getAmountAvailable() {
        return amountAvailable;
    }
    public String getType() {
        return type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", id='" + id + '\'' +
                ", amountAvailable=" + amountAvailable +
                ", type='" + type + '\'' +
                '}';
    }
}