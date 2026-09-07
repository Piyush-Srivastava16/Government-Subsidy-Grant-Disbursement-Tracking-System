 package com.government.subsidy.service;

import java.util.Map;

public interface AnalyticsService {

    Map<String, Long> getBeneficiaryCountByRegion();

    // Region-wise Released Fund Analytics
    Map<String, Double> getReleasedFundByRegion();

    long getTotalApplications();

    long getApprovedApplications();

    long getPendingApplications();

    Double getTotalFundReleased();

    Double getTotalFundUtilized();
}

