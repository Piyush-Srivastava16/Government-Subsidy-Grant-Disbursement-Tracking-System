 package com.government.subsidy.service;

import com.government.subsidy.dto.FundUtilizationSummary;
import com.government.subsidy.entity.FundUtilization;

import java.util.List;

public interface FundUtilizationService {

    FundUtilization createFundUtilization(
            FundUtilization fundUtilization);

    FundUtilization getFundUtilizationById(Long id);

    List<FundUtilization> getAllFundUtilizations();

    FundUtilization updateFundUtilization(
            Long id,
            FundUtilization fundUtilization);

    void deleteFundUtilization(Long id);

    // Dashboard analytics
    FundUtilizationSummary getFundUtilizationSummary();
}
