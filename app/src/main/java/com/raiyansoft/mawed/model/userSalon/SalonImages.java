package com.raiyansoft.mawed.model.userSalon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalonImages implements Serializable {

    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
