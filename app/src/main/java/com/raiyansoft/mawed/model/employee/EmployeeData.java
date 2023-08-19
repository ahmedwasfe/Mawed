package com.raiyansoft.mawed.model.employee;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EmployeeData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("review")
    private double review;

    @SerializedName("home")
    private int home;

    @SerializedName("image")
    private String images;

    @SerializedName("time")
    private List<String> times;

    @SerializedName("cat")
    private List<EmployeeDataCat> cat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<EmployeeDataCat> getCat() {
        return cat;
    }

    public void setCat(List<EmployeeDataCat> cat) {
        this.cat = cat;
    }
}
