package com.government.subsidy.repository;

import com.government.subsidy.entity.Disbursement;
import com.government.subsidy.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DisbursementRepository
        extends JpaRepository<Disbursement, Long> {

    Optional<Disbursement> findByMilestone(Milestone milestone);

    // Total Released Amount
    @Query("""
            SELECT COALESCE(SUM(d.amount), 0)
            FROM Disbursement d
            WHERE d.status = 'RELEASED'
            """)
    Double getTotalReleasedAmount();

    // Total Pending Amount
    @Query("""
            SELECT COALESCE(SUM(d.amount), 0)
            FROM Disbursement d
            WHERE d.status = 'PENDING'
            """)
    Double getTotalPendingAmount();

    // Region-wise Released Fund
    @Query("""
            SELECT d.application.beneficiary.region,
                   COALESCE(SUM(d.amount), 0)
            FROM Disbursement d
            WHERE d.status = 'RELEASED'
            GROUP BY d.application.beneficiary.region
            """)
    List<Object[]> getReleasedFundByRegion();
}

