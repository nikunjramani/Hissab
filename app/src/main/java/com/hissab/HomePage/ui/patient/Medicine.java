package com.hissab.HomePage.ui.patient;

public class Medicine {
    String mid,pa_id,pid,quentity,uid,date,selling_price;

    Medicine(){

    }
    public Medicine(String date,String mid, String pa_id, String pid, String quentity,String selling_price,String uid) {
        this.mid = mid;
        this.pa_id = pa_id;
        this.pid = pid;
        this.quentity = quentity;
        this.uid = uid;
        this.date = date;
        this.selling_price=selling_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPa_id() {
        return pa_id;
    }

    public void setPa_id(String p_id) {
        this.pa_id = p_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuentity() {
        return quentity;
    }

    public void setQuentity(String quentity) {
        this.quentity = quentity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
