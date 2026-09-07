package com.government.subsidy.repository;

import com.government.subsidy.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeneficiaryRepository
        extends JpaRepository<Beneficiary, Long> {

    @Query("""
            SELECT b.region, COUNT(b)
            FROM Beneficiary b
            GROUP BY b.region
            """)
    List<Object[]> countBeneficiariesByRegion();
}