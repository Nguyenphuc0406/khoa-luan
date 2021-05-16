package com.hust.medtech.data.entity.key;

import com.hust.medtech.data.entity.ItemOfDept;
import com.hust.medtech.data.entity.TransactionMedical;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class TransactionMedicalDetailID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private TransactionMedical transactionMedical;
    @ManyToOne
    @JoinColumn(name = "item_of_dept_id")
    private ItemOfDept itemOfDeptId;
}
