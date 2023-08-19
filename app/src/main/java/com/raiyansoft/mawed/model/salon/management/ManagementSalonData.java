package com.raiyansoft.mawed.model.salon.management;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ManagementSalonData implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("data_order")
    private List<DataOrder> dataOrders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<DataOrder> getDataOrders() {
        return dataOrders;
    }

    public void setDataOrders(List<DataOrder> dataOrders) {
        this.dataOrders = dataOrders;
    }
}
