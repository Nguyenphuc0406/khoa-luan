package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessOfTreatmentDTO {
    private Integer potId;
    private String description;
    private List<Integer> depts = new ArrayList<>();
}
