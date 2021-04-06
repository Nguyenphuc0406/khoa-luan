package com.hust.medtech.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TransactionMedicalDTO {
    private Integer transId;
    private Date createDate;
    private AccountDTO account;

    private List<Integer> itemOfDepts = new ArrayList<>();
//    private List<TransactionMedicalDetailDTO> transMedDetails;

    public TransactionMedicalDTO() {
    }

    public TransactionMedicalDTO(Integer transId, Date createDate, AccountDTO account, List<Integer> itemOfDepts) {
        this.transId = transId;
        this.createDate = createDate;
        this.account = account;
        this.itemOfDepts = itemOfDepts;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public List<Integer> getItemOfDepts() {
        return itemOfDepts;
    }

    public void setItemOfDepts(List<Integer> itemOfDepts) {
        this.itemOfDepts = itemOfDepts;
    }
}
