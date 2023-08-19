package com.raiyansoft.mawed.model.userOrders.coupon;

import com.google.gson.annotations.SerializedName;

public class CheckCoupon {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private CheckCouponData data;

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

    public CheckCouponData getData() {
        return data;
    }

    public void setData(CheckCouponData data) {
        this.data = data;
    }
}
