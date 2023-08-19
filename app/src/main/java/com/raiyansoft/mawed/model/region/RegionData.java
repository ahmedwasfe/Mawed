package com.raiyansoft.mawed.model.region;

import com.google.gson.annotations.SerializedName;

public class RegionData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("region_id")
    private Integer regionId;

    @SerializedName("title")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
