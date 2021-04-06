package com.hust.medtech.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOfDeptDTO {

    private int iodId;

    private String name;

    private String consultingRoom;

    private int price;

    private String description;

    private String code;

    private DeptDTO dept;

}
