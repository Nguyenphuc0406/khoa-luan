package com.hust.medtech.repository;

import com.hust.medtech.data.entity.TransactionMedicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransMedDetailRepository extends JpaRepository<TransactionMedicalDetail, Integer> {
    @Transactional
    @Modifying
    @Query(value = "select t.transDetailId.itemOfDeptId.iodId from TransactionMedicalDetail t where t.transDetailId.transactionMedical.processOfTreatment.potId = ?1")
    List<Integer> getListIodByTransId(int potId);


    @Query("select td from TransactionMedicalDetail  td inner  join TransactionMedical  t on td.transDetailId.transactionMedical.transId " +
            " = t.transId where  t.doctor.deptDoctor.deptId = :deptId and t.processOfTreatment.potId =:potId " +
            " and t.patient.patientId = :patientId ")
    List<TransactionMedicalDetail> _getTransByPotIdAndDeptID(@Param("potId") int potId,
                                                             @Param("deptId") int deptId,
                                                             @Param("patientId") int patientId);
}
