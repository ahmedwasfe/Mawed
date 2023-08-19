package com.raiyansoft.mawed.model.address;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Governate implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("governorat_id")
    private int governoratId;

    @SerializedName("title_ar")
    private String titleAr;

    @SerializedName("title_en")
    private String titleEn;

    @SerializedName("delivery_cost")
    private int deliveryCost;

    @SerializedName("far_zone")
    private int farZone;

    @SerializedName("order_limit")
    private int orderLimit;

    @SerializedName("status")
    private int status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private Object deletedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGovernoratId() {
        return governoratId;
    }

    public void setGovernoratId(int governoratId) {
        this.governoratId = governoratId;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getFarZone() {
        return farZone;
    }

    public void setFarZone(int farZone) {
        this.farZone = farZone;
    }

    public int getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(int orderLimit) {
        this.orderLimit = orderLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
}
