package com.raiyansoft.mawed.model.userOrders;

import com.raiyansoft.mawed.model.userOrders.coupon.PromoCode;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserAppointmentData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("status")
    private String status;

    @SerializedName("salon")
    private String salon;

    @SerializedName("employee")
    private String employee;

    @SerializedName("call_number")
    private String callNumber;

    @SerializedName("payment")
    private String payment;

    @SerializedName("promo_code")
    private PromoCode promoCode;

    @SerializedName("promo_code_type")
    private int promoCodeType;

    @SerializedName("discount")
    private String discount;

    @SerializedName("total")
    private String total;

    @SerializedName("booking_date")
    private String bookingDate;

    @SerializedName("booking_time")
    private String bookingTime;

    @SerializedName("address_id")
    private int addressId;

    @SerializedName("notes")
    private String notes;

    @SerializedName("services")
    private List<UserAppointmentService> services;

    @SerializedName("address")
    private UserAppointmentAddress address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }

    public int getPromoCodeType() {
        return promoCodeType;
    }

    public void setPromoCodeType(int promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<UserAppointmentService> getServices() {
        return services;
    }

    public void setServices(List<UserAppointmentService> services) {
        this.services = services;
    }

    public UserAppointmentAddress getAddress() {
        return address;
    }

    public void setAddress(UserAppointmentAddress address) {
        this.address = address;
    }
}
