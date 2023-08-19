package com.raiyansoft.mawed.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeOrders implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("date_time")
    private String dateTime;

    @SerializedName("salon_name")
    private String salonName;

    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
