package com.raiyansoft.mawed.model.salon.services.save;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveServiceData implements Serializable {

    @SerializedName("vendor_id")
    private int vendorId;

    @SerializedName("cat_id")
    private String catId;

    @SerializedName("time")
    private String time;

    @SerializedName("price")
    private String price;

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
