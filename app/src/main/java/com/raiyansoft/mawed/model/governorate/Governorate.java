package com.raiyansoft.mawed.model.governorate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Governorate {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<GovernorateData> governorates;

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

    public List<GovernorateData> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(List<GovernorateData> governorates) {
        this.governorates = governorates;
    }
}
