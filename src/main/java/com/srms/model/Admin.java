package com.srms.model;

public class Admin extends Person {
    @Override
    public String getRole() {
        return "admin";
    }
}
