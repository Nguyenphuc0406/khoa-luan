package com.hust.medtech.repository;

import com.hust.medtech.data.entity.TransactionMedicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransMedDetailRepository extends JpaRepository<TransactionMedicalDetail, Integer> {
    @Transactional
    @Modifying
    @Query(value = "select t.transDetailId.itemOfDeptId from TransactionMedicalDetail t where t.transDetailId.transactionId = ?1")
    List<Integer> getListIodByTransId(int transactionId);

}
