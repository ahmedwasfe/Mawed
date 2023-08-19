package com.raiyansoft.mawed.model.payment.paymentStatus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentStatus implements Serializable {

    @SerializedName("status")
    public boolean status;

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public PaymentStatusData data;

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

    public PaymentStatusData getData() {
        return data;
    }

    public void setData(PaymentStatusData data) {
        this.data = data;
    }
}
