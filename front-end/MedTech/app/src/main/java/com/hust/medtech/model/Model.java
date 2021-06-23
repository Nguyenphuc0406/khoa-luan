package com.hust.medtech.model;

import com.google.gson.annotations.Expose;

public class Model {
    @Expose
    private String deptRoom;
    @Expose
    private String doctorName;
    @Expose
    private String doctorPhone;
    @Expose
    private String index;
    @Expose
    private String totalIndex;
    @Expose
    private String deptName;
    @Expose
    private int potId;
    @Expose
    private String patientName;
    @Expose
    private String patientAge;
    @Expose
    private String description;
    @Expose
    private String gender;
    @Expose
    private String name;
    @Expose
    private String consultingRoom;
    @Expose
    private String content;
    @Expose
    private String title;
    @Expose
    private String paymentCode;
    @Expose
    private String paymentDate;
    @Expose
    private int status;
    @Expose
    private int isDoctorAccepted;
    @Expose
    private int deptId;
    @Expose
    private int type;
    @Expose
    private int totalPrice;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsultingRoom() {
        return consultingRoom;
    }

    public void setConsultingRoom(String consultingRoom) {
        this.consultingRoom = consultingRoom;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getIsDoctorAccepted() {
        return isDoctorAccepted;
    }

    public void setIsDoctorAccepted(int isDoctorAccepted) {
        this.isDoctorAccepted = isDoctorAccepted;
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

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptRoom() {
        return deptRoom;
    }

    public void setDeptRoom(String deptRoom) {
        this.deptRoom = deptRoom;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTotalIndex() {
        return totalIndex;
    }

    public void setTotalIndex(String totalIndex) {
        this.totalIndex = totalIndex;
    }
}
