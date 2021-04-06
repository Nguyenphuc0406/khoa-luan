package com.hust.medtech.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_of_dept")
public class ItemOfDept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IOD_ID")
    private int iodId;

    @Column(name = "name")
    private String name;

    @Column(name = "consulting_room")
    private String consultingRoom;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEPT_ID")
    private Dept dept;

}
