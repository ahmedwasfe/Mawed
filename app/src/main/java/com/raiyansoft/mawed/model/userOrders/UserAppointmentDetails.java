package com.raiyansoft.mawed.model.userOrders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAppointmentDetails implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private UserAppointmentData data;

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

    public UserAppointmentData getData() {
        return data;
    }

    public void setData(UserAppointmentData data) {
        this.data = data;
    }
}
