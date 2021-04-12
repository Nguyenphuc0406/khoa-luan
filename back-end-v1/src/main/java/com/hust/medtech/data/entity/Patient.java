package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private int patientId;

    @Column(name = "AGE")
    private int age;

    @Column(name = "PERMANENT_ADDRESS")
    private String permanentAddress;
    @OneToMany(mappedBy = "patientPay", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "patientPot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProcessOfTreatment> process = new ArrayList<>();

    @Column(name = "ACCOUNT_ID")
    private int accountId;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionMedical> transactionMedicals = new ArrayList<>();

}
