package com.hust.medtech.data.request;

import com.hust.medtech.data.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataTransaction {
    private PatientDTO patientDTO;
    private List<Integer> itemOfDeptId;
}
