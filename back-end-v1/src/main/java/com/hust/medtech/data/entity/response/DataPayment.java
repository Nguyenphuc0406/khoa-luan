package com.hust.medtech.data.entity.response;

import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.entity.ItemOfDept;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPayment {

    private Integer transMedId;
    private List<ItemOfDeptDTO> itemOfDepts;
    private Integer totalPrice;
    private String namePatient;
//    private Integer outpatientCost;

}
