package com.raiyansoft.mawed.model.userOrders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAppointmentAddress implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("address")
    private Object address;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("title")
    private Object title;

    @SerializedName("street")
    private String street;

    @SerializedName("block")
    private Object block;

    @SerializedName("widget")
    private String widget;

    @SerializedName("house_number")
    private String houseNumber;

    @SerializedName("governate")
    private String governate;

    @SerializedName("region")
    private String region;

    @SerializedName("notes")
    private Object notes;

    @SerializedName("governate_id")
    private int governateId;

    @SerializedName("region_id")
    private int regionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
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

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getBlock() {
        return block;
    }

    public void setBlock(Object block) {
        this.block = block;
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

    public String getGovernate() {
        return governate;
    }

    public void setGovernate(String governate) {
        this.governate = governate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public int getGovernateId() {
        return governateId;
    }

    public void setGovernateId(int governateId) {
        this.governateId = governateId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
