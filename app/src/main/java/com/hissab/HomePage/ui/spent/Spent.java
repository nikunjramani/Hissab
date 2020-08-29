package com.hissab.HomePage.ui.spent;

public class Spent {
    String name,price,medicine_name,date;

    public Spent(String name, String price, String medicine_name, String date) {
        this.name = name;
        this.price = price;
        this.medicine_name = medicine_name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
