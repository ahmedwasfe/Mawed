package com.raiyansoft.mawed.model.settings;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettingsData implements Serializable {

    @SerializedName("title_first_interface")
    private String titleFirstInterface;

    @SerializedName("title_second_interface")
    private String titleSecondInterface;

    @SerializedName("title_third_interface")
    private String titleThirdInterface;

    @SerializedName("des_first_interface")
    private String desFirstInterface;

    @SerializedName("des_second_interface")
    private String desSecondInterface;

    @SerializedName("des_third_interface")
    private String desThirdInterface;

    @SerializedName("android_version")
    private String androidVersion;

    @SerializedName("ios_version")
    private String iosVersion;

    @SerializedName("force_update")
    private boolean forceUpdate;

    @SerializedName("force_close")
    private boolean forceClose;

    @SerializedName("whats")
    private String whats;

    @SerializedName("snap")
    private String snap;

    @SerializedName("instagram")
    private String instagram;

    @SerializedName("TikTok")
    private String tikTok;

    @SerializedName("twitter")
    private String twitter;

    @SerializedName("activation_url")
    private String activationUrl;

    @SerializedName("emails")
    private String emails;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    public String getTitleFirstInterface() {
        return titleFirstInterface;
    }

    public void setTitleFirstInterface(String titleFirstInterface) {
        this.titleFirstInterface = titleFirstInterface;
    }

    public String getTitleSecondInterface() {
        return titleSecondInterface;
    }

    public void setTitleSecondInterface(String titleSecondInterface) {
        this.titleSecondInterface = titleSecondInterface;
    }

    public String getTitleThirdInterface() {
        return titleThirdInterface;
    }

    public void setTitleThirdInterface(String titleThirdInterface) {
        this.titleThirdInterface = titleThirdInterface;
    }

    public String getDesFirstInterface() {
        return desFirstInterface;
    }

    public void setDesFirstInterface(String desFirstInterface) {
        this.desFirstInterface = desFirstInterface;
    }

    public String getDesSecondInterface() {
        return desSecondInterface;
    }

    public void setDesSecondInterface(String desSecondInterface) {
        this.desSecondInterface = desSecondInterface;
    }

    public String getDesThirdInterface() {
        return desThirdInterface;
    }

    public void setDesThirdInterface(String desThirdInterface) {
        this.desThirdInterface = desThirdInterface;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isForceClose() {
        return forceClose;
    }

    public void setForceClose(boolean forceClose) {
        this.forceClose = forceClose;
    }

    public String getWhats() {
        return whats;
    }

    public void setWhats(String whats) {
        this.whats = whats;
    }

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTikTok() {
        return tikTok;
    }

    public void setTikTok(String tikTok) {
        this.tikTok = tikTok;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
