package com.government.subsidy.service;

import com.government.subsidy.entity.Subsidy;

import java.util.List;

public interface SubsidyService {

    Subsidy createSubsidy(Subsidy subsidy);

    Subsidy getSubsidyById(Long id);

    List<Subsidy> getAllSubsidies();

    Subsidy updateSubsidy(Long id, Subsidy subsidy);

    void deleteSubsidy(Long id);
}
