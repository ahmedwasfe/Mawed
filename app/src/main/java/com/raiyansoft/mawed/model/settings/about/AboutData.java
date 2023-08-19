package com.raiyansoft.mawed.model.settings.about;

import com.google.gson.annotations.SerializedName;

public class AboutData {

    @SerializedName("about")
    private String about;

    @SerializedName("privacy")
    private String privacy;

    @SerializedName("conditions")
    private String conditions;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
