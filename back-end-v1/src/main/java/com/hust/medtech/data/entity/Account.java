package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private int accountId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate DOB;

    @Column(name = "JOB")
    private String job;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "identity_card")
    private String identityCard;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "CODE")
    private String code;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Patient patient;


}
