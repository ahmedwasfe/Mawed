package com.raiyansoft.mawed.model.plans.orderPlan;

import com.google.gson.annotations.SerializedName;

public class OrderPlanData {

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
