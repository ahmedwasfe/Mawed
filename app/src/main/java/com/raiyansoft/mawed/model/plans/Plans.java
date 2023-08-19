package com.raiyansoft.mawed.model.plans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Plans {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<PlanData> packages;

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

    public List<PlanData> getPackages() {
        return packages;
    }

    public void setPackages(List<PlanData> packages) {
        this.packages = packages;
    }
}
