package com.plainpixel.finalapplication;

public class User {

    private String FullName, ContactEmail, ContactNumber, Balance, Level;

    public User(){

    }

    public User(String fullName, String contactEmail, String contactNumber, String balance, String level) {
        FullName = fullName;
        ContactEmail = contactEmail;
        ContactNumber = contactNumber;
        Balance = balance;
        Level = level;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
