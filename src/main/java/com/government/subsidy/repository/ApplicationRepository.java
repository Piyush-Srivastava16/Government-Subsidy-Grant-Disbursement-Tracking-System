 package com.government.subsidy.repository;

import com.government.subsidy.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    long countByStatus(String status);

    @Query("""
            SELECT a.status, COUNT(a)
            FROM Application a
            GROUP BY a.status
            """)
    List<Object[]> countApplicationsByStatus();
}

