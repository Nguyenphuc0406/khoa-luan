package com.hust.medtech.repository;

import com.hust.medtech.data.entity.ProcessOfTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POTRepository extends JpaRepository<ProcessOfTreatment, Integer> {
}
