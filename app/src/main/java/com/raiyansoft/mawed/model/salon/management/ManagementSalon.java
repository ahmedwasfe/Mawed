package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ManagementSalon implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ManagementSalonData> data;

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

    public List<ManagementSalonData> getData() {
        return data;
    }

    public void setData(List<ManagementSalonData> data) {
        this.data = data;
    }
}
