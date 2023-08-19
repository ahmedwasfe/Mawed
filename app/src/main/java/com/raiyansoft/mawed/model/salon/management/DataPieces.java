package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataPieces implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("price")
    private String price;

    @SerializedName("time")
    private String time;

    @SerializedName("service")
    private Service service;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
