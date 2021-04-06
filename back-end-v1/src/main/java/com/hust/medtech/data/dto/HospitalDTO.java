package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {

    private Integer hospitalId;

    private String name;

    private String address;

    private String phoneNumber;
}
