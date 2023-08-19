package com.raiyansoft.mawed.model.auth.verifyOTP;

import com.google.gson.annotations.SerializedName;

public class ResendCodeData {

    @SerializedName("resend_code_count")
    private int resendCodeCount;

    public int getResendCodeCount() {
        return resendCodeCount;
    }

    public void setResendCodeCount(int resendCodeCount) {
        this.resendCodeCount = resendCodeCount;
    }
}
