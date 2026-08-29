package com.government.subsidy.repository;

import com.government.subsidy.entity.BeneficiaryDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryDocumentRepository
        extends JpaRepository<BeneficiaryDocument, Long> {
}