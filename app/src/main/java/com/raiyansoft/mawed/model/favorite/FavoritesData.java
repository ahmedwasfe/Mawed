package com.raiyansoft.mawed.model.favorite;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FavoritesData implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("commercail_name")
    private String commercailName;

    @SerializedName("fav")
    private boolean fav;

    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCommercailName() {
        return commercailName;
    }

    public void setCommercailName(String commercailName) {
        this.commercailName = commercailName;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
