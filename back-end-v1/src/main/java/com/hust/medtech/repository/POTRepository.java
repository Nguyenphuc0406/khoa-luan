package com.hust.medtech.repository;

import com.hust.medtech.data.dto.PotDTO;
import com.hust.medtech.data.entity.ProcessOfTreatment;
import com.hust.medtech.data.entity.TransactionMedical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface POTRepository extends JpaRepository<ProcessOfTreatment, Integer> {
    @Query("select p from ProcessOfTreatment p where p.patientPot.account.username = ?1 and function('DATE',function('DATE_FORMAT',p.dateOfExamination,'%Y-%m-%d')) = function('CURDATE') order by p.dateOfExamination desc ")
    Page<ProcessOfTreatment>  _getProcessByUsernameAndDay(String username, Pageable pageable);



}
