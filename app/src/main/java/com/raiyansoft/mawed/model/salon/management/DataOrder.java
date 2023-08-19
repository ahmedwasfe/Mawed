package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataOrder implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("date")
    private String date;

    @SerializedName("date_time")
    private String dateTime;

    @SerializedName("status")
    private String status;

    @SerializedName("data_pieces")
    private DataPieces dataPieces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataPieces getDataPieces() {
        return dataPieces;
    }

    public void setDataPieces(DataPieces dataPieces) {
        this.dataPieces = dataPieces;
    }
}
