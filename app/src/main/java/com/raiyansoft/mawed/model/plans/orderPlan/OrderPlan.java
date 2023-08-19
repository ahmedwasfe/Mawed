package com.raiyansoft.mawed.model.plans.orderPlan;

import com.google.gson.annotations.SerializedName;

public class OrderPlan {

    @SerializedName("status")
    private boolean status;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private OrderPlanData orderPlan;

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

    public OrderPlanData getOrderPlan() {
        return orderPlan;
    }

    public void setOrderPlan(OrderPlanData orderPlan) {
        this.orderPlan = orderPlan;
    }
}
