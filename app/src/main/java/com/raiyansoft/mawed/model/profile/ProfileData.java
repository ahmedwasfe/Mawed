package com.raiyansoft.mawed.model.profile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileData implements Serializable {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("salon_name")
    private String salonName;

    @SerializedName("commercail_name_en")
    private String commercailNameEn;

    @SerializedName("commercail_name_ar")
    private String commercailNameAr;

    @SerializedName("description_ar")
    private String descriptionAr;

    @SerializedName("description_en")
    private String descriptionEn;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("sex")
    private int sex;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private String type;

    @SerializedName("home")
    private int home;

    @SerializedName("status")
    private String status;

    @SerializedName("exp_date")
    private String expDate;

    @SerializedName("days")
    private String days;

    @SerializedName("fee")
    private int fee;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getCommercailNameEn() {
        return commercailNameEn;
    }

    public void setCommercailNameEn(String commercailNameEn) {
        this.commercailNameEn = commercailNameEn;
    }

    public String getCommercailNameAr() {
        return commercailNameAr;
    }

    public void setCommercailNameAr(String commercailNameAr) {
        this.commercailNameAr = commercailNameAr;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
