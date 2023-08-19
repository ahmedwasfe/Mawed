package com.raiyansoft.mawed.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ad implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("layout")
    private int layout;

    @SerializedName("lauout_title")
    private String lauoutTitle;

    @SerializedName("days")
    private int days;

    @SerializedName("status")
    private int status;

    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public String getLauoutTitle() {
        return lauoutTitle;
    }

    public void setLauoutTitle(String lauoutTitle) {
        this.lauoutTitle = lauoutTitle;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
