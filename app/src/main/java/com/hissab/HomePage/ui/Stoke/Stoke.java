package com.hissab.HomePage.ui.Stoke;

public class Stoke {
    String pid,quantity,sp,date,name,actual_price,selling_price;

    public Stoke(){}
    public Stoke(String pid, String quantity, String sp, String date, String name, String actual_price, String selling_price) {
        this.pid = pid;
        this.quantity = quantity;
        this.sp = sp;
        this.date = date;
        this.name = name;
        this.actual_price = actual_price;
        this.selling_price = selling_price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }
}
