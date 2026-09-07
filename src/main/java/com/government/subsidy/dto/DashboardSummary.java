 package com.government.subsidy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummary {

    private long totalBeneficiaries;

    private long totalApplications;

    private long approvedApplications;

    private long pendingApplications;

    private long totalDisbursements;

    private Double totalDisbursementAmount;

    private Double totalFundUtilized;
}

