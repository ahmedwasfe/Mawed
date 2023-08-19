package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryVendor implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("price")
    private String price;

    @SerializedName("time")
    private String time;

    @SerializedName("category")
    private CategoryOrder category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CategoryOrder getCategory() {
        return category;
    }

    public void setCategory(CategoryOrder category) {
        this.category = category;
    }
}
