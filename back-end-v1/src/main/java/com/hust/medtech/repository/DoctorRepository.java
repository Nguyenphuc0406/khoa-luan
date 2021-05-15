package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Account;
import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByDoctorId(int doctorId);

    Doctor findByRoom(String Room);

    Doctor findByAccount(Account account);

    List<Doctor> findByDeptDoctor(Dept dept);

    List<Doctor> findDoctorByActive(int active);
}
