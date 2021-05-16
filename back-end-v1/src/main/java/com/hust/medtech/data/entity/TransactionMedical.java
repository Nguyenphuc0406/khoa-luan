package com.hust.medtech.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "transaction_medical")
public class TransactionMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private int transId;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;
    @OneToMany(mappedBy = "transDetailId.transactionMedical",fetch = FetchType.LAZY)
    private List<TransactionMedicalDetail> transactionMedicalDetails;

    @Column(name = "STATUS")
    // 1 - da thanh toan
    // 0 - chua thanh toan
    private Integer status;


}
