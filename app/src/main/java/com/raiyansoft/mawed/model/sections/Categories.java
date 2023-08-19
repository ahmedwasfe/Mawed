package com.raiyansoft.mawed.model.sections;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable {

    @SerializedName("count_total")
    private int countTotal;

    @SerializedName("nextPageUrl")
    private Object nextPageUrl;

    @SerializedName("pages")
    private int pages;

    @SerializedName("data")
    private List<SectionData> sections;

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

    public List<SectionData> getSections() {
        return sections;
    }

    public void setSections(List<SectionData> sections) {
        this.sections = sections;
    }
}
