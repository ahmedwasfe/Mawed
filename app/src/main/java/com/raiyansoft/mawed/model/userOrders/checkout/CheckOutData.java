package com.raiyansoft.mawed.model.userOrders.checkout;

import com.google.gson.annotations.SerializedName;

public class CheckOutData {

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("url")
    private String url;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
