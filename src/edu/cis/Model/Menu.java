package edu.cis.Model;

import java.util.ArrayList;

public class Menu {

    private ArrayList<MenuItem> eatriumItems;
    private String adminID;

    public Menu(){
        this.eatriumItems = new ArrayList<MenuItem>();
        this.adminID = "";
    }

    public ArrayList<MenuItem> getEatriumItems() {
        return eatriumItems;
    }

    public void setEatriumItems(ArrayList<MenuItem> eatriumItems) {
        this.eatriumItems = eatriumItems;
    }

    public void addEatriumItems(MenuItem item){
        this.eatriumItems.add(item);
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    @Override
    public String toString(){
        return eatriumItems + " " + adminID;
    }
}
