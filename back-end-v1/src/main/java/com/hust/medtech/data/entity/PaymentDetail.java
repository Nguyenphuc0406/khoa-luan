package com.hust.medtech.data.entity;

import com.hust.medtech.data.entity.key.PaymentDetailID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYMENT_DETAIL")
public class PaymentDetail {
    @EmbeddedId
    private PaymentDetailID detailID;
}
