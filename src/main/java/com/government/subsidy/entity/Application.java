package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDate applicationDate;

    @Setter
    private String status;

    @Setter
    private Double eligibilityScore;

    @Setter
    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @Setter
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

}