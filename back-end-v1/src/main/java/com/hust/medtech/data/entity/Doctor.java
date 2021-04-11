package com.hust.medtech.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private int doctorId;

    @Column(name = "ROOM")
    private String room;

    @Column(name = "ACTIVE")
    private int active; // 1 : acctive - 0 : nonActive

    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEPT_ID")
    private Dept deptDoctor;
}
