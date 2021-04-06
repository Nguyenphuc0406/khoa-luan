package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private int paymentId;

    @Column(name = "MATERIAL_COST")
    private int materialCost;

    @Column(name = "OUTPATIENT_COST")
    private int outpatientCost;
    @JoinColumn(name = "PATIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patientPay;
}
