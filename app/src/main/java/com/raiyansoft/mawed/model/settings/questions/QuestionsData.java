package com.raiyansoft.mawed.model.settings.questions;

import com.google.gson.annotations.SerializedName;

public class QuestionsData {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
