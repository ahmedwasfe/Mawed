package com.raiyansoft.mawed.model.sections;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("banners")
    private List<Object> banners;

    @SerializedName("categories")
    private Categories categories;

    public List<Object> getBanners() {
        return banners;
    }

    public void setBanners(List<Object> banners) {
        this.banners = banners;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
