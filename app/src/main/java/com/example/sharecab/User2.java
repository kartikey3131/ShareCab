package com.example.sharecab;

public class User2 {
    String name;
    String contact;
    String carNumber;
    String model;
    String price;
    String type;
    String availability;
    String uid;
    public User2(){

    }

    public User2(String name, String contact, String carNumber, String model, String price,String type,String availability,String uid) {
        this.name = name;
        this.contact = contact;
        this.carNumber = carNumber;
        this.model = model;
        this.price = price;
        this.type =type;
        this.availability = availability;
        this.uid=uid;
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

    public String getContact() {
        return contact;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
