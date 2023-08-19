package com.raiyansoft.mawed.model.salon.services.save;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveService implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private SaveServiceData service;

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

    public SaveServiceData getService() {
        return service;
    }

    public void setService(SaveServiceData service) {
        this.service = service;
    }
}
