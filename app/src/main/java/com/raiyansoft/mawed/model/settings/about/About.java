package com.raiyansoft.mawed.model.settings.about;

import com.google.gson.annotations.SerializedName;

public class About {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AboutData data;

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

    public AboutData getData() {
        return data;
    }

    public void setData(AboutData data) {
        this.data = data;
    }
}
