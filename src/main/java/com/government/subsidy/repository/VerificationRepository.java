package com.government.subsidy.repository;

import com.government.subsidy.entity.Application;
import com.government.subsidy.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository
        extends JpaRepository<Verification, Long> {

    Optional<Verification> findByApplicationAndVerificationLevel(
            Application application,
            String verificationLevel
    );
}