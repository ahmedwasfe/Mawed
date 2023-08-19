package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable {


    @SerializedName("id")
    private String id;

    @SerializedName("categoryVendor")
    private CategoryVendor categoryVendor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoryVendor getCategoryVendor() {
        return categoryVendor;
    }

    public void setCategoryVendor(CategoryVendor categoryVendor) {
        this.categoryVendor = categoryVendor;
    }
}
