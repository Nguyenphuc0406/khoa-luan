package com.hust.medtech.data.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.hust.medtech.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AccountDTO {
    private int accountId;

    private String username;

    private String password;

    private String fullName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate DOB;

    private String job;

    private String nationality;

    private String phoneNumber;

    private String identityCard;

    private String role;

    private String code;

    private String address;

    public AccountDTO(Account accountDTO) {
        accountId = accountDTO.getAccountId();
        username = accountDTO.getUsername();
        fullName = accountDTO.getFullName();
        DOB = accountDTO.getDOB();
        phoneNumber = accountDTO.getPhoneNumber();
        job = accountDTO.getJob();
        nationality = accountDTO.getNationality();
        identityCard = accountDTO.getIdentityCard();
        role = accountDTO.getRole();
        code = accountDTO.getCode();
        address = accountDTO.getPatient().getPermanentAddress();
    }
}