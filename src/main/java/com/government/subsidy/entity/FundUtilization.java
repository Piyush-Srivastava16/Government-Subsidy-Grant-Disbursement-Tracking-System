package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class FundUtilization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Double utilizedAmount;

    @Setter
    private LocalDate utilizationDate;

    @Setter
    private String description;

    @Setter
    private String status;

    @Setter
    @OneToOne
    @JoinColumn(name = "disbursement_id")
    private Disbursement disbursement;

}