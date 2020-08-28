package com.hissab.HomePage.ui.patient;

public class Patient {
    String pa_id,uid,name,number,age,address;

    public Patient(){

    }
    public Patient(String address,String age,String name,String number,String pa_id,String uid) {
        this.pa_id = pa_id;
        this.uid = uid;
        this.name = name;
        this.number = number;
        this.age = age;
        this.address = address;
    }

    public String getPa_id() {
        return pa_id;
    }

    public void setPa_id(String pid) {
        this.pa_id = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
