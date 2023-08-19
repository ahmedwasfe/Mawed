package com.raiyansoft.mawed.model.convertSalon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalonData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("password")
    private String password;

    @SerializedName("status")
    private String status;

    @SerializedName("address")
    private String address;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("activation_code")
    private String activationCode;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("resend_code_count")
    private int resendCodeCount;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("remember_token")
    private Object rememberToken;

    @SerializedName("credit")
    private Object credit;

    @SerializedName("points")
    private Object points;

    @SerializedName("city_id")
    private Object cityId;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("region_id")
    private Object regionId;

    @SerializedName("created_at")
    private String createAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("type")
    private String type;

    @SerializedName("exp_date")
    private String expDate;

    @SerializedName("exp_date_account")
    private Object expDateAccount;

    @SerializedName("country_code")
    private Object countryCode;

    @SerializedName("days")
    private Object days;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("sex")
    private int sex;

    @SerializedName("mobile_number_new")
    private String mobileNumberNew;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("commercail_name_ar")
    private String commercailNameAr;

    @SerializedName("cat_id")
    private String catId;

    @SerializedName("type_business")
    private String typeBusiness;

    @SerializedName("mobile_number_salon")
    private String mobileNumberSalon;

    @SerializedName("whats_number")
    private String whatsNumber;

    @SerializedName("salon_id")
    private Object salonId;

    @SerializedName("description_ar")
    private String descriptionAr;

    @SerializedName("description_en")
    private String descriptionEn;

    @SerializedName("commercail_name_en")
    private String commercailNameEn;

    @SerializedName("home")
    private int home;

    @SerializedName("google_id")
    private Object googleId;

    @SerializedName("review")
    private double review;

    @SerializedName("employee_review")
    private Object employeeReview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getResendCodeCount() {
        return resendCodeCount;
    }

    public void setResendCodeCount(int resendCodeCount) {
        this.resendCodeCount = resendCodeCount;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Object getRegionId() {
        return regionId;
    }

    public void setRegionId(Object regionId) {
        this.regionId = regionId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public Object getExpDateAccount() {
        return expDateAccount;
    }

    public void setExpDateAccount(Object expDateAccount) {
        this.expDateAccount = expDateAccount;
    }

    public Object getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Object countryCode) {
        this.countryCode = countryCode;
    }

    public Object getDays() {
        return days;
    }

    public void setDays(Object days) {
        this.days = days;
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

    public String getMobileNumberNew() {
        return mobileNumberNew;
    }

    public void setMobileNumberNew(String mobileNumberNew) {
        this.mobileNumberNew = mobileNumberNew;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCommercailNameAr() {
        return commercailNameAr;
    }

    public void setCommercailNameAr(String commercailNameAr) {
        this.commercailNameAr = commercailNameAr;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getTypeBusiness() {
        return typeBusiness;
    }

    public void setTypeBusiness(String typeBusiness) {
        this.typeBusiness = typeBusiness;
    }

    public String getMobileNumberSalon() {
        return mobileNumberSalon;
    }

    public void setMobileNumberSalon(String mobileNumberSalon) {
        this.mobileNumberSalon = mobileNumberSalon;
    }

    public String getWhatsNumber() {
        return whatsNumber;
    }

    public void setWhatsNumber(String whatsNumber) {
        this.whatsNumber = whatsNumber;
    }

    public Object getSalonId() {
        return salonId;
    }

    public void setSalonId(Object salonId) {
        this.salonId = salonId;
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

    public String getCommercailNameEn() {
        return commercailNameEn;
    }

    public void setCommercailNameEn(String commercailNameEn) {
        this.commercailNameEn = commercailNameEn;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public Object getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Object googleId) {
        this.googleId = googleId;
    }

    public double getReview() {
        return review;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public Object getEmployeeReview() {
        return employeeReview;
    }

    public void setEmployeeReview(Object employeeReview) {
        this.employeeReview = employeeReview;
    }
}
