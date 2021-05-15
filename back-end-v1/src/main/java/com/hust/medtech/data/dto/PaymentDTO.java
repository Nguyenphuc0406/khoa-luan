package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer paymentId;
    private int outpatientCost;
    private int totalPrice;
    private Integer patientId;
    private Integer transMedicalId;

}
