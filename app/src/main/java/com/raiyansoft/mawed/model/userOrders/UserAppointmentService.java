package com.raiyansoft.mawed.model.userOrders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAppointmentService implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
