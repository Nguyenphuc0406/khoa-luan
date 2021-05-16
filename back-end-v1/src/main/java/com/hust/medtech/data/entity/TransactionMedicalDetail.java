package com.hust.medtech.data.entity;

import com.hust.medtech.data.entity.key.PaymentDetailID;
import com.hust.medtech.data.entity.key.TransactionMedicalDetailID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "transaction_medical_detail")
public class TransactionMedicalDetail {
    @EmbeddedId
    private TransactionMedicalDetailID transDetailId;
}
