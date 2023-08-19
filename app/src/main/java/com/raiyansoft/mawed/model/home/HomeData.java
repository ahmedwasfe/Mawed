package com.raiyansoft.mawed.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeData implements Serializable {

    @SerializedName("ads")
    public List<Ad> ads;

    @SerializedName("services")
    public List<HomeService> services;

    @SerializedName("governorates")
    public List<HomeGovernorate> governorates;

    @SerializedName("appUsers")
    public List<AppUser> appUsers;

    @SerializedName("orders")
    public List<HomeOrders> orders;

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<HomeService> getServices() {
        return services;
    }

    public void setServices(List<HomeService> services) {
        this.services = services;
    }

    public List<HomeGovernorate> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(List<HomeGovernorate> governorates) {
        this.governorates = governorates;
    }

    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public List<HomeOrders> getOrders() {
        return orders;
    }

    public void setOrders(List<HomeOrders> orders) {
        this.orders = orders;
    }
}
