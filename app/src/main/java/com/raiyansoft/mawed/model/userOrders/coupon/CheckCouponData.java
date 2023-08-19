package com.raiyansoft.mawed.model.userOrders.coupon;

import com.google.gson.annotations.SerializedName;

public class CheckCouponData {

    @SerializedName("code")
    private String code;

    @SerializedName("code_limit")
    private int codeLimit;

    @SerializedName("code_max")
    private int codeMax;

    @SerializedName("type")
    private int type;

    @SerializedName("discount_type")
    private String discountType;

    @SerializedName("discount")
    private String discount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
