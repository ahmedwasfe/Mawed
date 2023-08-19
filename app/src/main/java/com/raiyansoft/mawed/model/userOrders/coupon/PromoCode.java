package com.raiyansoft.mawed.model.userOrders.coupon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PromoCode implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("discount")
    private String discount;

    @SerializedName("count_number")
    private int countNumber;

    @SerializedName("end_at")
    private String end_at;

    @SerializedName("type")
    private int type;

    @SerializedName("percent")
    private int percent;

    @SerializedName("use_number")
    private int useNumber;

    @SerializedName("code_limit")
    private int codeLimit;

    @SerializedName("code_max")
    private int codeMax;

    @SerializedName("status")
    private int status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(int useNumber) {
        this.useNumber = useNumber;
    }

    public int getCodeLimit() {
        return codeLimit;
    }

    public void setCodeLimit(int codeLimit) {
        this.codeLimit = codeLimit;
    }

    public int getCodeMax() {
        return codeMax;
    }

    public void setCodeMax(int codeMax) {
        this.codeMax = codeMax;
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
}
