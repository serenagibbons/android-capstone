package com.example.androidcapstone.Model;

public class Contact {


    private String name;
    private String phoneNum;
    private String userName;

    public Contact() {
        name = "John Doe";
        phoneNum = "555-555-5555";
        userName = "jdoe";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
