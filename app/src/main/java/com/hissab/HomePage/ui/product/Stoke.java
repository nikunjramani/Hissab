package com.hissab.HomePage.ui.product;

public class Stoke {
    String sid,pid,quantity,sp,date;

    public Stoke(){}
    public Stoke(String sid,String pid, String quantity, String sp, String date) {
        this.pid = pid;
        this.sid=sid;
        this.quantity = quantity;
        this.sp = sp;
        this.date = date;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
}
