package com.buyfurn.Buyfurn.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String address;
    private String pincode;
    private String city;
    private String state;

    // Constructors, getters, setters
    public Address() {
    }

    public Address(String address, String pincode, String city, String state) {
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
