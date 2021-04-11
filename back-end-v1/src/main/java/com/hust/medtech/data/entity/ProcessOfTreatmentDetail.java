package com.hust.medtech.data.entity;

import com.hust.medtech.data.entity.key.ProcessOfTreatmentDetailID;
import com.hust.medtech.data.entity.key.TransactionMedicalDetailID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "process_of_treatment_detail")
public class ProcessOfTreatmentDetail {
    @EmbeddedId
    private ProcessOfTreatmentDetailID processDetailId;
}
