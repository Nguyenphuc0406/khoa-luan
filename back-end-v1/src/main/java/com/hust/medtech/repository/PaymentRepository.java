package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Notify;
import com.hust.medtech.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Payment findPaymentByPaymentCode(String code);

    //    Payment findByPaymentCode
    @Query("select  p from Payment  p where p.patientPay.patientId = ?1 order by p.paymentTime desc ")
    List<Payment> _getPayTrans(int accountId);
}
