package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String verificationLevel;

    @Setter
    private String officerName;

    @Setter
    private String status;

    @Setter
    private String remarks;

    @Setter
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

}