package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "PAYMENT_ID")
    @GeneratedValue( generator = "uuid2" )
    @GenericGenerator( name = "uuid2", strategy = "uuid2" )
    private String paymentId;

    @Column(name = "OUTPATIENT_COST")
    private int outpatientCost;

    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @JoinColumn(name = "PATIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patientPay;

    @OneToOne
    @JoinColumn(name = "TRANS_MEDICAL_ID")
    private TransactionMedical transactionMedical;
}
