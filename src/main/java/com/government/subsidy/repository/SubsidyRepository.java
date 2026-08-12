package com.government.subsidy.repository;

import com.government.subsidy.entity.Subsidy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository   // @Repository represents the data access layer, and It also allows Spring to manage this repository as a Spring bean.
public interface SubsidyRepository extends JpaRepository<Subsidy, Long> {

}

