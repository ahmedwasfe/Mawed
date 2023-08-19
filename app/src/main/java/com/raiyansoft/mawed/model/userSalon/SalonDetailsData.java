package com.raiyansoft.mawed.model.userSalon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalonDetailsData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("commercail_name")
    private String commercailName;

    @SerializedName("description")
    private String description;

    @SerializedName("review")
    private double review;

    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;

    @SerializedName("all")
    private List<AllCategory> allCategories;

    @SerializedName("cat")
    private List<SalonCategory> categories;

    @SerializedName("employee")
    private List<EmployeeData> employees;

    @SerializedName("images")
    private List<SalonImages> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommercailName() {
        return commercailName;
    }

    public void setCommercailName(String commercailName) {
        this.commercailName = commercailName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getReview() {
        return review;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<AllCategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<AllCategory> allCategories) {
        this.allCategories = allCategories;
    }

    public List<SalonCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<SalonCategory> categories) {
        this.categories = categories;
    }

    public List<EmployeeData> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeData> employees) {
        this.employees = employees;
    }

    public List<SalonImages> getImages() {
        return images;
    }

    public void setImages(List<SalonImages> images) {
        this.images = images;
    }
}
