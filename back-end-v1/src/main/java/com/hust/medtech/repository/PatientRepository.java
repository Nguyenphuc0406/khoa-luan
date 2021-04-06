package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer > {
    Patient findByPatientId(int patientId);
}
