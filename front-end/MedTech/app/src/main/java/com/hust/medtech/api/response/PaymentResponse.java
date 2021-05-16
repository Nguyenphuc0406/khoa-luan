package com.hust.medtech.api.response;

import com.hust.medtech.model.Item;

import java.util.List;

public class PaymentResponse {
    private long  transMedId;
    private List<Item> itemOfDepts;

    private long  totalPrice;
    private String namePatient;

    public long getTransMedId() {
        return transMedId;
    }

    public void setTransMedId(long transMedId) {
        this.transMedId = transMedId;
    }

    public List<Item> getItemOfDepts() {
        return itemOfDepts;
    }

    public void setItemOfDepts(List<Item> itemOfDepts) {
        this.itemOfDepts = itemOfDepts;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }
}
