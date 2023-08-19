package com.raiyansoft.mawed.model.address;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("address")
    private String address;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("title")
    private String title;

    @SerializedName("street")
    private String street;

    @SerializedName("widget")
    private String widget;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("city")
    private City city;

    @SerializedName("governate")
    private Governate governate;

    @SerializedName("default")
    private boolean mydefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Governate getGovernate() {
        return governate;
    }

    public void setGovernate(Governate governate) {
        this.governate = governate;
    }

    public boolean isMydefault() {
        return mydefault;
    }

    public void setMydefault(boolean mydefault) {
        this.mydefault = mydefault;
    }
}
