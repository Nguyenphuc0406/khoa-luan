package com.hust.medtech.repository;

import com.hust.medtech.data.entity.Patient;
import com.hust.medtech.data.entity.TransactionMedical;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionMedicalRepository extends JpaRepository<TransactionMedical, Integer> {
    TransactionMedical findByTransId(int transId);

    TransactionMedical findByPatientAndStatus(Patient patient, int status);

    @Query("select t from TransactionMedical t where t.patient.account.username = ?1 and function('DATE',function('DATE_FORMAT',t.createDate,'%Y-%m-%d')) = function('CURDATE') order by t.createDate desc ")
    Page<TransactionMedical> _getTransByUserAnDate(String userName, Pageable pageable);
}
