package com.raiyansoft.mawed.model.salon.workHour;

public class SendWorkHours {

    private int type;

    private String day;

    private int vendorId;

    private String validity;

    private String startTime1;

    private String endTime1;

    private String startTime2;

    private String endTime2;

    public SendWorkHours() {
    }

    public SendWorkHours(int type, String day, int vendorId, String validity, String startTime1, String endTime1, String startTime2, String endTime2) {
        this.type = type;
        this.day = day;
        this.vendorId = vendorId;
        this.validity = validity;
        this.startTime1 = startTime1;
        this.endTime1 = endTime1;
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }
}
