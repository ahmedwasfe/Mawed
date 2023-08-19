package com.raiyansoft.mawed.model.filter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalonData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("commercail_name")
    private String commercailName;

    @SerializedName("cityData")
    private String cityData;

    @SerializedName("image")
    private String image;

    @SerializedName("home")
    private Boolean home;

    @SerializedName("review")
    private Double review;

    @SerializedName("fav")
    private boolean fav;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCommercailName() {
        return commercailName;
    }

    public void setCommercailName(String commercailName) {
        this.commercailName = commercailName;
    }

    public String getCityData() {
        return cityData;
    }

    public void setCityData(String cityData) {
        this.cityData = cityData;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isHome() {
        return home;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
