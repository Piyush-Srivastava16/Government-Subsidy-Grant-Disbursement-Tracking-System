package com.government.subsidy.service.imp;

import com.government.subsidy.dto.DashboardSummary;
import com.government.subsidy.repository.ApplicationRepository;
import com.government.subsidy.repository.BeneficiaryRepository;
import com.government.subsidy.repository.DisbursementRepository;
import com.government.subsidy.repository.FundUtilizationRepository;
import com.government.subsidy.service.DashboardService;
import org.springframework.stereotype.Service;

@Service("dashboardServiceV1")
public class DashboardServiceImplV1
        implements DashboardService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final ApplicationRepository applicationRepository;
    private final DisbursementRepository disbursementRepository;
    private final FundUtilizationRepository fundUtilizationRepository;

    public DashboardServiceImplV1(
            BeneficiaryRepository beneficiaryRepository,
            ApplicationRepository applicationRepository,
            DisbursementRepository disbursementRepository,
            FundUtilizationRepository fundUtilizationRepository) {

        this.beneficiaryRepository = beneficiaryRepository;
        this.applicationRepository = applicationRepository;
        this.disbursementRepository = disbursementRepository;
        this.fundUtilizationRepository =
                fundUtilizationRepository;
    }

    @Override
    public DashboardSummary getDashboardSummary() {

        long totalBeneficiaries =
                beneficiaryRepository.count();

        long totalApplications =
                applicationRepository.count();

        long approvedApplications =
                applicationRepository.countByStatus(
                        "APPROVED");

        long pendingApplications =
                applicationRepository.countByStatus(
                        "PENDING");

        long totalDisbursements =
                disbursementRepository.count();

        Double totalDisbursementAmount =
                disbursementRepository
                        .getTotalReleasedAmount();

        Double totalFundUtilized =
                fundUtilizationRepository
                        .getTotalUtilizedAmount();

        return new DashboardSummary(
                totalBeneficiaries,
                totalApplications,
                approvedApplications,
                pendingApplications,
                totalDisbursements,
                totalDisbursementAmount,
                totalFundUtilized
        );
    }
}