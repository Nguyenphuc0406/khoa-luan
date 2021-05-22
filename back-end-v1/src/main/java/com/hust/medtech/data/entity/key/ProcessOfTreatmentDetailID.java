package com.hust.medtech.data.entity.key;

import com.hust.medtech.data.entity.Dept;
import com.hust.medtech.data.entity.ProcessOfTreatment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ProcessOfTreatmentDetailID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "pot_id")
    private ProcessOfTreatment potId;
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Dept deptId;
}
