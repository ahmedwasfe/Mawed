package com.raiyansoft.mawed.model.address;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Addresses implements Serializable {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private AddressesData addressesData;

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

    public AddressesData getAddressesData() {
        return addressesData;
    }

    public void setAddressesData(AddressesData addressesData) {
        this.addressesData = addressesData;
    }
}
