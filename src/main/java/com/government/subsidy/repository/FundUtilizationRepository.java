package com.government.subsidy.repository;

import com.government.subsidy.entity.FundUtilization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FundUtilizationRepository
        extends JpaRepository<FundUtilization, Long> {

    @Query("""
            SELECT COALESCE(SUM(f.utilizedAmount), 0)
            FROM FundUtilization f
            WHERE f.status = 'VERIFIED'
            """)
    Double getTotalUtilizedAmount();
}