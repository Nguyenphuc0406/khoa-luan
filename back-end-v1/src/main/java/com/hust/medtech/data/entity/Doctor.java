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
@Table(name = "DOCTOR")
public class Doctor {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private int doctorId;

    @Column(name = "ROOM")
    private String room;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
