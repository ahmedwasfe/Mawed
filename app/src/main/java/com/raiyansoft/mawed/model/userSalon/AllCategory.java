package com.raiyansoft.mawed.model.userSalon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllCategory implements Serializable {

    @SerializedName("category_employee")
    private String categoryEmpName;

    @SerializedName("data_employee")
    private List<EmployeeData> employees;

    public String getCategoryEmpName() {
        return categoryEmpName;
    }

    public void setCategoryEmpName(String categoryEmpName) {
        this.categoryEmpName = categoryEmpName;
    }

    public List<EmployeeData> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeData> employees) {
        this.employees = employees;
    }
}
