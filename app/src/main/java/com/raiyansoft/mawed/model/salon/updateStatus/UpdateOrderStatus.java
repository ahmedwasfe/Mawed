package com.raiyansoft.mawed.model.salon.updateStatus;

import com.google.gson.annotations.SerializedName;

public class UpdateOrderStatus {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private UpdateOrderStatusData data;

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

    public UpdateOrderStatusData getData() {
        return data;
    }

    public void setData(UpdateOrderStatusData data) {
        this.data = data;
    }
}
