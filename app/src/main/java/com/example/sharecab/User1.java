package com.example.sharecab;

public class User1 {
    String name;
    String contact;
    String type;
    String uid;
    public User1(){

    }

    public User1(String name, String contact,String type,String uid) {
        this.name = name;
        this.contact = contact;
        this.type =type;
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

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
