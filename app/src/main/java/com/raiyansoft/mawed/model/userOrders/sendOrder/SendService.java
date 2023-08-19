package com.raiyansoft.mawed.model.userOrders.sendOrder;

import com.google.gson.annotations.SerializedName;

public class SendService {

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("price")
    private int price;

    @SerializedName("title")
    private String title;

    @SerializedName("time")
    private int time;

    public SendService() {
    }

    public SendService(int itemId, int price, String title, int time) {
        this.itemId = itemId;
        this.price = price;
        this.title = title;
        this.time = time;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
