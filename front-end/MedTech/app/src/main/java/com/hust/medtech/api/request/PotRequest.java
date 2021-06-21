package com.hust.medtech.api.request;

import java.util.List;

public class PotRequest {
    private int potId;
    private List<Integer> itemOfDepts;

    public int getPotId() {
        return potId;
    }

    public void setPotId(int potId) {
        this.potId = potId;
    }

    public List<Integer> getItemOfDepts() {
        return itemOfDepts;
    }

    public void setItemOfDepts(List<Integer> itemOfDepts) {
        this.itemOfDepts = itemOfDepts;
    }
}
