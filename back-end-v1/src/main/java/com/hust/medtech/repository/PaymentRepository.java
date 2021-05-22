package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Payment findPaymentByPaymentCode(String code);
//    Payment findByPaymentCode

}
