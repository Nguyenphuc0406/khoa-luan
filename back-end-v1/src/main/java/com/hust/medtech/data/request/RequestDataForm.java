package com.hust.medtech.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataForm {
    //    private int username;
//    private int age;
    private List<Integer> iodId;
    private String description;
}
