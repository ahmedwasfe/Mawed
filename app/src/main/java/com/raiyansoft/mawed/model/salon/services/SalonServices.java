package com.raiyansoft.mawed.model.salon.services;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalonServices implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<SalonServicesData> services;

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

    public List<SalonServicesData> getServices() {
        return services;
    }

    public void setServices(List<SalonServicesData> services) {
        this.services = services;
    }
}
