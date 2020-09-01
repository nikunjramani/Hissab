package com.hissab.HomePage.ui.product;

public class Product {
    public String pid,name,actualPrice,sellingPrice,quentity,date,sp;

    public Product(){}
    public Product(String actualPrice, String date, String name, String pid, String quentity,String sp, String sellingPrice) {
        this.pid = pid;
        this.sp=sp;
        this.name = name;
        this.actualPrice = actualPrice;
        this.sellingPrice = sellingPrice;
        this.quentity = quentity;
        this.date = date;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
