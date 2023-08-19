package com.raiyansoft.mawed.model.governorate;

import com.raiyansoft.mawed.model.region.RegionData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GovernorateData {

    @SerializedName("id")
    private Integer id;

    @SerializedName("city_id")
    private Integer cityId;

    @SerializedName("title")
    private String title;

    @SerializedName("regions")
    private List<RegionData> regions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RegionData> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionData> regions) {
        this.regions = regions;
    }
}
