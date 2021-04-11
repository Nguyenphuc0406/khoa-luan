package com.hust.medtech.repository;

import com.hust.medtech.data.entity.ProcessOfTreatmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POTDetailRepository extends JpaRepository<ProcessOfTreatmentDetail, Integer> {
}
