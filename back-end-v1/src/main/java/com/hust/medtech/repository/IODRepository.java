package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.ItemOfDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IODRepository extends JpaRepository<ItemOfDept, Integer> {

    ItemOfDept findByCode(String code);

    ItemOfDept findByName(String name);

    List<ItemOfDept> findByDept(Dept dept);


}
