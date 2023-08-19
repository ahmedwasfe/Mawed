package com.raiyansoft.mawed.model.salon.sales;

import com.raiyansoft.mawed.model.userSalon.EmployeeData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalesData implements Serializable {

    @SerializedName("total_cost")
    private int totalCost;

    @SerializedName("data")
    private List<EmployeeData> employees;

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<EmployeeData> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeData> employees) {
        this.employees = employees;
    }
}
