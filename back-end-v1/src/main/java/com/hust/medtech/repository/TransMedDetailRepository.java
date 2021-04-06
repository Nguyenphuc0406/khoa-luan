package com.hust.medtech.repository;

import com.hust.medtech.data.entity.TransactionMedicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransMedDetailRepository extends JpaRepository<TransactionMedicalDetail, Integer> {
}
