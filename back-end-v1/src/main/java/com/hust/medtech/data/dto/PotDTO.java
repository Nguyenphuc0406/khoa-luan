package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



public class PotDTO {
    private int potId;
    private String patientName;
    private int patientAge;
    private String description;
    private String gender;
    private int index;
    private int status;

    public PotDTO(int potId, String patientName, int patientAge, String description, int index, int status, String gender) {
        this.potId = potId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.description = description;
        this.index = index;
        this.status = status;
        this.gender =gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPotId() {
        return potId;
    }

    public void setPotId(int potId) {
        this.potId = potId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
