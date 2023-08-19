package com.raiyansoft.mawed.model.userSalon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalonDetails implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private SalonDetailsData salonDetails;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SalonDetailsData getSalonDetails() {
        return salonDetails;
    }

    public void setSalonDetails(SalonDetailsData salonDetails) {
        this.salonDetails = salonDetails;
    }
}