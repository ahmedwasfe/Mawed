package com.raiyansoft.mawed.model.auth.register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Register implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private RegisterData user;

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

    public RegisterData getUser() {
        return user;
    }

    public void setUser(RegisterData user) {
        this.user = user;
    }
}
