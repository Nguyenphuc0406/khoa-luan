package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Patient;
import com.hust.medtech.data.entity.TransactionMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionMedicalRepository extends JpaRepository<TransactionMedical, Integer> {
    TransactionMedical findByTransId(int transId);

    TransactionMedical findByPatientAndStatus(Patient patient, int status);
}
