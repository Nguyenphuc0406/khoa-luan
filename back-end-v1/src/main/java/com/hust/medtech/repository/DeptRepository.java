package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Dept;

import com.hust.medtech.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {
    Dept findByName(String deptName);

    Dept findByDeptId(int deptId);



}
