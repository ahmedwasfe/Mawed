package com.raiyansoft.mawed.model.userOrders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("count_total")
    private int countTotal;

    @SerializedName("nextPageUrl")
    private Object nextPageUrl;

    @SerializedName("pages")
    private int pages;

    @SerializedName("data")
    private List<UserOrdersData> orders;

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<UserOrdersData> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrdersData> orders) {
        this.orders = orders;
    }
}
