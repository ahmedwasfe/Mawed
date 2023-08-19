package com.raiyansoft.mawed.model.auth.verifyOTP;

import com.google.gson.annotations.SerializedName;

public class ResendCode {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ResendCodeData resendCode;

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

    public ResendCodeData getResendCode() {
        return resendCode;
    }

    public void setResendCode(ResendCodeData resendCode) {
        this.resendCode = resendCode;
    }
}
