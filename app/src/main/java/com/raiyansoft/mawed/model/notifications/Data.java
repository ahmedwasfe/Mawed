package com.raiyansoft.mawed.model.notifications;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("un_read")
    private int unRead;

    @SerializedName("count_total")
    private int countTotal;

    @SerializedName("nextPageUrl")
    private Object nextPageUrl;

    @SerializedName("pages")
    private int pages;

    @SerializedName("data")
    private List<NotificationData> notifiactions;

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<NotificationData> getNotifiactions() {
        return notifiactions;
    }

    public void setNotifiactions(List<NotificationData> notifiactions) {
        this.notifiactions = notifiactions;
    }
}
