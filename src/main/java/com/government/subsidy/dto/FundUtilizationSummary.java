package com.government.subsidy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundUtilizationSummary {

    private Double totalReleasedAmount;

    private Double totalPendingAmount;

    private long totalDisbursements;
}
