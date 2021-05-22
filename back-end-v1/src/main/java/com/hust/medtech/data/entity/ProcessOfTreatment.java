package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "process_of_treatment")
public class ProcessOfTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POT_ID")
    private int potId;

    @Column(name = "DATE_OF_EXAMINATION")
    private Date dateOfExamination;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patientPot;

    @OneToMany(mappedBy = "processDetailId.potId",fetch = FetchType.LAZY)
    private List<ProcessOfTreatmentDetail> processOfTreatmentDetails;

}
