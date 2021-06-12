package com.hust.medtech.data.entity.response;

import com.hust.medtech.data.dto.DeptDTO;
import com.hust.medtech.data.dto.DoctorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDataPOT {
//    private Lis;
    private String deptRoom;
    private String doctorName;
    private String doctorPhone;
    private String deptName;
    private int index;
    private int totalIndex;

}
