package com.raiyansoft.mawed.model.auth.register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterData implements Serializable {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("token")
    private String token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
