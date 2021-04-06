package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByDoctorId(int doctorId);

    Doctor findByRoom(String Room);



}
