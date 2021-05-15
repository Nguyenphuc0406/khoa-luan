package com.hust.medtech.api.request;

import java.util.List;

public class RegisterMedRequest {
    private String description;
    private List<Integer> depts;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getDepts() {
        return depts;
    }

    public void setDepts(List<Integer> depts) {
        this.depts = depts;
    }
}
