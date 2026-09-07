package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Double amount;

    @Setter
    private LocalDate disbursementDate;

    @Setter
    private String status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Setter
    @OneToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

}