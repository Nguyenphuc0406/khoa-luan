package com.hust.medtech.repository;

import com.hust.medtech.data.entity.TransactionMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMedicalRepository extends JpaRepository<TransactionMedical, Integer> {
}
