package com.hust.medtech.data.entity.response;

import com.hust.medtech.data.entity.ItemOfDept;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory {
    private String paymentCode;
    private int totalPrice;
    private int transMedicalId;
    private LocalDateTime time;
    private List<ItemOfDept> itemOfDepts;
}
