package com.government.subsidy.service.imp;

import com.government.subsidy.repository.ApplicationRepository;
import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.repository.DisbursementRepository;
import com.government.subsidy.repository.FundUtilizationRepository;
import com.government.subsidy.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("analyticsServiceV1")
public class AnalyticsServiceImpl implements AnalyticsService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final ApplicationRepository applicationRepository;
    private final DisbursementRepository disbursementRepository;
    private final FundUtilizationRepository fundUtilizationRepository;

    public AnalyticsServiceImpl(
            BeneficiaryRepository beneficiaryRepository,
            ApplicationRepository applicationRepository,
            DisbursementRepository disbursementRepository,
            FundUtilizationRepository fundUtilizationRepository) {

        this.beneficiaryRepository = beneficiaryRepository;
        this.applicationRepository = applicationRepository;
        this.disbursementRepository = disbursementRepository;
        this.fundUtilizationRepository = fundUtilizationRepository;
    }

    @Override
    public Map<String, Long> getBeneficiaryCountByRegion() {

        List<Object[]> results =
                beneficiaryRepository.countBeneficiariesByRegion();

        Map<String, Long> analytics = new LinkedHashMap<>();

        for (Object[] row : results) {

            String region = (String) row[0];
            Long count = ((Number) row[1]).longValue();

            analytics.put(region, count);
        }

        return analytics;
    }

    // Region-wise Released Fund Analytics
    @Override
    public Map<String, Double> getReleasedFundByRegion() {

        List<Object[]> results =
                disbursementRepository.getReleasedFundByRegion();

        Map<String, Double> analytics =
                new LinkedHashMap<>();

        for (Object[] row : results) {

            String region = (String) row[0];

            Double amount =
                    ((Number) row[1]).doubleValue();

            analytics.put(region, amount);
        }

        return analytics;
    }

    @Override
    public long getTotalApplications() {
        return applicationRepository.count();
    }

    @Override
    public long getApprovedApplications() {
        return applicationRepository.countByStatus("APPROVED");
    }

    @Override
    public long getPendingApplications() {
        return applicationRepository.countByStatus("PENDING");
    }

    @Override
    public Double getTotalFundReleased() {
        return disbursementRepository.getTotalReleasedAmount();
    }

    @Override
    public Double getTotalFundUtilized() {
        return fundUtilizationRepository.getTotalUtilizedAmount();
    }
}
