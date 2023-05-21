package edu.cis.Controller;

import java.util.ArrayList;

public class Menu {
    ArrayList<MenuItem> eatriumItems;

    String adminID;
    public Menu(String adminID) {
        eatriumItems = new ArrayList<MenuItem>();
        this.adminID = adminID;
    }

    public ArrayList<MenuItem> getEatriumItems() {
        return eatriumItems;
    }
    public String getAdminID() {
        return adminID;
    }
    public void setEatriumItems(ArrayList<MenuItem> eatriumItems) {
        this.eatriumItems = eatriumItems;
    }
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
    public void addEatriumItems(MenuItem newItem){
        eatriumItems.add(newItem);
    }
    public void deleteEatriumItem(MenuItem item) {
        eatriumItems.remove(item);
    }
    @Override
    public String toString() {
        return "Menu{" +
                "eatriumItems=" + eatriumItems +
                ", adminID='" + adminID + '\'' +
                '}';
    }
}