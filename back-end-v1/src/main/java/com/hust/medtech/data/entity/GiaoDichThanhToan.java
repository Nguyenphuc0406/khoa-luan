package com.hust.medtech.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GIAO_DICH_THANH_TOAN")
@Entity
public class GiaoDichThanhToan {
    @Id
    @Column(name = "giao_dich_thanh_toan_id")
    @GeneratedValue( generator = "uuid2" )
    @GenericGenerator( name = "uuid2", strategy = "uuid2" )
    private String giaoDichThanhToanId;
    @Column(name = "ngay_giao_dich")
    private Date data;
}
