package com.government.subsidy.repository;

import com.government.subsidy.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository
        extends JpaRepository<Milestone, Long> {
}