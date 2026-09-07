package com.government.subsidy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class BeneficiaryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String documentType;

    @Setter
    private String documentName;

    @Setter
    private String documentNumber;

    @Setter
    private String verificationStatus;

    @Setter
    private String fileName;

    @Setter
    private String filePath;

    @Setter
    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
}