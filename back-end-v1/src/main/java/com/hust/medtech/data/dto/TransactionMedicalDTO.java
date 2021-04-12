package com.hust.medtech.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMedicalDTO {
    private Integer transId;
    private Date createDate;
    //    private AccountDTO account;
    private String patientName;
//    private String doctorName;
    private List<Integer> itemOfDepts = new ArrayList<>();
//    private List<TransactionMedicalDetailDTO> transMedDetails;


}
