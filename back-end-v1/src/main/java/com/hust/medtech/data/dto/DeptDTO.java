package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptDTO {

    private Integer deptId;

    private String name;

    private String phoneNumber;

    private String address;

    private String description;

    private HospitalDTO hospital;
}