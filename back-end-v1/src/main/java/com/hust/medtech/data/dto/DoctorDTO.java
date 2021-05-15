package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DoctorDTO {

    private int doctorId;

    private String room;

    private int active;

    private AccountDTO account;

    private DeptDTO dept;
}
