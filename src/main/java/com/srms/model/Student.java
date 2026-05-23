package com.srms.model;

import java.util.Collections;
import java.util.List;

public class Student extends Person implements Reportable {
    private int userId;
    private String department;
    private int semester;
    private String phone;
    private String dob;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String getRole() {
        return "student";
    }

    @Override
    public List<Result> generateReport() {
        return Collections.emptyList();
    }
}
