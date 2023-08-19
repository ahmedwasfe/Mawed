package com.raiyansoft.mawed.model.userOrders.checkout;

import com.google.gson.annotations.SerializedName;

public class CheckOut {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private CheckOutData data;

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

    public CheckOutData getData() {
        return data;
    }

    public void setData(CheckOutData data) {
        this.data = data;
    }
}
