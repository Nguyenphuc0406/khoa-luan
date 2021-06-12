package com.hust.medtech.repository;

import com.hust.medtech.data.entity.ProcessOfTreatmentDetail;
import com.hust.medtech.data.entity.key.ProcessOfTreatmentDetailID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface POTDetailRepository extends JpaRepository<ProcessOfTreatmentDetail, ProcessOfTreatmentDetailID> {

    @Query(value = "select count(*) " +
            "from process_of_treatment_detail " +
            "         inner join process_of_treatment pot " +
            "                    on process_of_treatment_detail.pot_id = pot.pot_id " +
            "where dept_id = ?1" +
            "  and DATE(DATE_FORMAT(date_of_examination, '%Y-%m-%d')) = CURDATE()", nativeQuery = true)
    List<Object[]> _getCountNumberDept(int deptId);

    @Query("select p from ProcessOfTreatmentDetail p where p.indexNum > :currentNum and p.processDetailId.deptId.deptId = :deptId " +
            " and p.processDetailId.potId.potId > :potCurrent and p.accStatus = 0 order by p.indexNum asc")
    Page<ProcessOfTreatmentDetail> _getAccountNotifyByPotId(@Param("currentNum") int currentNum,
                                                            @Param("deptId")int deptId,   @Param("potCurrent") int potCurrent,
                                                            Pageable pageable);

}
